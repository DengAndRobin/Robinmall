package com.robin.service.impl;

import com.robin.dao.ProductCommentsMapper;
import com.robin.entity.ProductComments;
import com.robin.entity.ProductCommentsVO;
import com.robin.service.ProductCommentsService;
import com.robin.vo.PageHelper;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductCommentsServiceImpl implements ProductCommentsService {

    @Autowired
    private ProductCommentsMapper productCommentsMapper;
    @Override
    public ResultVO listCommentsByProductId(String productId,int pageNum,int limit) {
        //1返回总记录数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int totalCount = productCommentsMapper.selectCountByExample(example);
        //2计算总页数
        int pageCount = totalCount % limit == 0? totalCount/limit : (totalCount/limit) + 1;
        //3查询分页数据
        int start = (pageNum-1)*limit;
        List<ProductCommentsVO> productCommentsVOList = productCommentsMapper.selectCommentsByProductId(productId, start, limit);
        return new ResultVO(ResStatus.YES,"success",new PageHelper<ProductCommentsVO>(totalCount,pageCount,productCommentsVOList));
    }

    @Override
    public ResultVO getCommentsCountByProductId(String productId) {
        //计算评价总数
        Example totalCountExample = new Example(ProductComments.class);
        Example.Criteria totalCountCriteria = totalCountExample.createCriteria();
        totalCountCriteria.andEqualTo("productId",productId);
        int totalCount = productCommentsMapper.selectCountByExample(totalCountExample);
        //查询好评数
        totalCountCriteria.andEqualTo("commType",1);
        int goodTotal = productCommentsMapper.selectCountByExample(totalCountExample);
        //查询中评数
        Example midExample = new Example(ProductComments.class);
        Example.Criteria midExampleCriteria = midExample.createCriteria();
        midExampleCriteria.andEqualTo("productId",productId);
        midExampleCriteria.andEqualTo("commType",0);
        int midTotal = productCommentsMapper.selectCountByExample(midExample);
        //查询差评数
        Example badExample = new Example(ProductComments.class);
        Example.Criteria badExampleCriteria = badExample.createCriteria();
        badExampleCriteria.andEqualTo("productId",productId);
        badExampleCriteria.andEqualTo("commType",-1);
        int badTotal = productCommentsMapper.selectCountByExample(badExample);
        //计算好评率
        double goodPercent = (double)(goodTotal) / (double) (totalCount) * 100;
        String goodPercentValue = (goodPercent+"").substring(0,(goodPercent+"").lastIndexOf(".")+3);
        HashMap<String,Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("goodTotal",goodTotal);
        map.put("midTotal",midTotal);
        map.put("badTotal",badTotal);
        map.put("goodPercent", goodPercentValue);
        return new ResultVO(ResStatus.YES,"success",map);
    }
}
