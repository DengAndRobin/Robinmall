package com.robin.productskuquery.controller;

import com.robin.entity.ProductSku;
import com.robin.productskuquery.service.ProductSkuQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductSkuQueryController {

    @Autowired
    private ProductSkuQueryService productSkuQueryService;

    @GetMapping("/product/sku/query")
    public ProductSku query(String skuId){
        return productSkuQueryService.queryProductSku(skuId);
    }
}
