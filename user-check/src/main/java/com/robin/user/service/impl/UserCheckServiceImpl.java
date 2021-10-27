package com.robin.user.service.impl;

import com.robin.entity.Users;
import com.robin.user.dao.UserCheckMapper;
import com.robin.user.service.UserCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserCheckServiceImpl implements UserCheckService {

    @Autowired
    private UserCheckMapper userCheckMapper;
    @Override
    public Users queryUser(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        List<Users> users = userCheckMapper.selectByExample(example);
        if(users.size()>0){
            return users.get(0);
        }else{
            return null;
        }
    }
}
