package com.robin.dao;

import com.robin.entity.IndexImg;
import com.robin.general.GeneralDao;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexImgMapper extends GeneralDao<IndexImg> {

    @Select("select img_id,\n" +
            "       img_url,\n" +
            "       img_bg_color,\n" +
            "       prod_id,\n" +
            "       category_id,\n" +
            "       index_type,\n" +
            "       seq,\n" +
            "       status,\n" +
            "       create_time,\n" +
            "       update_time\n" +
            "from index_img\n" +
            "where status = 1\n" +
            "order by seq")
    public List<IndexImg> getIndexImgs();
}