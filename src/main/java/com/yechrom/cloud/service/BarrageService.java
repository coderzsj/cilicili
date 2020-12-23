package com.yechrom.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yechrom.cloud.dto.mapper.BarrageMapper;
import com.yechrom.cloud.dto.pojo.Barrage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description TODO
 * @Date 2020/12/23 10:38
 * @Author zsj
 */
@Component
@Slf4j
public class BarrageService {
    @Autowired
    BarrageMapper barrageMapper;

    public List<Barrage> showBarrages(Integer videoId) {
        return barrageMapper.selectList(new QueryWrapper<Barrage>().eq("video_id",videoId));
    }
}
