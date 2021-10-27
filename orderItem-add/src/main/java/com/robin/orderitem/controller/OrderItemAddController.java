package com.robin.orderitem.controller;

import com.robin.entity.ShoppingCartVO;
import com.robin.orderitem.service.OrderItemAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderItemAddController {
    @Autowired
    private OrderItemAddService orderItemAddService;

    @PostMapping("/item/save")
    public int save(@RequestBody List<ShoppingCartVO> shoppingCartVOS, String orderId){
        return orderItemAddService.saveOrderItem(shoppingCartVOS, orderId);
    }
}
