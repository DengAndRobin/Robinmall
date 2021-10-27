package com.robin.dao;

import com.robin.entity.Product;
import com.robin.entity.ProductVO;
import com.robin.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper extends GeneralDao<Product> {

    //根据上线日期找出新品推荐
    public List<ProductVO> selectRecommendProducts();

    //根据销量查找前6的商品0
    public List<ProductVO> selectTop6ByCategoryId(int categoryId);

    /**
     * 根据级别id分页查找出商品
     * @param categoryId
     * @param start
     * @param limit
     * @return
     */
    public List<ProductVO> selectProductByCategoryId(@Param("categoryId") int categoryId,
                                                     @Param("start") int start,
                                                     @Param("limit") int limit);

    /**
     * 根据类别id查询商品的所有品牌
     * @param categoryId
     * @return
     */
    public List<String> selectBrandsByCategoryId(int categoryId);

    /**
     * 根据级别id和品牌分页查找出商品
     * @param categoryId
     * @param brand
     * @param start
     * @param limit
     * @return
     */
    public List<ProductVO> selectProductByCategoryIdAndBrand(@Param("categoryId") int categoryId,
                                                     @Param("brand") String brand,
                                                     @Param("start") int start,
                                                     @Param("limit") int limit);
    /**
     * 根据级别id和品牌分页查找出商品总数量
     * @param categoryId
     * @param brand
     * @return
     */
    public int selectProductCountByCategoryIdAndBrand(@Param("categoryId") int categoryId,
                                                             @Param("brand") String brand);

    /**
     * 根据关键字分页查找出商品
     * @param keyWord
     * @param start
     * @param limit
     * @return
     */
    public List<ProductVO> selectProductByKeyWord(@Param("keyWord")String keyWord,
                                                     @Param("start") int start,
                                                     @Param("limit") int limit);

    /**
     * 根据关键字查询相关商品的所有品牌
     * @param keyWord
     * @return
     */
    public List<String> selectBrandsByKeyWord(String keyWord);
}