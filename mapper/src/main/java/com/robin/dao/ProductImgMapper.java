package com.robin.dao;

import com.robin.entity.ProductImg;
import com.robin.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgMapper extends GeneralDao<ProductImg> {

    public List<ProductImg> selectProductImgByProductId(int productId);
}