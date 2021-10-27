package com.robin.api.service.feign;

import com.robin.api.service.feign.fallback.UserCheckClientFallBack;
import com.robin.beans.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-check",fallback = UserCheckClientFallBack.class)
public interface UserCheckClient {

    @GetMapping("/user/check")
    public Users check(@RequestParam("username") String username);
}
