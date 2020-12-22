package com.yechrom.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.yechrom.cloud.dto.pojo.Video;
import com.yechrom.cloud.dto.vo.ShowAllSellHouseVo;
import com.yechrom.cloud.dto.vo.response.ResponseBaseVo;
import com.yechrom.cloud.dto.vo.response.ResponseErrorVo;
import com.yechrom.cloud.dto.vo.response.ResponseVo;
import com.yechrom.cloud.service.UserService;
import com.yechrom.cloud.service.VideoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 视频接口
 * @Date 2020/12/15 16:35
 * @Author zsj
 */
@RestController("video1")
@Slf4j
public class VideoController {
    @Autowired
    UserService  userService;
    @Autowired
    VideoService videoService;

//    @PostMapping("/user/login")
//    @ResponseBody
//    @ApiOperation("根据id获取视频信息")
//    public ResponseBaseVo findVideoById(Integer id) {
//        log.info("调用视频接口 , 传入的参数为 : {}", id);
//        Video video = videoService.findVideoById(id);
//        if (video == null) {
////            result.put("status", "failure");
//////            result.put("msg", "找不到该视频");
//            ResponseErrorVo response = new ResponseErrorVo();
//            response.setErrorcode(0);
//            response.setError("找不到该视频~");
//        }
////            result.put("status", "success");
////            result.put("video", video);
//        //成功时返回
//        ResponseVo response = new ResponseVo();
//        JSONObject result   = new JSONObject();
//        result.put("video", video);
//        response.setErrorcode(1);
//        response.setData(result);
//        return response;
//
//    }

    @RequestMapping("/add")
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
        video.setUploadTime(ZonedDateTime.now());
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

    @RequestMapping("/play")
    @ResponseBody
    public ResponseBaseVo playVideo(Integer id) {
        Video          video = videoService.findVideoById(id);
        ResponseBaseVo response;
        video.setCountPlay(video.getCountPlay() + 1);
        videoService.updateVideo(video);
        response = new ResponseVo();
        response.setErrorcode(1);
        ((ResponseVo) response).setData(video.getCountPlay());
        return response;
    }

    @RequestMapping("/like")
    @ResponseBody
    public ResponseBaseVo likeVideo(Integer id) {
        Video          video    = videoService.findVideoById(id);
        ResponseBaseVo response = new ResponseVo();
        video.setCountLike(video.getCountLike() + 1);
        videoService.updateVideo(video);
        response.setErrorcode(1);
        ((ResponseVo) response).setData(video.getCountLike());
        return response;
    }

//    @RequestMapping("/show")
//    @ResponseBody
//    public ResponseBaseVo showVideos(@RequestBody ShowAllSellHouseVo requestVo) {
//        log.info("调用了展示在租房源接口 , 传入报文为: {}" , requestVo);
//        Map<String, Serializable> result   = new HashMap<>();
//        ArrayList<Video>          videos   = (ArrayList<Video>) videoService.showVideos();
//        PageInfo<Video>           pageInfo = new PageInfo<>(videos);
//        result.put("page", pageInfo);
//        return result;
//    }
//
//    @RequestMapping("/find")
//    @ResponseBody
//
//    public Map<String, Serializable> findVideos(Integer offset, String q) {
//        Map<String, Serializable> result = new HashMap<>();
//        if (offset == null) {
//            offset = 0;
//        }
//        PageHelper.startPage(offset, 12);
//        ArrayList<Video> videos   = (ArrayList<Video>) videoService.queryVideos(q);
//        PageInfo<Video>  pageInfo = new PageInfo<>(videos);
//        result.put("page", pageInfo);
//        return result;
//    }
//
//    @ResponseBody
//    @ExceptionHandler({Exception.class})
//    public Map<String, Serializable> exceptionHandle(Exception e) {
//        Map<String, Serializable> result = new HashMap<>();
//        result.put("status", "failure");
//        result.put("msg", "参数错误");
//        Logger logger = LoggerFactory.getLogger(this.getClass());
//        logger.error(e.getMessage());
//        logger.error(e.getLocalizedMessage());
//        return result;
//    }
}
