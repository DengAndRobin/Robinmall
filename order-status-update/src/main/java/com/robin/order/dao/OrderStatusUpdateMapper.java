package com.robin.order.dao;

import com.robin.entity.Orders;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
@Repository
public interface OrderStatusUpdateMapper extends Mapper<Orders>, MySqlMapper<Orders> {
}
