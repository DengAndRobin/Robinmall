package com.robin.api.controller;

import com.robin.api.service.UserRegistService;
import com.robin.beans.Users;
import com.robin.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Api(value = "提供注册接口",tags = "用户管理")
public class UserRegistController {

    @Autowired
    private UserRegistService userRegistService;
    @ApiOperation("用户注册接口")
    @PostMapping("/user/regist")
    public ResultVO regist(@RequestBody Users user){
        return userRegistService.regist(user.getUsername(), user.getPassword());
    }
}
