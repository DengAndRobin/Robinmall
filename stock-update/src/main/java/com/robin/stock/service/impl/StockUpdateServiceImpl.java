package com.robin.stock.service.impl;

import com.robin.entity.ProductSku;
import com.robin.stock.dao.StockUpdateMapper;
import com.robin.stock.service.StockUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StockUpdateServiceImpl implements StockUpdateService {
    @Autowired
    private StockUpdateMapper stockUpdateMapper;
    @Override
    public int updateStock(List<ProductSku> skus) {
        int isSuccess = 1;
        for (ProductSku productSku : skus) {
            int i = stockUpdateMapper.updateByPrimaryKeySelective(productSku);
            isSuccess &= i;
        }
        return isSuccess;
    }
}
