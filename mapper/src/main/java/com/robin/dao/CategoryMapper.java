package com.robin.dao;

import com.robin.entity.Category;
import com.robin.entity.CategoryVO;
import com.robin.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends GeneralDao<Category> {


    public List<CategoryVO> selectAllCategories(int parentId);

    //查询一级类别
    public List<CategoryVO> selectFirstLevelCategories();
}