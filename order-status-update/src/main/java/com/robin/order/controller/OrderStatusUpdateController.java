package com.robin.order.controller;

import com.robin.entity.Orders;
import com.robin.order.service.OrderStatusUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderStatusUpdateController {

    @Autowired
    private OrderStatusUpdateService orderStatusUpdateService;

    @PutMapping("/order/status/update")
    public int update(@RequestBody Orders order){
        return orderStatusUpdateService.updateStatus(order);
    }
}
