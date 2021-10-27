package com.robin.user.service.impl;

import com.robin.entity.Users;
import com.robin.user.dao.UserSaveMapper;
import com.robin.user.service.UserSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSaveServiceImpl implements UserSaveService {

    @Autowired
    private UserSaveMapper userSaveMapper;
    @Override
    public int save(Users users) {
        return userSaveMapper.insertUseGeneratedKeys(users);
    }
}
