package com.zben.eshop.inventory.service;

/**
 * @DESC:
 * @AUTHOR: jhon.zhou
 * @DATE: 2019/7/22 0022 16:22
 */
public interface RedisService {

    void set(String key, String value);

    String get(String key);
}
