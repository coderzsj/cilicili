package com.yechrom.cloud.dto.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class Barrage implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer videoId;

    private ZonedDateTime sendtime;

    private String content;

    private String color;

    private Integer offtime;

    private Byte position;
}
