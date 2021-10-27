package com.robin.productskuquery.dao;

import com.robin.entity.ProductSku;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface ProductSkuQueryMapper extends Mapper<ProductSku>, MySqlMapper<ProductSku> {

}
