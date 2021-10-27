package com.robin.api.service.feign.fallback;

import com.robin.api.service.feign.UserCheckClient;
import com.robin.beans.Users;

public class UserCheckClientFallBack implements UserCheckClient {
    @Override
    public Users check(String username) {
        return null;
    }
}
