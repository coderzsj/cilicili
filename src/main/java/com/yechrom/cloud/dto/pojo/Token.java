package com.yechrom.cloud.dto.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Token {
    private Integer id;

    private Integer userId;

    private String token;

    private Date applytime;

    private Date expiretime;

    private Integer countAuth;

    private Integer maxCountAuth;

    private String ussage;
}
