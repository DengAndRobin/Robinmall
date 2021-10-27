package com.robin.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是预请求,直接放行响应
        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }
        String token = request.getHeader("token");
        if(token == null){
            ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_NOT, "请先登录账号！", null);
            doResponse(response, resultVO);
        }else{
            String s = stringRedisTemplate.boundValueOps(token).get();
            if(s == null){
                ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_NOT, "请先登录账号！", null);
                doResponse(response, resultVO);
            }else {
                stringRedisTemplate.boundValueOps(token).expire(30, TimeUnit.MINUTES);
                return true;
            }
        }
        return false;
    }

    private void doResponse(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        String msg = new ObjectMapper().writeValueAsString(resultVO);
        printWriter.write(msg);
        printWriter.flush();
        printWriter.close();
    }
}
