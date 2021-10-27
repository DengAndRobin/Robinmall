package com.robin.order.service;

import com.robin.entity.Orders;
import com.robin.entity.ShoppingCartVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrderAddService {

    public List<ShoppingCartVO> addOrder(Orders orders, String cids);
}
