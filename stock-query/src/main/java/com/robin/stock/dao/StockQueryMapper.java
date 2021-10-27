package com.robin.stock.dao;

import com.robin.entity.ShoppingCartVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

@Repository
public interface StockQueryMapper extends Mapper<ShoppingCartVO>, MySqlMapper<ShoppingCartVO> {

    public List<ShoppingCartVO> selectShoppingCartByCartIds(@Param("cids") List<Integer> cids);
}
