package com.robin.stock.dao;

import com.robin.entity.ProductSku;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface StockUpdateMapper extends Mapper<ProductSku>, MySqlMapper<ProductSku> {
}
