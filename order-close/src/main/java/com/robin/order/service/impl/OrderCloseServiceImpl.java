package com.robin.order.service.impl;

import com.robin.beans.OrderItem;
import com.robin.beans.Orders;
import com.robin.beans.ProductSku;
import com.robin.order.service.OrderCloseService;
import com.robin.order.service.feign.OrderItemQueryClient;
import com.robin.order.service.feign.OrderStatusUpdateClient;
import com.robin.order.service.feign.ProductSkuQueryClient;
import com.robin.order.service.feign.StockUpdateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderCloseServiceImpl implements OrderCloseService {

    @Autowired
    private OrderStatusUpdateClient orderStatusUpdateClient;

    @Autowired
    private OrderItemQueryClient orderItemQueryClient;

    @Autowired
    private ProductSkuQueryClient productSkuQueryClient;

    @Autowired
    private StockUpdateClient stockUpdateClient;

    @Override
    public int closeOrder(String orderId,int closeType) {
        //1修改订单状态
        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setCloseType(closeType);
        order.setStatus("6");
        int i = orderStatusUpdateClient.update(order);
        //2查询订单项快照
        if(i > 0){
            List<OrderItem> orderItems = orderItemQueryClient.query(orderId);
            if (orderItems != null && orderItems.size() != 0){
                List<ProductSku> productSkus = new ArrayList<>();
                for (OrderItem orderItem : orderItems) {
                    ProductSku sku = productSkuQueryClient.query(orderItem.getSkuId());
                    sku.setStock(sku.getStock()+orderItem.getBuyCounts());
                    productSkus.add(sku);
                }
                //3还原库存
                int j = stockUpdateClient.update(productSkus);
                return j;
            }
        }

        return 0;
    }
}
