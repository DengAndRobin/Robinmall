package com.robin.orderitem.service;

import com.robin.entity.OrderItem;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface OrderItemQueryService {
    public List<OrderItem> queryOrderItem(String orderId);
}
