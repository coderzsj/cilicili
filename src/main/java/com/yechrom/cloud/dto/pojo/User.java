package com.yechrom.cloud.dto.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class User {
    Integer id;
    String  username;
    String  password;
    String  identity;
}
