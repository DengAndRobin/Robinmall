package com.robin.api.service.impl;

import com.robin.api.service.OrderSubmitService;
import com.robin.api.service.feign.OrderAddFeign;
import com.robin.api.service.feign.OrderItemAddFeign;
import com.robin.api.service.feign.ShopCartDelFeign;
import com.robin.api.service.feign.StockUpdateFeign;
import com.robin.beans.Orders;
import com.robin.beans.ProductSku;
import com.robin.beans.ShoppingCartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderSubmitServiceImpl implements OrderSubmitService {

    @Autowired
    private OrderAddFeign orderAddFeign;

    @Autowired
    private OrderItemAddFeign orderItemAddFeign;

    @Autowired
    private StockUpdateFeign stockUpdateFeign;

    @Autowired
    private ShopCartDelFeign shopCartDelFeign;

    @Override
    public Map<String, String> addOrder(String cids, Orders order) {

        Map<String,String> map = null;
        //1保存订单 校验库存
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");
        order.setOrderId(orderId);
        List<ShoppingCartVO> shoppingCartVOS = orderAddFeign.add(order, cids);
        //2保存订单快照
        if(shoppingCartVOS != null) {
            int i = orderItemAddFeign.save(shoppingCartVOS, orderId);
            if (i > 0) {
                //3修改库存
                List<ProductSku> skus = new ArrayList<>();
                for (ShoppingCartVO cartVO : shoppingCartVOS) {
                    ProductSku productSku = new ProductSku();
                    productSku.setSkuId(cartVO.getSkuId());
                    productSku.setStock(cartVO.getSkuStock()-Integer.parseInt(cartVO.getCartNum()));
                    skus.add(productSku);
                }
                int j = stockUpdateFeign.update(skus);
                if(j > 0){
                    //4删除购物车记录
                    int k = shopCartDelFeign.delete(cids);
                    if(k > 0){
                        map = new HashMap<>();
                        map.put("orderId", orderId);
                        String untitled ="";
                        for (ShoppingCartVO shoppingCartVO : shoppingCartVOS) {
                            untitled += shoppingCartVO.getSkuName() + " ";
                        }
                        map.put("productNames", untitled);
                        return map;
                    }
                }
            }
        }
        return null;
    }
}
