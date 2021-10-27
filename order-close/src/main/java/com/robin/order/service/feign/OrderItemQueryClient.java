package com.robin.order.service.feign;

import com.robin.beans.OrderItem;
import com.robin.order.service.feign.fallback.OrderStatusUpdateClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "orderitem-query",fallback = OrderStatusUpdateClientFallBack.class)
public interface OrderItemQueryClient {

    @GetMapping("/orderItem/query")
    public List<OrderItem> query(@RequestParam String orderId);
}
