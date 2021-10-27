package com.robin.service;

import com.robin.entity.Orders;
import com.robin.vo.ResultVO;

import java.sql.SQLException;
import java.util.Map;

public interface OrderService {
    //保存订单
    public Map<String, String> addOrder(String cartIds, Orders order) throws SQLException;

    //修改订单状态
    public ResultVO updateOrderStatus(String orderId,String status);

    //取消订单
    public void closeOrder(String orderId);

    public ResultVO getOrders(String userId,String status,int pageNum,int limit);

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    public ResultVO deleteOrderByOrderId(String orderId);
}
