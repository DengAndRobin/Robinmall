package com.robin.api.controller;

import com.robin.api.service.UserLoginService;
import com.robin.vo.ResultVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;
    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",required = true,name = "username",value = "用户登录账号"),
            @ApiImplicitParam(dataType = "String",required = true,name = "password",value = "用户登录密码")
    })
    @GetMapping("/user/login")
    public ResultVO login(String username, String password){
        return userLoginService.loginCheck(username, password);
    }
}
