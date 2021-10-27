package com.robin.controller;

import com.robin.service.UserService;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "提供用户的登录和注册接口",tags = "用户管理")
//允许数据跨域请求
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",required = true,name = "username",value = "用户登录账号"),
            @ApiImplicitParam(dataType = "String",required = true,name = "password",value = "用户登录密码")
    })
    @GetMapping("/login")
    public ResultVO login(String username, String password){
        return userService.loginCheck(username, password);
    }

    @ApiOperation("用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",required = true,name = "username",value = "用户注册账号"),
            @ApiImplicitParam(dataType = "String",required = true,name = "password",value = "用户注册密码")
    })
    @PostMapping("/regist")
    public ResultVO regist(String username,String password){
        return userService.userRegist(username, password);
    }

    @ApiOperation("检查用户token是否过期接口")
    @GetMapping("/check")
    public ResultVO userTokenCheck(@RequestHeader("token")String token){
        return new ResultVO(ResStatus.YES,"success",null);
    }

    @GetMapping("/userdetail")
    @ApiOperation("查询用户信息接口")
    @ApiImplicitParam(dataType = "int",required = true,name = "userId",value = "用户id")
    public ResultVO getUser(Integer userId){
        return userService.getUserByUserId(userId);
    }

}
