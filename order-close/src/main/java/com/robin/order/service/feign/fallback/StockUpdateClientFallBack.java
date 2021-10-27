package com.robin.order.service.feign.fallback;

import com.robin.beans.ProductSku;
import com.robin.order.service.feign.StockUpdateClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockUpdateClientFallBack implements StockUpdateClient {
    @Override
    public int update(List<ProductSku> skus) {
        System.out.println("stock-update-----fallback!");
        return 0;
    }
}
