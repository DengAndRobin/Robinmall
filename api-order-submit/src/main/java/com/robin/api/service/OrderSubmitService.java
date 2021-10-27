package com.robin.api.service;

import com.robin.beans.Orders;

import java.util.Map;

public interface OrderSubmitService {

    public Map<String,String> addOrder(String cids, Orders order);
}
