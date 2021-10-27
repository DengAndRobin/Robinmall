package com.robin.controller;

import com.robin.entity.UserAddr;
import com.robin.service.UserAddrService;
import com.robin.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/useraddr")
@Api(value = "提供用户收获地址相关接口",tags = "收货地址管理")
public class UserAddrController {

    @Autowired
    private UserAddrService userAddrService;

    @GetMapping("/list")
    @ApiOperation("查询用户收货地址接口")
    @ApiImplicitParam(dataType = "int",name = "userId",value = "用户ID",required = true)
    public ResultVO listUserAddrs(Integer userId,
                                  @RequestHeader("token")String token){
        return userAddrService.listAddrsByUserId(userId);
    }

    @PostMapping("/add")
    @ApiOperation("添加用户收货地址接口")
    public ResultVO addUserAddr(@RequestBody UserAddr userAddr,
                                @RequestHeader("token")String token){
        return userAddrService.addAddr(userAddr);
    }

    @PutMapping("/update")
    @ApiOperation("修改用户收货地址接口")
    public ResultVO updateUserAddr(@RequestBody UserAddr userAddr,
                                @RequestHeader("token")String token){
        return userAddrService.updateAddr(userAddr);
    }

    @PutMapping("/delete")
    @ApiOperation("删除用户收货地址接口")
    @ApiImplicitParam(dataType = "int",name = "addId",value = "用户地址ID",required = true)
    public ResultVO deleteUserAddr(Integer addId,
                                  @RequestHeader("token")String token){
        return userAddrService.deleteAddr(addId);
    }

    @PutMapping("/setcommon")
    @ApiOperation("设置用户默认收货地址接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "addId",value = "用户地址ID",required = true),
            @ApiImplicitParam(dataType = "int",name = "userId",value = "用户ID",required = true)
    })
    public ResultVO setCommonAddr(Integer addId,Integer userId,
                                   @RequestHeader("token")String token){
        return userAddrService.setCommonAddr(userId, addId);
    }
}
