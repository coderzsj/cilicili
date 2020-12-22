package com.yechrom.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yechrom.cloud.dto.mapper.VideoMapper;
import com.yechrom.cloud.dto.pojo.SellHouse;
import com.yechrom.cloud.dto.pojo.Video;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description TODO
 * @Date 2020/12/15 16:52
 * @Author zsj
 */
@Component
public class VideoService  {
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


    public List<Video> showVideos() {
        return videoMapper.selectList(new QueryWrapper<>());
    }


    public List<Video> queryVideos(String keyword) {
        return videoMapper.selectList(new QueryWrapper<Video>().like("title",keyword).like("description",keyword).like("upload_username",keyword));
    }


}
