package com.robin.order.service.impl;

import com.robin.entity.Orders;
import com.robin.entity.ShoppingCartVO;
import com.robin.order.dao.OrderAddMapper;
import com.robin.order.service.OrderAddService;
import com.robin.order.service.feign.StockQueryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderAddServiceImpl implements OrderAddService {
    @Autowired
    private StockQueryClient stockQueryClient;

    @Autowired
    private OrderAddMapper orderAddMapper;

    @Override
    public List<ShoppingCartVO> addOrder(Orders order, String cids) {
        //根据cids调用stock-query得到购物车列表
        List<ShoppingCartVO> shoppingCartVOList = stockQueryClient.query(cids);
        if(shoppingCartVOList == null || shoppingCartVOList.size() == 0){
            return null;
        }
        boolean isEnough = true;
        String untitled = "";
        for (ShoppingCartVO shoppingCartVO : shoppingCartVOList) {
            if(Integer.parseInt(shoppingCartVO.getCartNum()) > shoppingCartVO.getSkuStock()){
                isEnough = false;
                break;
            }
            untitled += shoppingCartVO.getProductName() + " ";
        }
        if(isEnough) {
            //保存订单
            order.setUntitled(untitled);
            order.setStatus("1");
            order.setCreateTime(new Date());
            int i = orderAddMapper.insert(order);
            if(i > 0){
                return shoppingCartVOList;
            }
        }
        return null;
    }
}
