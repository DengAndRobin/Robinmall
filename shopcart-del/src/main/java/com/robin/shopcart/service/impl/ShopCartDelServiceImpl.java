package com.robin.shopcart.service.impl;

import com.robin.shopcart.dao.ShopCartDelMapper;
import com.robin.shopcart.service.ShopCartDelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopCartDelServiceImpl implements ShopCartDelService {

    @Autowired
    private ShopCartDelMapper shopCartDelMapper;
    @Override
    public int deleteShopCart(String cartIds) {
        int isSuccess = 1;
        String[] arr = cartIds.split(",");
        for (int i = 0; i < arr.length; i++) {
            int j = shopCartDelMapper.deleteByPrimaryKey(Integer.parseInt(arr[i]));
            isSuccess &= j;
        }
        return isSuccess;
    }
}
