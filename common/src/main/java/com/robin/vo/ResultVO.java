package com.robin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Result对象",description = "返回给前端的结果对象")
public class ResultVO {
    @ApiModelProperty("响应状态码")
    //响应给前端的状态码
    private int code;
    @ApiModelProperty("响应提示信息")
    //响应给前端的状态信息
    private String msg;
    @ApiModelProperty("响应数据")
    //响应给前端的数据
    private Object data;
}
