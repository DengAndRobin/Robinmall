package com.robin.service;


import com.robin.vo.ResultVO;

public interface UserService {

    //用户登录
    public ResultVO loginCheck(String username,String password);

    //用户注册
    public ResultVO userRegist(String username,String password);

    /**
     * 查询用户个人信息
     * @param userId
     * @return
     */
    public ResultVO getUserByUserId(int userId);
}
