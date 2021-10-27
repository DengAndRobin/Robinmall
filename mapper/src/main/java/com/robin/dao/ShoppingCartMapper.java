package com.robin.dao;

import com.robin.entity.ShoppingCart;
import com.robin.entity.ShoppingCartVO;
import com.robin.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartMapper extends GeneralDao<ShoppingCart> {

    public List<ShoppingCartVO> selectShoppingCartByUserId(int userId);

    public int updateCartNumByCartId(@Param("cartId")int cartId,
                                     @Param("cartNum")int cartNum);

    public int deleteShoppingCartByCartId(int cartId);

    public List<ShoppingCartVO> selectShoppingCartByCartIds(List<Integer> cartIds);
}