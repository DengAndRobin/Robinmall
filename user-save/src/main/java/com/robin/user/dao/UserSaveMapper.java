package com.robin.user.dao;

import com.robin.entity.Users;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface UserSaveMapper extends Mapper<Users>, MySqlMapper<Users> {

}
