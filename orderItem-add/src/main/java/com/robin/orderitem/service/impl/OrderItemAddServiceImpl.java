package com.robin.orderitem.service.impl;

import com.netflix.discovery.converters.Auto;
import com.robin.entity.OrderItem;
import com.robin.entity.ShoppingCartVO;
import com.robin.orderitem.dao.OrderItemMapper;
import com.robin.orderitem.service.OrderItemAddService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class OrderItemAddServiceImpl implements OrderItemAddService {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Override
    public int saveOrderItem(List<ShoppingCartVO> shoppingCartVOS, String orderId) {
        int isSuccess = 1;
        for (ShoppingCartVO cartVO : shoppingCartVOS) {
            int cnum = Integer.parseInt(cartVO.getCartNum());
            String orderItemId = System.currentTimeMillis() + "" + (new Random().nextInt(89999) + 10000);
            OrderItem orderItem = new OrderItem(orderItemId, orderId, cartVO.getProductId(), cartVO.getProductName(), cartVO.getProductImg(), cartVO.getSkuId(),
                    cartVO.getSkuName(), new BigDecimal(cartVO.getSellPrice()), cnum, new BigDecimal(cartVO.getSellPrice() * cnum), new Date(),
                    new Date(), 0);
            int i = orderItemMapper.insert(orderItem);
            isSuccess &= i;
        }
        return isSuccess;
    }
}
