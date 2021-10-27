package com.robin.order.controller;

import com.robin.entity.Orders;
import com.robin.order.service.OrderTimeoutQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderTimeoutQueryController {

    @Autowired
    private OrderTimeoutQueryService orderTimeoutQueryService;
    @GetMapping("/order/query/timeout")
    public List<Orders> query(){
        return orderTimeoutQueryService.listOrders();
    }
}
