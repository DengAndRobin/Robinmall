package com.robin.controller;

import com.robin.service.CategoryService;
import com.robin.service.IndexImgService;
import com.robin.service.ProductService;
import com.robin.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/index")
@Api(value = "提供首页显示数据的接口",tags = "首页管理")
public class IndexController {

    @Autowired
    private IndexImgService indexImgService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @ApiOperation("首页轮播图接口")
    @GetMapping("/indexImg")
    public ResultVO listIndexImgs(){
        return indexImgService.listIndexImgs();
    }

    @ApiOperation("商品分类查询接口")
    @GetMapping("/category-list")
    public ResultVO listCategories(){
        return categoryService.listCategories();
    }

    @ApiOperation("新品推荐接口")
    @GetMapping("/list-recommends")
    public ResultVO listRecommendProducts(){
        return productService.listRecommendProducts();
    }

    @ApiOperation("商品分类推荐接口")
    @GetMapping("/category-recommends")
    public ResultVO listRecommendProductsByCategoryId(){
        return categoryService.listFirstLevelCategories();
    }
}
