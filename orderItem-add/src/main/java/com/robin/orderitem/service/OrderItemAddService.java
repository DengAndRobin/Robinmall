package com.robin.orderitem.service;

import com.robin.entity.OrderItem;
import com.robin.entity.ShoppingCartVO;

import java.util.List;

public interface OrderItemAddService {
    public int saveOrderItem(List<ShoppingCartVO> shoppingCartVOS, String orderId);
}
