package com.robin.service;

import com.robin.vo.ResultVO;

public interface CategoryService {

    public ResultVO listCategories();

    //查询一级商品的前六个销量最高的商品
    public ResultVO listFirstLevelCategories();
}
