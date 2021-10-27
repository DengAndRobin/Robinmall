package com.robin.order.service.feign.fallback;

import com.robin.entity.ShoppingCartVO;
import com.robin.order.service.feign.StockQueryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockQueryClientFallBack implements StockQueryClient {

    @Override
    public List<ShoppingCartVO> query(String cartIds) {
        return null;
    }
}
