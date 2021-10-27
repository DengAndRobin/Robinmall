package com.robin.api.service.feign.fallback;

import com.robin.api.service.feign.StockUpdateFeign;
import com.robin.beans.ProductSku;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockUpdateFeignFallBack implements StockUpdateFeign {
    @Override
    public int update(List<ProductSku> skus) {
        return 0;
    }
}
