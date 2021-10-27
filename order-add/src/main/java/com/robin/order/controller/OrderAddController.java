package com.robin.order.controller;

import com.robin.entity.Orders;
import com.robin.entity.ShoppingCartVO;
import com.robin.order.service.OrderAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderAddController {

    @Autowired
    private OrderAddService orderAddService;

    @PostMapping("/order/save")
    public List<ShoppingCartVO> add(@RequestBody Orders order, String cids){
        return orderAddService.addOrder(order, cids);
    }
}
