package com.robin.stock.service;

import com.robin.entity.ShoppingCartVO;

import java.util.List;

public interface StockQueryService {

    public List<ShoppingCartVO> queryStock(String cartIds);
}
