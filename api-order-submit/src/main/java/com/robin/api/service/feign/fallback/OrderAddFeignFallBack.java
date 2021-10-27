package com.robin.api.service.feign.fallback;

import com.robin.api.service.feign.OrderAddFeign;
import com.robin.beans.Orders;
import com.robin.beans.ShoppingCartVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Component
public class OrderAddFeignFallBack implements OrderAddFeign {
    @Override
    public List<ShoppingCartVO> add(Orders order, String cids) {
        return null;
    }
}
