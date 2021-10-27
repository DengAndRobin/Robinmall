package com.robin.order.service.feign;

import com.robin.entity.ShoppingCartVO;
import com.robin.order.service.feign.fallback.StockQueryClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "stock-query",fallback = StockQueryClientFallBack.class)
public interface StockQueryClient {

    @GetMapping("/stock/query")
    public List<ShoppingCartVO> query(@RequestParam("cartIds") String cartIds);
}
