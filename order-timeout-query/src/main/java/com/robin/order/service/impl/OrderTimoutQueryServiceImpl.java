package com.robin.order.service.impl;

import com.robin.entity.Orders;
import com.robin.order.dao.OrderTimeoutQueryMapper;
import com.robin.order.service.OrderTimeoutQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class OrderTimoutQueryServiceImpl implements OrderTimeoutQueryService {

    @Autowired
    private OrderTimeoutQueryMapper orderTimeoutQueryMapper;
    @Override
    public List<Orders> listOrders() {
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");
        Date time = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
        criteria.andLessThan("createTime", time);
        List<Orders> ordersList = orderTimeoutQueryMapper.selectByExample(example);
        return ordersList;
    }
}
