package com.yechrom.cloud.dto.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 添加用户接口的接收vo
 */
@Data
public class AddUserVo {
    private String id;
    private String username;
    private String password;
    private String identity;
}
