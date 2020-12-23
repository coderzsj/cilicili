package com.yechrom.cloud.dto.vo;

import lombok.Data;

/**
 * 添加用户接口的接收vo
 */
@Data
public class AddUserVo {
    private String username;
    private String password;
    private String identity;
}
