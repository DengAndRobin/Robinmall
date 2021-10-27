package com.robin.controller;

import com.robin.service.ProductCommentsService;
import com.robin.service.ProductService;
import com.robin.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/product")
@Api(value = "提供商品信息的相关接口",tags = "商品管理")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCommentsService productCommentsService;

    @GetMapping("/detail-info/{pid}")
    @ApiOperation("商品基本信息查询接口")
    public ResultVO getProductBasicInfo(@PathVariable("pid") String pid){
        return productService.getProductBasicInfo(pid);
    }

    @GetMapping("/detail-params/{pid}")
    @ApiOperation("商品参数信息查询接口")
    public ResultVO getProductParamsById(@PathVariable("pid") String pid){
        return productService.getProductParamsById(pid);
    }

    @GetMapping("/detail-commonts/{pid}")
    @ApiOperation("商品评论信息查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true)
    })
    public ResultVO getProductComments(@PathVariable("pid") String pid,int pageNum,int limit){
        return productCommentsService.listCommentsByProductId(pid, pageNum, limit);
    }

    @GetMapping("/detail-commontscount/{pid}")
    @ApiOperation("商品评论统计查询接口")
    public ResultVO getProductCommentsCount(@PathVariable("pid") String pid){
        return productCommentsService.getCommentsCountByProductId(pid);
    }

    @GetMapping("/listbycid/{cid}")
    @ApiOperation("根据类别查询商品列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true)
    })
    public ResultVO getProductsByCategoryId(@PathVariable("cid") int categoryId,
                                            int pageNum,int limit){
        return productService.getProductsByCategoryId(categoryId, pageNum, limit);
    }

    @GetMapping("/listbrandsbycid/{cid}")
    @ApiOperation("根据类别查询商品品牌接口")
    public ResultVO getBrandsByCategoryId(@PathVariable("cid") int categoryId){
        return productService.getBrandsByCategoryId(categoryId);
    }

    @GetMapping("/listbycidandbrand/{cid}")
    @ApiOperation("根据类别和品牌查询商品列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true),
            @ApiImplicitParam(dataType = "String",name = "brand",value = "商品品牌",required = true)
    })
    public ResultVO getProductsByCategoryIdAndBrand(@PathVariable("cid") int categoryId,
                                                    String brand, int pageNum,int limit){
        return productService.getProductsByCategoryIdAndBrand(categoryId, brand, pageNum, limit);
    }

    @GetMapping("/listbykeyword")
    @ApiOperation("根据关键字搜索商品列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "keyWord",value = "关键字",required = true),
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true)
    })
    public ResultVO getProductsByCategoryId(String keyWord, int pageNum,int limit){
        return productService.getProductsByKeyWord(keyWord, pageNum, limit);
    }

    @GetMapping("/listbrandsbykw")
    @ApiOperation("根据关键字查询相关商品品牌接口")
    public ResultVO getBrandsByKeyWord(String keyWord){
        return productService.getBrandsByKeyWord(keyWord);
    }
}
