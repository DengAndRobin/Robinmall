package com.robin.api.service.feign.fallback;

import com.robin.api.service.feign.ShopCartDelFeign;
import org.springframework.stereotype.Component;

@Component
public class ShopCartDelFeignFallBack implements ShopCartDelFeign {
    @Override
    public int delete(String cartIds) {
        return 0;
    }
}
