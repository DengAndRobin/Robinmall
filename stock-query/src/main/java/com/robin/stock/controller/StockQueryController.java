package com.robin.stock.controller;

import com.robin.entity.ShoppingCartVO;
import com.robin.stock.service.StockQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockQueryController {

    @Autowired
    private StockQueryService stockQueryService;
    @GetMapping("/query")
    public List<ShoppingCartVO> query(String cartIds){
        return stockQueryService.queryStock(cartIds);
    }
}
