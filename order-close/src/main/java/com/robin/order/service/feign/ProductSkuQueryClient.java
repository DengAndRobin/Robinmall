package com.robin.order.service.feign;

import com.robin.beans.ProductSku;
import com.robin.order.service.feign.fallback.ProductSkuQueryClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "product-sku-query",fallback = ProductSkuQueryClientFallBack.class)
public interface ProductSkuQueryClient {

    @GetMapping("/product/sku/query")
    public ProductSku query(@RequestParam String skuId);
}
