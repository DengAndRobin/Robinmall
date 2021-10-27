package com.robin.api.service.feign;

import com.robin.api.service.feign.fallback.StockUpdateFeignFallBack;
import com.robin.beans.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(value = "stock-update",fallback = StockUpdateFeignFallBack.class)
public interface StockUpdateFeign {

    @PutMapping("/stock/update")
    public int update(List<ProductSku> skus);
}
