package com.yechrom.cloud.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yechrom.cloud.dto.mapper.UserMapper;
import com.yechrom.cloud.dto.pojo.User;
import com.yechrom.cloud.dto.vo.AddUserVo;
import com.yechrom.cloud.dto.vo.LoginVo;
import com.yechrom.cloud.dto.vo.UpdateUserVo;
import com.yechrom.cloud.exception.exceptions.ParamIsNullException;
import com.yechrom.cloud.exception.exceptions.TokenNullException;
import com.yechrom.cloud.exception.exceptions.UsernameOrPasswordIsNullException;
import com.yechrom.cloud.util.RedisUtil;
import com.yechrom.cloud.util.UUIDUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 登陆
     *
     * @param loginVo
     * @return
     * @throws Exception
     */
    public String login(LoginVo loginVo) throws Exception {
        if (StringUtils.isAllBlank(loginVo.getUsername()) || StringUtils.isAllBlank(loginVo.getPassword())) {
            throw new UsernameOrPasswordIsNullException("用户名或密码为空");
        }
        User   user  = userMapper.selectOne(new QueryWrapper<User>().eq("username", loginVo.getUsername()).eq("password", loginVo.getPassword()));
        String token = "";
        if (user == null) {
            return token;
        }
        token = UUIDUtil.getUserUUID();
        //信息存入redis
        JSONObject json = new JSONObject();
        json.put("identity", user.getIdentity());
        json.put("username", user.getUsername());
        json.put("id", user.getId());
        redisUtil.setEx(token, json.toJSONString(), 1, TimeUnit.DAYS);
        return token;
    }

    /**
     * 从缓存获取用户信息
     *
     * @param token
     * @return
     */
    public JSONObject getInfo(String token) throws Exception {
        String userInfo = "";
        userInfo = redisUtil.get(token);
        if (StringUtils.isEmpty(userInfo)) {
            throw new UsernameOrPasswordIsNullException("reids中不存在info~");
        }
        return JSONObject.parseObject(userInfo);
    }

    /**
     * 登出用户
     *
     * @param token
     * @return
     * @throws Exception
     */
    public int logout(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            throw new TokenNullException("token不存在~");
        }
        //判断是否存在token
        Boolean hasKey = redisUtil.hasKey(token);
        //如果存在 删除
        if (hasKey) {
            redisUtil.delete(token);
            return 1;
        }
        return 0;
    }

    public List<User> show() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    public int updateUser(UpdateUserVo userVo) throws Exception {
        if (!StringUtils.isNotBlank(userVo.getPassword()) || !StringUtils.isNotBlank(userVo.getName())) {
            throw new ParamIsNullException("姓名或密码不能为空~");
        }
        User userPo = new User();
        userPo.setUsername(userVo.getName());
        userPo.setPassword(userVo.getPassword());
        return userMapper.update(userPo, new UpdateWrapper<User>().eq("id", userVo.getId()));
    }

    public int deleteUser(String id) throws Exception {
        return userMapper.delete(new QueryWrapper<User>().eq("id",id));
    }

    public int addUser(AddUserVo userVo) throws Exception {
        User userPo = new User();
        userPo.setPassword(userVo.getPassword());
        userPo.setUsername(userVo.getUsername());
        userPo.setIdentity(userVo.getIdentity());
        return userMapper.insert(userPo);
    }
}
