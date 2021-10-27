package com.robin.service.impl;

import com.robin.dao.*;
import com.robin.entity.*;
import com.robin.service.OrderService;
import com.robin.vo.PageHelper;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Transactional
    public Map<String, String> addOrder(String cartIds, Orders order) throws SQLException {
        Map<String,String>map = null;
        String[] arr = cartIds.split(",");
        ArrayList<Integer> cartIdList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            cartIdList.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> shoppingCartVOList = shoppingCartMapper.selectShoppingCartByCartIds(cartIdList);
        //加锁
        boolean isLock = true;
        String[] skuIds = new String[shoppingCartVOList.size()];
        Map<String, RLock> locks = new HashMap<>();
        for (int i = 0; i < shoppingCartVOList.size();i++) {
            String skuId = shoppingCartVOList.get(i).getSkuId();
            RLock lock = redissonClient.getLock(skuId);
            boolean b = false;
            try {
                b = lock.tryLock(10, 3, TimeUnit.SECONDS);
                if(b){
                    skuIds[i] = skuId;
                    locks.put(skuId, lock);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isLock = isLock & b;
        }
        try {
            if(isLock){
                //检查库存是否足够
                boolean isEnough = true;
                String untitled = "";
                shoppingCartVOList = shoppingCartMapper.selectShoppingCartByCartIds(cartIdList);
                for (ShoppingCartVO shoppingCartVO : shoppingCartVOList) {
                    if(Integer.parseInt(shoppingCartVO.getCartNum()) > shoppingCartVO.getSkuStock()){
                        isEnough = false;
                    }
                    untitled += shoppingCartVO.getProductName() + " ";
                }
                if(isEnough){
                    //保存订单
                    order.setUntitled(untitled);
                    order.setStatus("1");
                    order.setCreateTime(new Date());
                    String orderId = UUID.randomUUID().toString().replaceAll("-", "");
                    order.setOrderId(orderId);
                    ordersMapper.insert(order);
                    //保存订单项快照
                    for (ShoppingCartVO cartVO : shoppingCartVOList) {
                        int cnum = Integer.parseInt(cartVO.getCartNum());
                        String orderItemId = System.currentTimeMillis() + "" + (new Random().nextInt(89999) + 10000);
                        OrderItem orderItem = new OrderItem(orderItemId, orderId, cartVO.getProductId(), cartVO.getProductName(), cartVO.getProductImg(), cartVO.getSkuId(),
                                cartVO.getSkuName(), new BigDecimal(cartVO.getSellPrice()), cnum, new BigDecimal(cartVO.getSellPrice() * cnum), new Date(),
                                new Date(), 0);
                        orderItemMapper.insert(orderItem);

                    }
                    //扣减库存
                    List<ProductSku> skus = new ArrayList<>();
                    for (ShoppingCartVO cartVO : shoppingCartVOList) {
                        ProductSku productSku = new ProductSku();
                        productSku.setSkuId(cartVO.getSkuId());
                        productSku.setStock(cartVO.getSkuStock()-Integer.parseInt(cartVO.getCartNum()));
                        skus.add(productSku);
                    }
                    for (ProductSku productSku : skus) {
                        productSkuMapper.updateByPrimaryKeySelective(productSku);
                    }
                    //删除购物车记录
                    for (Integer cartId : cartIdList) {
                        shoppingCartMapper.deleteByPrimaryKey(cartId);
                    }
                    map = new HashMap<>();
                    map.put("orderId", orderId);
                    map.put("productNames", untitled);
                    return map;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            for (int i = 0; i < skuIds.length; i++) {
                if(skuIds[i] != null && !"".equals(skuIds[i])){
                    locks.get(skuIds[i]).unlock();
                }
            }
        }
        return map;

    }

    @Override
    @Transactional
    public ResultVO updateOrderStatus(String orderId, String status) {
        Example example = new Example(OrderItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        List<OrderItem> orderItemList = orderItemMapper.selectByExample(example);
        for (OrderItem orderItem : orderItemList) {
            ProductSku productSku = productSkuMapper.selectByPrimaryKey(orderItem.getSkuId());
            Product product = productMapper.selectByPrimaryKey(productSku.getProductId());
            product.setSoldNum(product.getSoldNum() + orderItem.getBuyCounts());
            productMapper.updateByPrimaryKey(product);
        }
        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setStatus(status);
        order.setUpdateTime(new Date());
        order.setPayTime(new Date());
        int i = ordersMapper.updateByPrimaryKeySelective(order);
        if(i <= 0){
            return new ResultVO(ResStatus.NO,"update fail",null);
        }
        return new ResultVO(ResStatus.YES,"update success",null);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeOrder(String orderId) {
        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setStatus("6");
        order.setUpdateTime(new Date());
        order.setCancelTime(new Date());
        order.setCloseType(1);
        order.setDeleteStatus(1);
        ordersMapper.updateByPrimaryKeySelective(order);
        //还原库存
        Example example = new Example(OrderItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        List<OrderItem> orderItemList = orderItemMapper.selectByExample(example);
        for (OrderItem orderItem : orderItemList) {
            ProductSku productSku = productSkuMapper.selectByPrimaryKey(orderItem.getSkuId());
            productSku.setStock(productSku.getStock()+orderItem.getBuyCounts());
            productSkuMapper.updateByPrimaryKey(productSku);
        }
    }

    @Override
    public ResultVO getOrders(String userId, String status, int pageNum, int limit) {
        int start = (pageNum - 1) * limit;
        List<OrdersVO> ordersVOList = ordersMapper.selectOrders(userId, status, start, limit);
        //查询总记录数
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        if(status != null && !"".equals(status)){
            criteria.andEqualTo("status", status);
        }
        int totalCount = ordersMapper.selectCountByExample(example);
        int pageCount = totalCount % limit == 0? totalCount/limit : (totalCount/limit)+1;
        return new ResultVO(ResStatus.YES,"success",
                new PageHelper<OrdersVO>(totalCount,pageCount,ordersVOList));
    }

    @Override
    @Transactional
    public ResultVO deleteOrderByOrderId(String orderId) {
        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setDeleteStatus(1);
        int i = ordersMapper.updateByPrimaryKeySelective(order);
        if(i <= 0){
            return new ResultVO(ResStatus.NO,"fail",null);
        }
        return new ResultVO(ResStatus.YES,"success",null);
    }
}
