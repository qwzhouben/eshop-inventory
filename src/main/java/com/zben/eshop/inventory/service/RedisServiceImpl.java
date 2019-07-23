package com.zben.eshop.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * @DESC:
 * @author: jhon.zhou
 * @date: 2019/7/22 0022 16:23
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    JedisCluster jedisCluster;

    @Override
    public void set(String key, String value) {
        jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public void del(String key) {
        jedisCluster.del(key);
    }
}
