package com.robin.api.service.feign;

import com.robin.api.service.feign.fallback.OrderItemAddFeignFallBack;
import com.robin.beans.ShoppingCartVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "orderItem-add",fallback = OrderItemAddFeignFallBack.class)
public interface OrderItemAddFeign {

    @PostMapping("/item/save")
    public int save(List<ShoppingCartVO> shoppingCartVOS,@RequestParam("orderId") String orderId);
}
