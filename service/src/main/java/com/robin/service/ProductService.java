package com.robin.service;

import com.robin.vo.ResultVO;

public interface ProductService{

    public ResultVO listRecommendProducts();

    public ResultVO getProductBasicInfo(String productId);

    public ResultVO getProductParamsById(String productId);

    /**
     * 根据类别id查询商品列表
     * @param categoryId
     * @param pageNum
     * @param limit
     * @return
     */
    public ResultVO getProductsByCategoryId(int categoryId,int pageNum,int limit);

    /**
     * 根据类别id查询商品品牌
     * @param categoryId
     * @return
     */
    public ResultVO getBrandsByCategoryId(int categoryId);

    /**
     * 根据类别id和品牌查询商品列表
     * @param categoryId
     * @param brand
     * @param pageNum
     * @param limit
     * @return
     */
    public ResultVO getProductsByCategoryIdAndBrand(int categoryId,String brand,int pageNum,int limit);
    /**
     * 根据关键字查询商品列表
     * @param keyWord
     * @param pageNum
     * @param limit
     * @return
     */
    public ResultVO getProductsByKeyWord(String keyWord,int pageNum,int limit);

    /**
     * 根据关键字查询相关商品品牌
     * @param keyWord
     * @return
     */
    public ResultVO getBrandsByKeyWord(String keyWord);

}
