package com.yechrom.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yechrom.cloud.dto.pojo.Comment;
import com.yechrom.cloud.dto.pojo.Video;
import com.yechrom.cloud.dto.vo.response.ResponseBaseVo;
import com.yechrom.cloud.dto.vo.response.ResponseErrorVo;
import com.yechrom.cloud.dto.vo.response.ResponseVo;
import com.yechrom.cloud.service.CommentService;
import com.yechrom.cloud.service.UserService;
import com.yechrom.cloud.service.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService    userService;
    @Autowired
    VideoService   videoService;

    @PostMapping("/findId")
    public ResponseBaseVo showComments(@RequestParam("id") Integer videoId, Integer offset) {
        if (offset == null) {
            offset = 0;
        }
        Page<Comment> page        = new Page<>(offset, 10);
        Page<Comment> commentPage = commentService.showComments(page, videoId);
        if (commentPage == null) {
            ResponseErrorVo response = new ResponseErrorVo();
            response.setErrorcode(0);
            response.setError("找不到视频~");
        }
        ResponseVo response = new ResponseVo();
        JSONObject result   = new JSONObject();
        result.put("video", commentPage);
        response.setErrorcode(1);
        response.setData(result);
        return response;
    }

    @PostMapping("/add")
    public ResponseBaseVo addComment(@RequestBody Comment comment, String token) throws Exception {
        JSONObject    info = userService.getInfo(token);
        StringBuilder sb   = new StringBuilder();
        if (info == null) {
            sb.append("用户不存在");
        }
        if (comment.getVideoId() == null) {
            sb.append("视频 id 为空");
        } else {
            Video video = videoService.findVideoById(comment.getVideoId());
            if (video == null) {
                sb.append("视频 id 不正确");
            }
        }
        if (comment.getContent() == null) {
            sb.append("评论内容为空");
        } else if (comment.getContent().length() == 0 || comment.getContent().length() > 250) {
            sb.append("评论内容为空或超过250长度限制");
        }
        if (StringUtils.isNotBlank(sb)) {
            ResponseErrorVo response = new ResponseErrorVo();
            response.setErrorcode(0);
            response.setError(sb.toString());
            return response;
        }
        ResponseVo response = new ResponseVo();
        comment.setUserId((Integer) info.get("id"));
        comment.setId(null);
        comment.setSendtime(new Date());
        comment.setCountLike(0);
        int i = commentService.addComment(comment);
        response.setErrorcode(1);
        response.setData(i);
        return response;
    }

    @PostMapping("/delete")
    public ResponseBaseVo deleteComment(Integer id, String token) throws Exception {
        JSONObject    info    = userService.getInfo(token);
        Comment       comment = commentService.findCommentById(id);
        StringBuilder sb      = new StringBuilder();
        if (comment == null) {
            sb.append("没有该评论");
        } else if (info == null) {
            sb.append("用户不存在");
        } else if (!info.get("id").equals(comment.getUserId())) {
            sb.append("非本人操作，拒绝授权");
        }
        int result = commentService.deleteComment(id);
        if (result == 0) {
            sb.append("删除用户失败~");
        }
        if (StringUtils.isNotBlank(sb)) {
            ResponseErrorVo response = new ResponseErrorVo();
            response.setErrorcode(0);
            ((ResponseErrorVo) response).setError("删除用户失败~");
            return response;
        }
        ResponseVo responseVo = new ResponseVo();
        responseVo.setErrorcode(1);
        responseVo.setData(result);
        return responseVo;
    }

    @PostMapping("/like")
    public ResponseBaseVo likeComment(Integer id) {
        Comment comment = commentService.findCommentById(id);
        if (comment == null) {
            ResponseErrorVo response = new ResponseErrorVo();
            response.setErrorcode(0);
            ((ResponseErrorVo) response).setError("删除用户失败~");
            return response;
        }
        comment.setCountLike(comment.getCountLike() + 1);
        int        result     = commentService.updateComment(comment);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setErrorcode(1);
        responseVo.setData(result);
        return responseVo;

    }
}
