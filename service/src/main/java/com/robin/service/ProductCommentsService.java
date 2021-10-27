package com.robin.service;

import com.robin.vo.ResultVO;

public interface ProductCommentsService {
    //查询评论分类显示
    public ResultVO listCommentsByProductId(String productId,int pageNum,int limit);

    //查询评论总数
    public ResultVO getCommentsCountByProductId(String productId);
}
