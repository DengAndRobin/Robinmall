package com.robin.shopcart.dao;

import com.robin.entity.ShoppingCart;
import com.robin.entity.ShoppingCartVO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface ShopCartDelMapper extends Mapper<ShoppingCartVO>, MySqlMapper<ShoppingCartVO> {
}
