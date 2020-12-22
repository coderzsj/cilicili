package com.yechrom.cloud;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yechrom.cloud.dto.mapper.UserMapper;
import com.yechrom.cloud.dto.pojo.User;
import com.yechrom.cloud.dto.pojo.Video;
import com.yechrom.cloud.dto.vo.response.ResponseBaseVo;
import com.yechrom.cloud.dto.vo.response.ResponseVo;
import com.yechrom.cloud.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudApplicationTests {

    @Autowired
    VideoService videoService;

    @Autowired
    UserMapper userMapper;

    @Test
    public void hello() {
        System.out.println("hello world");
    }

    @Test
    public void m(){
        Video  video    = videoService.findVideoById(1);
        ResponseBaseVo response = new ResponseVo();
        video.setCountLike(video.getCountLike() + 1);
        videoService.updateVideo(video);
        response.setErrorcode(1);
        ((ResponseVo) response).setData(video.getCountLike());
    }

    @Test
    public void selectPage() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("age",26);
        Page<User>  page      = new Page<>(1, 2);
        IPage<User> userIPage = userMapper.selectPage(page, wrapper);
        System.out.println("总条数"+userIPage.getTotal());
        System.out.println("总页数"+userIPage.getPages());

    }



}
