package com.robin.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robin.dao.CategoryMapper;
import com.robin.entity.Category;
import com.robin.entity.CategoryVO;
import com.robin.service.CategoryService;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 查询分类列表（包含三个等级的分类）
     * @return
     */
    @Override
    public ResultVO listCategories() {
        List<CategoryVO> categoryVOList = null;
        try {
            String categoriesStr = stringRedisTemplate.boundValueOps("categories").get();
            if(categoriesStr != null){
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, CategoryVO.class);
                categoryVOList = objectMapper.readValue(categoriesStr, javaType);
            }else {
                categoryVOList = categoryMapper.selectAllCategories(0);
                stringRedisTemplate.boundValueOps("categories").set(objectMapper.writeValueAsString(categoryVOList));
                stringRedisTemplate.boundValueOps("categories").expire(1, TimeUnit.DAYS);
            }
        }catch (Exception e){

        }
        return new ResultVO(ResStatus.YES,"success",categoryVOList);
    }

    /**
     * 查询一级分类下销量前六的商品
     * @return
     */
    @Override
    public ResultVO listFirstLevelCategories() {
        List<CategoryVO> categoryVOList = categoryMapper.selectFirstLevelCategories();
        return new ResultVO(ResStatus.YES,"success",categoryVOList);
    }
}
