package com.robin.api.service;

import com.robin.vo.ResultVO;

public interface UserLoginService {
    //用户登录
    public ResultVO loginCheck(String username, String password);
}
