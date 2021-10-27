package com.robin.service;

import com.robin.entity.ShoppingCart;
import com.robin.vo.ResultVO;

import java.util.List;

public interface ShoppingCartService {

    public ResultVO addShoppingCart(ShoppingCart cart);

    public ResultVO listShoppingCartsByUserId(int userId);

    public ResultVO updateCartNum(int cartId,int cartNum);

    public ResultVO deleteShoppingCart(int cartId);

    public ResultVO listShoppingCartsByCartIds(String cartIds);
}
