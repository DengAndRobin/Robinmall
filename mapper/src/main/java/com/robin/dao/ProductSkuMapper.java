package com.robin.dao;

import com.robin.entity.ProductSku;
import com.robin.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuMapper extends GeneralDao<ProductSku> {

    /**
     * 根据商品id找出对应价格最低的套餐
     * @param productId
     * @return
     */
    public List<ProductSku> selectMinPriceByProductId(String productId);
}