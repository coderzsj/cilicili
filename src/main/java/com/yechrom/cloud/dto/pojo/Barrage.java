package com.yechrom.cloud.dto.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("barrages")
public class Barrage implements Serializable {
    @TableId(value = "id",type = IdType.AUTO )
    private Integer id;

    private Integer userId;

    private Integer videoId;

    private Date sendtime;

    private String content;

    private String color;

    private Integer offtime;

    private Byte position;
}
