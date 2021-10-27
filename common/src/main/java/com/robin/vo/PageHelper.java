package com.robin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageHelper<T>{

    //总记录条数
    private int totalCount;

    //总页数
    private int pageCount;

    //分页数据
    private List<T> list;
}
