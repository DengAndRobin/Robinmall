package com.robin.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robin.dao.ProductImgMapper;
import com.robin.dao.ProductMapper;
import com.robin.dao.ProductParamsMapper;
import com.robin.dao.ProductSkuMapper;
import com.robin.entity.*;
import com.robin.service.ProductService;
import com.robin.vo.PageHelper;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImgMapper productImgMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductParamsMapper productParamsMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResultVO listRecommendProducts() {
        List<ProductVO> productVOList = productMapper.selectRecommendProducts();
        return new ResultVO(ResStatus.YES,"success",productVOList);
    }

    @Override
    public ResultVO getProductBasicInfo(String productId) {
        try{
            String productInfo = (String) stringRedisTemplate.boundHashOps("products").get(productId);
            if(productInfo != null){
                Product product = objectMapper.readValue(productInfo, Product.class);
                String imgStr = (String) stringRedisTemplate.boundHashOps("productImgs").get(productId);
                JavaType javaType1 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductImg.class);
                List<ProductImg> productImgs = objectMapper.readValue(imgStr, javaType1);
                String skuStr = (String) stringRedisTemplate.boundHashOps("productSkus").get(productId);
                JavaType javaType2 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductSku.class);
                List<ProductSku> productSkus = objectMapper.readValue(skuStr, javaType2);

                HashMap<String, Object> basicInfo = new HashMap<>();
                basicInfo.put("product", product);
                basicInfo.put("productImgs", productImgs);
                basicInfo.put("productSkus", productSkus);
                return new ResultVO(ResStatus.YES,"success",basicInfo);
            }
            Example productExample = new Example(Product.class);
            Example.Criteria productExampleCriteria = productExample.createCriteria();
            productExampleCriteria.andEqualTo("productId",productId);
            productExampleCriteria.andEqualTo("productStatus",1);
            List<Product> productList = productMapper.selectByExample(productExample);
            if(productList == null || productList.size() == 0){
                return new ResultVO(ResStatus.NO,"查询的商品不存在！",null);
            }
            //将商品信息存入到redis
            stringRedisTemplate.boundHashOps("products").put(productId,objectMapper.writeValueAsString(productList.get(0)));
            //如果查询到了商品，查询商品图片
            Example imgExample = new Example(ProductImg.class);
            Example.Criteria imgExampleCriteria = imgExample.createCriteria();
            imgExampleCriteria.andEqualTo("itemId",productId);
            List<ProductImg> productImgList = productImgMapper.selectByExample(imgExample);
            //将商品图片存入到redis
            stringRedisTemplate.boundHashOps("productImgs").put(productId,objectMapper.writeValueAsString(productImgList));
            //查询商品套餐
            Example skuExample = new Example(ProductSku.class);
            Example.Criteria skuExampleCriteria = skuExample.createCriteria();
            skuExampleCriteria.andEqualTo("productId",productId);
            skuExampleCriteria.andEqualTo("status",1);
            List<ProductSku> productSkuList = productSkuMapper.selectByExample(skuExample);
            stringRedisTemplate.boundHashOps("productSkus").put(productId, objectMapper.writeValueAsString(productSkuList));
            HashMap<String, Object> basicInfo = new HashMap<>();
            basicInfo.put("product", productList.get(0));
            basicInfo.put("productImgs", productImgList);
            basicInfo.put("productSkus", productSkuList);
            return new ResultVO(ResStatus.YES,"success",basicInfo);
        }catch (Exception e){
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }

    @Override
    public ResultVO getProductParamsById(String productId) {
        Example example = new Example(ProductParams.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        List<ProductParams> productParamsList = productParamsMapper.selectByExample(example);
        if(productParamsList == null || productParamsList.size() == 0){
            return new ResultVO(ResStatus.NO,"此商品无参数信息",null);
        }
        return new ResultVO(ResStatus.YES,"success",productParamsList.get(0));
    }

    @Override
    public ResultVO getProductsByCategoryId(int categoryId, int pageNum, int limit) {
        int start = (pageNum - 1 ) * limit;
        List<ProductVO> productVOList = productMapper.selectProductByCategoryId(categoryId, start, limit);
        //查找出总记录数和总页数
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId",categoryId);
        int totalCount = productMapper.selectCountByExample(example);
        int pageCount = totalCount % limit == 0 ? totalCount/limit : (totalCount / limit) + 1;
        return new ResultVO(ResStatus.YES,"success",
                new PageHelper<ProductVO>(totalCount,pageCount,productVOList));
    }

    @Override
    public ResultVO getBrandsByCategoryId(int categoryId) {
        List<String> brands = productMapper.selectBrandsByCategoryId(categoryId);
        return new ResultVO(ResStatus.YES,"success",brands);
    }

    @Override
    public ResultVO getProductsByCategoryIdAndBrand(int categoryId,String brand,int pageNum,int limit) {
        int start = (pageNum - 1 ) * limit;
        List<ProductVO> productVOList = productMapper.selectProductByCategoryIdAndBrand(categoryId, brand, start, limit);
        //查找出总记录数和总页数
        int totalCount = productMapper.selectProductCountByCategoryIdAndBrand(categoryId, brand);
        int pageCount = totalCount % limit == 0 ? totalCount/limit : (totalCount / limit) + 1;
        return new ResultVO(ResStatus.YES,"success",
                new PageHelper<ProductVO>(totalCount,pageCount,productVOList));
    }

    @Override
    public ResultVO getProductsByKeyWord(String keyWord, int pageNum, int limit) {
        int start = (pageNum - 1 ) * limit;
        keyWord = "%" + keyWord + "%";
        List<ProductVO> productVOList = productMapper.selectProductByKeyWord(keyWord, start, limit);
        //查找出总记录数和总页数
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("productName",keyWord);
        int totalCount = productMapper.selectCountByExample(example);
        int pageCount = totalCount % limit == 0 ? totalCount/limit : (totalCount / limit) + 1;
        return new ResultVO(ResStatus.YES,"success",
                new PageHelper<ProductVO>(totalCount,pageCount,productVOList));

    }

    @Override
    public ResultVO getBrandsByKeyWord(String keyWord) {
        keyWord = "%"+keyWord+"%";
        List<String> brands = productMapper.selectBrandsByKeyWord(keyWord);
        return new ResultVO(ResStatus.YES,"success",brands);
    }
}
