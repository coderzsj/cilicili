package com.yechrom.cloud.dto.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class Comment {
    private Integer id;

    private Integer videoId;

    private Integer userId;

    private String content;

    private ZonedDateTime sendtime;

    private Integer countLike;
}
