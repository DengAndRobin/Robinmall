package com.robin.service;

import com.robin.entity.UserAddr;
import com.robin.vo.ResultVO;

public interface UserAddrService {
    /**
     * 查询收货地址列表
     * @param userId
     * @return
     */
    public ResultVO listAddrsByUserId(int userId);

    /**
     * 新增收货地址
     * @param userAddr
     * @return
     */
    public ResultVO addAddr(UserAddr userAddr);

    /**
     * 修改收货地址
     * @param userAddr
     * @return
     */
    public ResultVO updateAddr(UserAddr userAddr);

    /**
     * 删除收货地址
     * @param addrId
     * @return
     */
    public ResultVO deleteAddr(int addrId);

    /**
     * 设置默认地址
     * @param addrId
     * @param userId
     * @return
     */
    public ResultVO setCommonAddr(int userId,int addrId);
}
