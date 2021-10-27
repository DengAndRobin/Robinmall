package com.robin.service.impl;

import com.robin.dao.UserAddrMapper;
import com.robin.entity.UserAddr;
import com.robin.service.UserAddrService;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
@Service
public class UserAddrServiceImpl implements UserAddrService {

    @Autowired
    private UserAddrMapper userAddrMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO listAddrsByUserId(int userId) {
        Example example = new Example(UserAddr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("status",1);
        List<UserAddr> userAddrList = userAddrMapper.selectByExample(example);
        return new ResultVO(ResStatus.YES,"success",userAddrList);
    }

    @Override
    @Transactional
    public ResultVO addAddr(UserAddr userAddr) {
        userAddr.setStatus(1);
        userAddr.setCreateTime(new Date());
        userAddr.setUpdateTime(new Date());
        int i = userAddrMapper.insert(userAddr);
        if(i <= 0){
            return new ResultVO(ResStatus.NO,"fail",null);
        }
        return new ResultVO(ResStatus.YES,"success",null);
    }

    @Override
    @Transactional
    public ResultVO updateAddr(UserAddr userAddr) {
        UserAddr userAddr1 = userAddrMapper.selectByPrimaryKey(userAddr.getAddrId());
        userAddr.setCreateTime(userAddr1.getCreateTime());
        userAddr.setUpdateTime(new Date());
        int i = userAddrMapper.updateByPrimaryKey(userAddr);
        if(i <= 0){
            return new ResultVO(ResStatus.NO,"fail",null);
        }
        return new ResultVO(ResStatus.YES,"success",null);

    }

    @Override
    @Transactional
    public ResultVO deleteAddr(int addrId) {
        UserAddr userAddr = new UserAddr();
        userAddr.setStatus(0);
        userAddr.setAddrId(addrId);
        int i = userAddrMapper.updateByPrimaryKeySelective(userAddr);
        if(i <= 0){
            return new ResultVO(ResStatus.NO,"fail",null);
        }
        return new ResultVO(ResStatus.YES,"success",null);
    }

    @Override
    @Transactional
    public ResultVO setCommonAddr(int userId,int addrId) {
        Example example = new Example(UserAddr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("commonAddr", 1);
        UserAddr userAddr = new UserAddr();
        userAddr.setCommonAddr(0);
        userAddrMapper.updateByExampleSelective(userAddr, example);
        userAddr.setAddrId(addrId);
        userAddr.setCommonAddr(1);
        userAddrMapper.updateByPrimaryKeySelective(userAddr);
        return new ResultVO(ResStatus.YES,"success",null);
    }


}
