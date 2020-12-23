package com.yechrom.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.yechrom.cloud.dto.pojo.Barrage;
import com.yechrom.cloud.dto.pojo.Video;
import com.yechrom.cloud.dto.vo.response.ResponseBaseVo;
import com.yechrom.cloud.dto.vo.response.ResponseErrorVo;
import com.yechrom.cloud.dto.vo.response.ResponseVo;
import com.yechrom.cloud.service.BarrageService;
import com.yechrom.cloud.service.UserService;
import com.yechrom.cloud.service.VideoService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/barrage")
public class BarrageController {
    @Autowired
    BarrageService barrageService;
    @Autowired
    UserService    userService;
    @Autowired
    VideoService   videoService;

    @PostMapping("/findId")
    public ResponseBaseVo showBarrages(@RequestParam("id") Integer videoId) {
        ArrayList<Barrage> barrages = (ArrayList<Barrage>) barrageService.showBarrages(videoId);
        if (CollectionUtils.isEmpty(barrages)) {
            ResponseErrorVo response = new ResponseErrorVo();
            response.setErrorcode(0);
            response.setError("找不到该视频~");
            return response;
        }
        //成功时返回
        ResponseVo response = new ResponseVo();
        JSONObject result   = new JSONObject();
        result.put("barrages", barrages);
        response.setErrorcode(1);
        response.setData(result);
        return response;
    }

    @PostMapping("/add")
    public ResponseBaseVo addBarrage(@RequestBody Barrage barrage, String token) throws Exception {
        JSONObject    info = userService.getInfo(token);
        StringBuilder sb   = new StringBuilder();
        if (info == null) {
            sb.append("找不到该用户~");
        }
        if (barrage.getVideoId() == null) {
            sb.append("视频id为空");
        } else {
            Video videoById = videoService.findVideoById(barrage.getVideoId());
            if (videoById == null) {
                sb.append("视频 id 不正确");
            }
        }
        if (barrage.getContent() == null) {
            sb.append("弹幕内容为空");
        } else if (barrage.getContent().length() == 0 || barrage.getContent().length() > 250) {
            sb.append("弹幕内容为空或超过250长度限制");
        }
        if (StringUtils.isNotBlank(sb)) {
            ResponseErrorVo response = new ResponseErrorVo();
            response.setErrorcode(0);
            response.setError(sb.toString());
            return response;
        }

        if (barrage.getColor() == null || barrage.getColor().length() == 0 || barrage.getColor().length() > 10) {
            barrage.setColor("#ffffff");
        }
        if (barrage.getOfftime() == null || barrage.getOfftime() < 0) {
            barrage.setOfftime(0);
        }
        if (barrage.getPosition() == null || barrage.getPosition() < 0) {
            barrage.setPosition(Byte.valueOf("0"));
        }
        barrage.setUserId((Integer) info.get("id"));
        barrage.setId(null);
        barrage.setSendtime(new Date());
        barrageService.addBarrage(barrage);
        ResponseVo response = new ResponseVo();
        response.setErrorcode(1);
        JSONObject data = new JSONObject();
        data.put("msg" , "添加弹幕信息成功~");
        ((ResponseVo) response).setData(data);
        return response;

    }
}
