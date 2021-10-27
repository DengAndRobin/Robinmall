package com.robin.order.service.feign;

import com.robin.beans.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "stock-update")
public interface StockUpdateClient {

    @PutMapping("/stock/update")
    public int update(@RequestBody List<ProductSku> skus);

}
