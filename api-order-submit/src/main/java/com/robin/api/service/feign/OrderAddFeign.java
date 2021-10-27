package com.robin.api.service.feign;

import com.robin.api.service.feign.fallback.OrderAddFeignFallBack;
import com.robin.beans.Orders;
import com.robin.beans.ShoppingCartVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "order-add",fallback = OrderAddFeignFallBack.class)
public interface OrderAddFeign {

    @PostMapping("/order/save")
    public List<ShoppingCartVO> add(@RequestBody Orders order, @RequestParam("cids") String cids);
}
