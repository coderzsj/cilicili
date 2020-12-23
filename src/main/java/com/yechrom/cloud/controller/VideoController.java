package com.yechrom.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yechrom.cloud.dto.pojo.Video;
import com.yechrom.cloud.dto.vo.ShowAllSellHouseVo;
import com.yechrom.cloud.dto.vo.response.ResponseBaseVo;
import com.yechrom.cloud.dto.vo.response.ResponseErrorVo;
import com.yechrom.cloud.dto.vo.response.ResponseVo;
import com.yechrom.cloud.service.UserService;
import com.yechrom.cloud.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 视频接口
 * @Date 2020/12/15 16:35
 * @Author zsj
 */
@RestController("video")
@Slf4j
@Api(tags = "video-controller")
public class VideoController {
    @Autowired
    UserService  userService;
    @Autowired
    VideoService videoService;

    @PostMapping("/findVideoById")
    @ResponseBody
    @ApiOperation("根据id获取视频信息")
    public ResponseBaseVo findVideoById(@RequestBody Integer id) {
        log.info("调用视频接口 , 传入的参数为 : {}", id);
        Video video = videoService.findVideoById(id);
        if (video == null) {
//            result.put("status", "failure");
////            result.put("msg", "找不到该视频");
            ResponseErrorVo response = new ResponseErrorVo();
            response.setErrorcode(0);
            response.setError("找不到该视频~");
        }
//            result.put("status", "success");
//            result.put("video", video);
        //成功时返回
        ResponseVo response = new ResponseVo();
        JSONObject result   = new JSONObject();
        result.put("video", video);
        response.setErrorcode(1);
        response.setData(result);
        return response;

    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseBaseVo addVideo(@RequestBody Video video) {
        log.info("调用视频接口 , 传入的参数为 : {}", video.toString());
        StringBuilder sb = new StringBuilder();
        if (video.getTitle() == null) {
            sb.append("视频标题为空");
        } else if (video.getTitle().length() == 0 || video.getTitle().length() > 50) {
            sb.append("视频标题为空或超过50长度限制");
        }
        if (video.getUrl() == null) {
            sb.append("视频地址为空");
        } else if (video.getUrl().length() == 0 || video.getUrl().length() > 100) {
            sb.append("视频地址为空或超过100长度限制");
        }
        if (video.getPicUrl() == null) {
            sb.append("视频封面地址为空");
        } else if (video.getPicUrl().length() == 0 || video.getPicUrl().length() > 100) {
            sb.append("视频封面地址为空或超过100长度限制");
        }
        if (StringUtils.isEmpty(sb)) {
            ResponseErrorVo response = new ResponseErrorVo();
            response.setErrorcode(0);
            response.setError(sb.toString());
            return response;
        }
        //成功时返回
        ResponseVo response = new ResponseVo();
//        video.setUploadUserid(user.getId());
        video.setId(null);
        video.setUploadTime(new Date());
        video.setCountPlay(0);
        video.setCountLike(0);
        videoService.addVideo(video);
        response.setErrorcode(1);
        video = videoService.findVideoByUrl(video.getUrl());
        JSONObject data = new JSONObject();
        data.put("video", video);
        ((ResponseVo) response).setData(data);
        return response;
    }

    @PostMapping("/play")
    @ResponseBody
    public ResponseBaseVo playVideo(@RequestBody Integer id) {
        Video          video = videoService.findVideoById(id);
        ResponseBaseVo response;
        video.setCountPlay(video.getCountPlay() + 1);
        videoService.updateVideo(video);
        response = new ResponseVo();
        response.setErrorcode(1);
        ((ResponseVo) response).setData(video.getCountPlay());
        return response;
    }

    @PostMapping("/like")
    @ResponseBody
    public ResponseBaseVo likeVideo(@RequestBody Integer id) {
        Video          video    = videoService.findVideoById(id);
        ResponseBaseVo response = new ResponseVo();
        video.setCountLike(video.getCountLike() + 1);
        videoService.updateVideo(video);
        response.setErrorcode(1);
        ((ResponseVo) response).setData(video.getCountLike());
        return response;
    }

    @PostMapping("/show")
    @ResponseBody
    @ApiOperation("分页获取视频信息")
    public ResponseBaseVo showVideos(@RequestBody ShowAllSellHouseVo requestVo) {
        Page<Video>  page      = new Page<>(requestVo.getPage(), requestVo.getLimit());
        Page<Video> videoPage = videoService.showVideos(page);
        if (videoPage == null) {
            ResponseErrorVo response = new ResponseErrorVo();
            response.setErrorcode(0);
            response.setError("找不到视频~");
        }
        ResponseVo response = new ResponseVo();
        JSONObject result   = new JSONObject();
        result.put("video", videoPage);
        response.setErrorcode(1);
        response.setData(result);
        return response;
    }

    /**
     * 分页查询视频信息通过关键字搜索
     *
     * @param requestVo
     * @returns com.yechrom.cloud.dto.vo.response.ResponseBaseVo
     */
    @PostMapping("/find")
    @ResponseBody
    public ResponseBaseVo findVideos(@RequestBody ShowAllSellHouseVo requestVo) {
        Page<Video>  page      = new Page<>(requestVo.getPage(), requestVo.getLimit());
        Page<Video> videoPage =  videoService.queryVideos(requestVo.getKeyword(),page);
        if (videoPage == null) {
            ResponseErrorVo response = new ResponseErrorVo();
            response.setErrorcode(0);
            response.setError("找不到视频~");
        }
        ResponseVo response = new ResponseVo();
        JSONObject result   = new JSONObject();
        result.put("video", videoPage);
        response.setErrorcode(1);
        response.setData(result);
        return response;
    }
}
