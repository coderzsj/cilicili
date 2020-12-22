package com.yechrom.cloud.dto.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@TableName("videos")
public class Video {
    private Integer id;
    private String title;
    private String url;
    private Integer uploadUserid;
    private Date uploadTime;
    private Integer countPlay;
    private Integer countLike;
    private String picUrl;
    private String description;
}
