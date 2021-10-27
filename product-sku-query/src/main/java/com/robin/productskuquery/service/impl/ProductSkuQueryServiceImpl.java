package com.robin.productskuquery.service.impl;

import com.netflix.discovery.converters.Auto;
import com.robin.entity.ProductSku;
import com.robin.productskuquery.dao.ProductSkuQueryMapper;
import com.robin.productskuquery.service.ProductSkuQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSkuQueryServiceImpl implements ProductSkuQueryService {
    @Autowired
    private ProductSkuQueryMapper productSkuQueryMapper;

    @Override
    public ProductSku queryProductSku(String skuId) {
        return productSkuQueryMapper.selectByPrimaryKey(skuId);
    }
}
