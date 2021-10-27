package com.robin.order.service.feign.fallback;

import com.robin.beans.OrderItem;
import com.robin.order.service.feign.OrderItemQueryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemQueryClientFallBack implements OrderItemQueryClient {

    @Override
    public List<OrderItem> query(String orderId) {
        System.out.println("orderItemQuery----fallback");
        return null;
    }
}
