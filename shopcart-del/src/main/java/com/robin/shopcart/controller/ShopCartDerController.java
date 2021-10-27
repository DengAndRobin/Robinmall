package com.robin.shopcart.controller;

import com.robin.shopcart.dao.ShopCartDelMapper;
import com.robin.shopcart.service.ShopCartDelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ShopCartDerController {

    @Autowired
    private ShopCartDelService shopCartDelService;

    @DeleteMapping("/shopcart/delete")
    public int delete(String cartIds) {
        return shopCartDelService.deleteShopCart(cartIds);
    }
}
