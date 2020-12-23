package com.yechrom.cloud.dto.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String  username;
    String  password;
    String  identity;
}
