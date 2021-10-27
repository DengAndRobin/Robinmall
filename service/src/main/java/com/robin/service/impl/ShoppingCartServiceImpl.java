package com.robin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robin.dao.ShoppingCartMapper;
import com.robin.entity.ShoppingCart;
import com.robin.entity.ShoppingCartVO;
import com.robin.service.ShoppingCartService;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public ResultVO addShoppingCart(ShoppingCart cart) {
        cart.setCartTime(sdf.format(new Date()));
        int i = shoppingCartMapper.insert(cart);
        if(i <= 0){
            return new ResultVO(ResStatus.NO,"fail",null);
        }
        return new ResultVO(ResStatus.YES,"success",null);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO listShoppingCartsByUserId(int userId) {
        List<ShoppingCartVO> shoppingCartVOList =
                shoppingCartMapper.selectShoppingCartByUserId(userId);
        return new ResultVO(ResStatus.YES,"success",shoppingCartVOList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
    public ResultVO updateCartNum(int cartId, int cartNum) {
        int i = shoppingCartMapper.updateCartNumByCartId(cartId,cartNum);
        if(i <= 0){
            return new ResultVO(ResStatus.NO,"update fail",null);
        }
        return new ResultVO(ResStatus.YES,"update success",null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
    public ResultVO deleteShoppingCart(int cartId) {
        int i = shoppingCartMapper.deleteShoppingCartByCartId(cartId);
        if(i <= 0){
            return new ResultVO(ResStatus.NO,"delete fail",null);
        }
        return new ResultVO(ResStatus.YES,"delete success",null);
    }

    @Override
    public ResultVO listShoppingCartsByCartIds(String cartIds) {
        String[] split = cartIds.split(",");
        ArrayList<Integer> cardIds = new ArrayList<>();
        for(int i = 0;i < split.length;i++){
            cardIds.add(Integer.parseInt(split[i]));
        }
        List<ShoppingCartVO> shoppingCartVOList = shoppingCartMapper.selectShoppingCartByCartIds(cardIds);
        return new ResultVO(ResStatus.YES,"success",shoppingCartVOList);
    }
}
