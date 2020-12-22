package com.yechrom.cloud.dto.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment {
    private Integer id;

    private Integer videoId;

    private Integer userId;

    private String content;

    private Date sendtime;

    private Integer countLike;
}
