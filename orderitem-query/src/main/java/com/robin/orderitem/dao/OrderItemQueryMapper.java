package com.robin.orderitem.dao;

import com.robin.entity.OrderItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface OrderItemQueryMapper extends Mapper<OrderItem>, MySqlMapper<OrderItem> {
}
