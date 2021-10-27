package com.robin.api.service.feign.fallback;

import com.robin.api.service.feign.UserCheckClient;
import com.robin.beans.Users;
import org.springframework.stereotype.Component;

@Component
public class UserCheckClientFallBack implements UserCheckClient {
    @Override
    public Users check(String username) {
        return new Users();
    }
}
