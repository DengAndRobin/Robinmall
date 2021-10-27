package com.robin.api.service.feign.fallback;

import com.robin.api.service.feign.UserSaveClient;
import com.robin.beans.Users;
import org.springframework.stereotype.Component;

@Component
public class UserSaveClientFallBack implements UserSaveClient {
    @Override
    public int save(Users user) {
        return 0;
    }
}
