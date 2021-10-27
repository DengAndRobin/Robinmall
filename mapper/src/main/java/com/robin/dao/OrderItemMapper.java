package com.robin.dao;

import com.robin.entity.OrderItem;
import com.robin.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper extends GeneralDao<OrderItem> {

    public List<OrderItem> selectOrderItemsByOrderId(String orderId);
}