package com.robin.orderitem.controller;

import com.robin.entity.OrderItem;
import com.robin.orderitem.service.OrderItemQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderItemQueryController {

    @Autowired
    private OrderItemQueryService orderItemQueryService;

    @GetMapping("/orderItem/query")
    public List<OrderItem> query(String orderId){
        return orderItemQueryService.queryOrderItem(orderId);
    }
}
