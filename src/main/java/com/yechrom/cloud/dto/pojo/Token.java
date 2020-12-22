package com.yechrom.cloud.dto.pojo;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Token {
    private Integer id;

    private Integer userId;

    private String token;

    private ZonedDateTime applytime;

    private ZonedDateTime expiretime;

    private Integer countAuth;

    private Integer maxCountAuth;

    private String ussage;
}
