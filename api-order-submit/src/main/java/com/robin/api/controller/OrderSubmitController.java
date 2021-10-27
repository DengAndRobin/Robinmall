package com.robin.api.controller;

import com.github.wxpay.sdk.WXPay;
import com.robin.api.config.MyPayConfig;
import com.robin.api.service.OrderSubmitService;
import com.robin.beans.Orders;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@Api(value = "提供订单相关的接口",tags = "订单管理")
public class OrderSubmitController {

    @Autowired
    private OrderSubmitService orderSubmitService;
    @PostMapping("/order/add")
    @ApiOperation("保存订单和订单项接口")
    @ApiImplicitParam(dataType = "string",name = "cartIds",value = "购买的购物车id集合",required = true)
    public ResultVO addOrder(String cartIds, @RequestBody Orders order,
                             @RequestHeader("token")String token){
        try{
            Map<String, String> orderInfo = orderSubmitService.addOrder(cartIds, order);
            String orderId = orderInfo.get("orderId");

            if(orderId == null || orderId.equals("")){
                return new ResultVO(ResStatus.NO,"提交订单失败！",null);
            }
            HashMap<String,String> data = new HashMap<>();
            data.put("body",orderInfo.get("productNames")); //商品描述
            data.put("out_trade_no",orderId); //使⽤当前⽤户订单的编号作为当前⽀付交易的交易号
            data.put("fee_type","CNY"); //⽀付币种
            data.put("total_fee",order.getActualAmount()*100+""); //⽀付⾦额
            data.put("trade_type","NATIVE"); //交易类型
            data.put("notify_url","http://lrobin.free.idcfengye.com/pay/callback"); //设置⽀付完成时的回调⽅法接⼝

            WXPay wxPay = new WXPay(new MyPayConfig());
            Map<String, String> resp = wxPay.unifiedOrder(data);
            orderInfo.put("payUrl", resp.get("code_url"));
            return new ResultVO(ResStatus.YES,"提交订单成功",orderInfo);
        }catch (SQLException e){
            return new ResultVO(ResStatus.NO,"提交订单失败！",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
