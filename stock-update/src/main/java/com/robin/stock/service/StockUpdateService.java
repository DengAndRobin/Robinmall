package com.robin.stock.service;

import com.robin.entity.ProductSku;
import org.apache.catalina.LifecycleState;

import java.util.List;

public interface StockUpdateService {
    public int updateStock(List<ProductSku> skus);
}