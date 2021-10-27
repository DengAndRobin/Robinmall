package com.robin.order.service.impl;

import com.robin.entity.Orders;
import com.robin.order.dao.OrderStatusUpdateMapper;
import com.robin.order.service.OrderStatusUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusUpdateServiceImpl implements OrderStatusUpdateService {

    @Autowired
    private OrderStatusUpdateMapper orderStatusUpdateMapper;
    @Override
    public int updateStatus(Orders order) {
        return orderStatusUpdateMapper.updateByPrimaryKeySelective(order);
    }
}
