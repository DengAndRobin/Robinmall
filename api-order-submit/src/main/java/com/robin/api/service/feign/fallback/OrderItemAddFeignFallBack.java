package com.robin.api.service.feign.fallback;

import com.robin.api.service.feign.OrderItemAddFeign;
import com.robin.beans.ShoppingCartVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemAddFeignFallBack implements OrderItemAddFeign {
    @Override
    public int save(List<ShoppingCartVO> shoppingCartVOS, String orderId) {
        return 0;
    }
}
