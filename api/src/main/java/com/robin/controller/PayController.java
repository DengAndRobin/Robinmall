package com.robin.controller;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.robin.service.OrderService;
import com.robin.vo.ResultVO;
import com.robin.websocket.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/pay")
@Api(value = "提供支付回调的接口",tags = "支付管理")
public class PayController {

    @Autowired
    private OrderService orderService;
    @ApiOperation("用户支付后的回调接口")
    @PostMapping("/callback")
    public String paySuccess(HttpServletRequest request) throws Exception {
        ServletInputStream is = request.getInputStream();
        byte[] bs = new byte[1024];
        int len = -1;
        StringBuilder builder = new StringBuilder();
        while ((len = is.read(bs)) != -1){
            builder.append(new String(bs,0,len));
        }
        String res = builder.toString();
        Map<String, String> map = WXPayUtil.xmlToMap(res);
        if(map != null && "success".equalsIgnoreCase(map.get("result_code"))){
            ResultVO resultVO = orderService.updateOrderStatus(map.get("out_trade_no"), "2");
            if ("update success".equals(resultVO.getMsg())){
                HashMap<String,String> resp = new HashMap<>();
                resp.put("return_code","success");
                resp.put("return_msg", "OK");
                resp.put("appid", map.get("appid"));
                resp.put("result_code", "success");
                WebSocketServer.sendMsg(map.get("out_trade_no"),"1");
                return WXPayUtil.mapToXml(resp);
            }
        }
        return null;

    }
}
