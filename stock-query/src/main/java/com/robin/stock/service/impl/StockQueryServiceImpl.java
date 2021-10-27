package com.robin.stock.service.impl;

import com.robin.entity.ShoppingCartVO;
import com.robin.stock.dao.StockQueryMapper;
import com.robin.stock.service.StockQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockQueryServiceImpl implements StockQueryService {

    @Autowired
    private StockQueryMapper stockQueryMapper;
    @Override
    public List<ShoppingCartVO> queryStock(String cartIds) {
        String[] arr = cartIds.split(",");
        ArrayList<Integer> cartIdList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            cartIdList.add(Integer.parseInt(arr[i]));
        }
        return stockQueryMapper.selectShoppingCartByCartIds(cartIdList);
    }
}
