package com.robin.order.service.feign;

import com.robin.beans.Orders;
import com.robin.order.service.feign.fallback.OrderStatusUpdateClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "order-status-update",fallback = OrderStatusUpdateClientFallBack.class)
public interface OrderStatusUpdateClient {
    @PutMapping("/order/status/update")
    public int update(@RequestBody Orders order);
}
