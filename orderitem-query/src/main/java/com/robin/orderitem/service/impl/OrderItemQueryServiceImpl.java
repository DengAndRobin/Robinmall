package com.robin.orderitem.service.impl;

import com.robin.entity.OrderItem;
import com.robin.orderitem.dao.OrderItemQueryMapper;
import com.robin.orderitem.service.OrderItemQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class OrderItemQueryServiceImpl implements OrderItemQueryService {
    @Autowired
    private OrderItemQueryMapper orderItemQueryMapper;

    @Override
    public List<OrderItem> queryOrderItem(String orderId) {
        Example example = new Example(OrderItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        List<OrderItem> orderItemList = orderItemQueryMapper.selectByExample(example);
        return orderItemList;
    }
}
