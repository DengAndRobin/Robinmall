package com.robin.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robin.dao.IndexImgMapper;
import com.robin.entity.IndexImg;
import com.robin.service.IndexImgService;
import com.robin.vo.ResStatus;
import com.robin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class IndexImgServiceImpl implements IndexImgService {

    @Autowired
    private IndexImgMapper indexImgMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public ResultVO listIndexImgs() {
        List<IndexImg> imgList = null;
        try {
            String imgStr = stringRedisTemplate.boundValueOps("indexImgs").get();
            if(imgStr != null) {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
                imgList = objectMapper.readValue(imgStr, javaType);
            }else {
                synchronized (this){
                    imgStr = stringRedisTemplate.boundValueOps("indexImgs").get();
                    if(imgStr == null){
                        imgList = indexImgMapper.getIndexImgs();
                        if(imgList == null){
                            stringRedisTemplate.boundValueOps("indexImgs").set("[]");
                            stringRedisTemplate.boundValueOps("indexImgs").expire(10, TimeUnit.SECONDS);

                        }else {
                            stringRedisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString(imgList));
                            stringRedisTemplate.boundValueOps("indexImgs").expire(1, TimeUnit.DAYS);
                        }
                    }else {
                        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
                        imgList = objectMapper.readValue(imgStr, javaType);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(imgList == null){
            return new ResultVO(ResStatus.NO,"fail",null);
        }
        return new ResultVO(ResStatus.YES,"success",imgList);
    }
}
