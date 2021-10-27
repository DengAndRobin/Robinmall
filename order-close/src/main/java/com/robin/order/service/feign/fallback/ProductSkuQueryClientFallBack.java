package com.robin.order.service.feign.fallback;

import com.robin.beans.ProductSku;
import com.robin.order.service.feign.ProductSkuQueryClient;
import org.springframework.stereotype.Component;

@Component
public class ProductSkuQueryClientFallBack implements ProductSkuQueryClient {
    @Override
    public ProductSku query(String skuId) {
        System.out.println("product-sku-query----fallback!");
        return null;
    }
}
