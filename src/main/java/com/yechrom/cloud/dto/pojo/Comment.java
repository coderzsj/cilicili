package com.yechrom.cloud.dto.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("comments")
public class Comment {
    @TableId(value = "id",type = IdType.AUTO )
    private Integer id;

    private Integer videoId;

    private Integer userId;

    private String content;

    private Date sendtime;

    private Integer countLike;
}
