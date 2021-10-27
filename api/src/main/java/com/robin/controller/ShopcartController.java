package com.robin.controller;

import com.robin.entity.ShoppingCart;
import com.robin.service.ShoppingCartService;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/shopcart")
@Api(value = "提供购物车业务的相关接口",tags = "购物车管理")
public class ShopcartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("添加购物车接口")
    public ResultVO addShoppingCart(@RequestBody ShoppingCart cart,
                                    @RequestHeader("token")String token){
        return shoppingCartService.addShoppingCart(cart);
    }

    @GetMapping("/list")
    @ApiOperation("查询用户购物车列表接口")
    @ApiImplicitParam(dataType = "int",name = "userId",value = "用户ID",required = true)
    public ResultVO listShoppingCartsByUserId(Integer userId,
                                              @RequestHeader("token")String token){
        return shoppingCartService.listShoppingCartsByUserId(userId);
    }

    @PutMapping("/update/{cid}/{cnum}")
    @ApiOperation("修改购物车数量接口")
    public ResultVO updateCartNum(@PathVariable("cid")Integer cartId,
                                  @PathVariable("cnum")Integer cartNum,
                                  @RequestHeader("token")String token){
        return shoppingCartService.updateCartNum(cartId, cartNum);
    }

    @DeleteMapping("/update/{cid}")
    @ApiOperation("删除购物车接口")
    public ResultVO deleteShoppingCart(@PathVariable("cid")Integer cartId,
                                  @RequestHeader("token")String token){
        return shoppingCartService.deleteShoppingCart(cartId);
    }

    @GetMapping("/listbycids")
    @ApiOperation("查询用户勾选的购物车列表接口")
    @ApiImplicitParam(dataType = "string",name = "cartIds",value = "勾选购物车的id集合",required = true)
    public ResultVO listShoppingCartsByCartIds(String cartIds,
                                              @RequestHeader("token")String token){
        return shoppingCartService.listShoppingCartsByCartIds(cartIds);
    }
}
