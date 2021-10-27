package com.robin.order.service.feign.fallback;

import com.robin.beans.Orders;
import com.robin.order.service.feign.OrderStatusUpdateClient;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusUpdateClientFallBack implements OrderStatusUpdateClient {
    @Override
    public int update(Orders order) {
        return 0;
    }
}
