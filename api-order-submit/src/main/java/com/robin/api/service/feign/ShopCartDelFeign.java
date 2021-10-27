package com.robin.api.service.feign;

import com.robin.api.service.feign.fallback.ShopCartDelFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "shopcart-del",fallback = ShopCartDelFeignFallBack.class)
public interface ShopCartDelFeign {

    @DeleteMapping("/shopcart/delete")
    public int delete(@RequestParam String cartIds);
}
