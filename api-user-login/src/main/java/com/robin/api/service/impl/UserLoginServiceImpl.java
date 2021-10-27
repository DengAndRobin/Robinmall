package com.robin.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robin.api.service.UserLoginService;
import com.robin.api.service.feign.UserCheckClient;
import com.robin.beans.Users;
import com.robin.utils.MD5Utils;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserCheckClient userCheckClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public ResultVO loginCheck(String username, String password) {
        Users user = userCheckClient.check(username);

        if(user == null){
            return new ResultVO(ResStatus.NO,"username Not Found!",null);
        }
        String md5 = MD5Utils.md5(password);
        if(!md5.equals(user.getPassword())){
            return new ResultVO(ResStatus.NO,"登录失败，密码错误！",null);
        }

        JwtBuilder builder = Jwts.builder();
        String token = builder.setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setId(user.getUserId() + "")
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256,"robin6666")
                .compact();
        try {
            stringRedisTemplate.boundValueOps(token).set(objectMapper.writeValueAsString(user),30, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResultVO(ResStatus.YES,token,user);
    }

}
