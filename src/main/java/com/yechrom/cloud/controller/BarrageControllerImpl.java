//package com.yechrom.cloud.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.yechrom.cloud.dto.pojo.Barrage;
//import com.yechrom.cloud.dto.vo.response.ResponseBaseVo;
//import com.yechrom.cloud.dto.vo.response.ResponseErrorVo;
//import com.yechrom.cloud.dto.vo.response.ResponseVo;
//import com.yechrom.cloud.service.BarrageService;
//import com.yechrom.cloud.service.UserService;
//import com.yechrom.cloud.service.VideoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.io.Serializable;
//import java.time.ZonedDateTime;
//import java.util.ArrayList;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/barrage")
//public class BarrageControllerImpl {
//    @Autowired
//    BarrageService barrageService;
//    @Autowired
//    UserService    userService;
//    @Autowired
//    VideoService   videoService;
//
//
//    @RequestMapping("/findId")
//    @ResponseBody
//    public ResponseBaseVo showBarrages(@RequestParam("id") Integer videoId) {
//        ArrayList<Barrage> barrages = (ArrayList<Barrage>) barrageService.showBarrages(videoId);
//        if (barrages == null) {
////            result.put("status", "failure");
//////            result.put("msg", "找不到该视频");
//            ResponseErrorVo response = new ResponseErrorVo();
//            response.setErrorcode(0);
//            response.setError("找不到该视频~");
//        }
//        //成功时返回
//        ResponseVo response = new ResponseVo();
//        JSONObject result   = new JSONObject();
//        result.put("barrages", barrages);
//        response.setErrorcode(1);
//        response.setData(result);
//        return response;
//    }
//
//    @RequestMapping("/add")
//    @ResponseBody
//    public Map<String, Serializable> addBarrage(Barrage barrage, String token) {
//        Token tokenCheck = TokenUtil.checkToken(token, TokenUtil.TokenUssage.DEFAULT);
//        User  user       = userService.findUserById(tokenCheck.getUserId());
//        if (user == null) {
//            throw new TokenUtil.TokenNotFound("用户不存在");
//        }
//        if (barrage.getVideoId() == null) {
//            result.put("msg", "视频 id 为空");
//            break;
//        } else {
//            Video video = videoService.findVideoById(barrage.getVideoId());
//            if (video == null) {
//                result.put("msg", "视频 id 不正确");
//                break;
//            }
//        }
//        if (barrage.getContent() == null) {
//            result.put("msg", "弹幕内容为空");
//            break;
//        } else if (barrage.getContent().length() == 0 || barrage.getContent().length() > 250) {
//            result.put("msg", "弹幕内容为空或超过250长度限制");
//            break;
//        }
//        if (barrage.getColor() == null || barrage.getColor().length() == 0 || barrage.getColor().length() > 10) {
//            barrage.setColor("#ffffff");
//            break;
//        }
//        if (barrage.getOfftime() == null || barrage.getOfftime() < 0) {
//            barrage.setOfftime(0);
//            break;
//        }
//        if (barrage.getPosition() == null || barrage.getPosition() < 0) {
//            barrage.setPosition(Byte.valueOf("0"));
//            break;
//        }
//        barrage.setUserId(user.getId());
//        barrage.setId(null);
//        barrage.setSendtime(ZonedDateTime.now());
//        try {
//            barrageService.addBarrage(barrage);
//            result.put("status", "success");
//        } catch (Exception e) {
//            result.put("msg", "未知错误");
//        }
//    }
//}
