package com.yechrom.cloud.dto.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;
@Data
public class Video {
    private Integer id;
    private String title;
    private String url;
    private Integer uploadUserid;
    private ZonedDateTime uploadTime;
    private Integer countPlay;
    private Integer countLike;
    private String picUrl;
    private String description;
}
