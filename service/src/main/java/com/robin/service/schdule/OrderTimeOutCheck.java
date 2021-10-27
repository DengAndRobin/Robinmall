package com.robin.service.schdule;

import com.github.wxpay.sdk.WXPay;
import com.robin.dao.OrderItemMapper;
import com.robin.dao.OrdersMapper;
import com.robin.dao.ProductSkuMapper;
import com.robin.entity.OrderItem;
import com.robin.entity.Orders;
import com.robin.entity.ProductSku;
import com.robin.service.OrderService;
import com.robin.service.config.MyPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderTimeOutCheck {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderService orderService;

    private WXPay wxPay = new WXPay(new MyPayConfig());

    @Scheduled(cron = "5/5 * * * * ?")
    public void checkAndCloseOrder(){
        //1.查询有可能超时未支付的所有订单
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");
        Date time = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
        criteria.andLessThan("createTime", time);
        List<Orders> ordersList = ordersMapper.selectByExample(example);

        //2向微信支付平台确认支付状态
        try{
            HashMap<String,String>params = new HashMap<>();
            for (Orders order : ordersList) {
                params.put("out_trade_no", order.getOrderId());
                Map<String, String> queryResp = wxPay.orderQuery(params);
                if("SUCCESS".equalsIgnoreCase(queryResp.get("trade_state"))){
                    //如果订单已支付，修改订单状态为2
                    Orders updateOrder = new Orders();
                    updateOrder.setOrderId(order.getOrderId());
                    updateOrder.setStatus("2");
                    ordersMapper.updateByPrimaryKeySelective(updateOrder);
                }else if ("NOTPAY".equalsIgnoreCase(queryResp.get("trade_state"))){
                    //向微信平台取消支付订单
                    Map<String, String> closeResp = wxPay.closeOrder(params);
                    //更新订单状态
                    orderService.closeOrder(order.getOrderId());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
