package com.yechrom.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yechrom.cloud.dto.mapper.VideoMapper;
import com.yechrom.cloud.dto.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2020/12/15 16:52
 * @Author zsj
 */
@Component
public class VideoService  {
    @Autowired
    VideoMapper videoMapper;

    public Video findVideoById(Integer id) {
        return videoMapper.selectById(id);
    }

    public Video findVideoByUrl(String url) {
        return videoMapper.selectOne(new QueryWrapper<Video>().eq("url",url));
    }


    public void addVideo(Video video) {
        videoMapper.insert(video);
    }


    public void updateVideo(Video video) {
        videoMapper.updateById(video);
    }


    public Page<Video> showVideos(Page<Video> page) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("id",1);
        Page<Video> videoPage = videoMapper.selectPage(page, wrapper);
        return videoPage;
    }


    public Page<Video> queryVideos(String keyword, Page<Video> page) {
        QueryWrapper<Video> like = new QueryWrapper<Video>().or().like("title", keyword).or().like("description", keyword).or().like("upload_userid", keyword);
        return videoMapper.selectPage(page,like);
    }


}
