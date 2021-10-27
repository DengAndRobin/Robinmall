package com.robin.order.controller;

import com.robin.order.service.OrderCloseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderCloseContorller {

    @Autowired
    private OrderCloseService orderCloseService;

    @PutMapping("/order/close")
    public int close(String orderId,int closeType){
        return orderCloseService.closeOrder(orderId, closeType);
    }
}
