package com.robin;

import com.robin.dao.CategoryMapper;
import com.robin.dao.ProductCommentsMapper;
import com.robin.dao.ProductMapper;
import com.robin.entity.CategoryVO;
import com.robin.entity.ProductCommentsVO;
import com.robin.entity.ProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class CategoryDaoTest {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductCommentsMapper productCommentsMapper;
    @Test
    public void selectAllCategories(){
        List<CategoryVO> categoryVOList = categoryMapper.selectAllCategories(0);

        for (CategoryVO c1 : categoryVOList) {
            System.out.println(c1);
            for (CategoryVO c2 : c1.getCategories()) {
                System.out.println("\t" + c2);
                for (CategoryVO c3 : c2.getCategories()) {
                    System.out.println("\t\t" + c3);
                }
            }
            
        }
    }
    @Test
    public void selectRecommendProducts(){
        List<ProductVO> productVOList = productMapper.selectRecommendProducts();
        for (ProductVO productVO : productVOList) {
            System.out.println(productVO);
        }
    }
    @Test
    public void selectFirstLevelCategories(){
        List<CategoryVO> categoryVOList = categoryMapper.selectFirstLevelCategories();
        for (CategoryVO categoryVO : categoryVOList) {
            System.out.println(categoryVO);
        }
    }
    @Test
    public void selectCommentsByProductId(){
        /*List<ProductCommentsVO> productCommentsVOList = productCommentsMapper.selectCommentsByProductId("3");
        for (ProductCommentsVO productCommentsVO : productCommentsVOList) {
            System.out.println(productCommentsVO);
        }*/
    }

}
