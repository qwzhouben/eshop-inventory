package com.zben.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zben.eshop.inventory.mapper.UserMapper;
import com.zben.eshop.inventory.model.User;
import com.zben.eshop.inventory.service.RedisService;
import com.zben.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @DESC:
 * @author: jhon.zhou
 * @date: 2019/7/22 0022 16:02
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public User findUserInfo() {
        return userMapper.findUserInfo();
    }

    @Override
    public User getCachedUserInfo() {
        redisService.set("cached_user", "{\"name\": \"lisi\", \"age\": 25}") ;
        String json = redisService.get("cached_user");
        JSONObject jsonObject = JSONObject.parseObject(json);

        User user = new User();
        user.setName(jsonObject.getString("name"));
        user.setAge(jsonObject.getInteger("age"));

        return user;
    }
}
