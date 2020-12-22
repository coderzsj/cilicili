package com.yechrom.cloud.dto.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("电子凭证查询结果DTO")
public class ShowAllSellHouseVo {

    private int limit;
    private int page;
}
