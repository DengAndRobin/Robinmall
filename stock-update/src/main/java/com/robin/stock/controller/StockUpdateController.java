package com.robin.stock.controller;

import com.robin.entity.ProductSku;
import com.robin.stock.service.StockUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockUpdateController {
    @Autowired
    private StockUpdateService stockUpdateService;

    @PutMapping("/stock/update")
    public int update(List<ProductSku> skus){
        return stockUpdateService.updateStock(skus);
    }
}
