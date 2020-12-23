package com.yechrom.cloud.dto.vo;

import lombok.Data;

/**
 * 获取用户信息接口 返回的
 */
@Data
public class ShowUserVo {

    private Integer id;
    private String username;
    private String password;
    private String identity;
}
