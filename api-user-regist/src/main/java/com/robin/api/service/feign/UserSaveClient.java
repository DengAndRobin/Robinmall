package com.robin.api.service.feign;

import com.robin.api.service.feign.fallback.UserSaveClientFallBack;
import com.robin.beans.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-save",fallback = UserSaveClientFallBack.class)
public interface UserSaveClient {

    @PostMapping("/user/save")
    public int save(Users user);
}
