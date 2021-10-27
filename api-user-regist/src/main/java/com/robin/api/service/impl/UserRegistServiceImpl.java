package com.robin.api.service.impl;

import com.robin.api.service.UserRegistService;
import com.robin.api.service.feign.UserCheckClient;
import com.robin.api.service.feign.UserSaveClient;
import com.robin.beans.Users;
import com.robin.utils.MD5Utils;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserRegistServiceImpl implements UserRegistService {

    @Autowired
    private UserCheckClient userCheckClient;
    @Autowired
    private UserSaveClient userSaveClient;
    @Override
    public ResultVO regist(String username, String password) {
        Users user = userCheckClient.check(username);
        if(user == null){
            user = new Users();
            user.setUsername(username);
            user.setPassword(MD5Utils.md5(password));
            user.setUserImg("img/default.png");
            user.setUserRegtime(new Date());
            user.setUserModtime(new Date());
            int i = userSaveClient.save(user);
            if(i > 0){
                return new ResultVO(ResStatus.YES,"注册成功！",user);
            }
            return new ResultVO(ResStatus.NO,"注册失败！",null);
        }else if(user.getUsername() == null){
            return new ResultVO(ResStatus.NO,"网路出现故障！请重试！",null);
        } else{
            return new ResultVO(ResStatus.NO,"用户名已存在！",null);
        }
    }
}
