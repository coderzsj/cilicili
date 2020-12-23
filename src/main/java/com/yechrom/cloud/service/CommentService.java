package com.yechrom.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yechrom.cloud.dto.mapper.CommentMapper;
import com.yechrom.cloud.dto.pojo.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2020/12/23 16:09
 * @Author zsj
 */
@Component
@Slf4j
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    public Page<Comment> showComments(Page<Comment> page, Integer videoId) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("video_id",videoId);
        Page<Comment> commentPage = commentMapper.selectPage(page, commentQueryWrapper);
        return commentPage;
    }

    public int addComment(Comment comment) {
        return commentMapper.insert(comment);
    }

    public Comment findCommentById(Integer id) {
        return commentMapper.selectById(id);
    }

    public int deleteComment(Integer id) {
        return commentMapper.deleteById(id);
    }

    public int updateComment(Comment comment) {
        return commentMapper.update(comment,new UpdateWrapper<Comment>().eq("id",comment.getId()));
    }
}
