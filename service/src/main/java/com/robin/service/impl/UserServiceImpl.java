package com.robin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robin.dao.UsersMapper;
import com.robin.entity.Users;
import com.robin.service.UserService;
import com.robin.utils.MD5Utils;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResultVO loginCheck(String username, String password) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        List<Users> users = usersMapper.selectByExample(example);

        if(users.size() == 0){
            return new ResultVO(ResStatus.NO,"登录失败，用户名不存在！",null);
        }
        String md5 = MD5Utils.md5(password);
        if(!md5.equals(users.get(0).getPassword())){
            return new ResultVO(ResStatus.NO,"登录失败，密码错误！",null);
        }

        JwtBuilder builder = Jwts.builder();
        String token = builder.setSubject(users.get(0).getUsername())
                .setIssuedAt(new Date())
                .setId(users.get(0).getUserId() + "")
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256,"robin6666")
                .compact();
        try {
            stringRedisTemplate.boundValueOps(token).set(objectMapper.writeValueAsString(users.get(0)),30,TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResultVO(ResStatus.YES,token,users.get(0));
    }

    @Transactional
    public ResultVO userRegist(String username, String password) {

        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        List<Users> users = usersMapper.selectByExample(example);

        if(users.size() == 0){
            Users user = new Users();
            user.setUsername(username);
            user.setPassword(MD5Utils.md5(password));
            user.setUserImg("img/default.png");
            user.setUserRegtime(new Date());
            user.setUserModtime(new Date());
            int i = usersMapper.insertUseGeneratedKeys(user);
            if(i > 0){
                return new ResultVO(ResStatus.YES,"注册成功！",user);
            }
            return new ResultVO(ResStatus.NO,"注册失败！",null);
        }else{
            return new ResultVO(ResStatus.NO,"用户名已存在！",null);
        }
    }

    @Override
    public ResultVO getUserByUserId(int userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        return new ResultVO(ResStatus.YES,"success",user);
    }
}
