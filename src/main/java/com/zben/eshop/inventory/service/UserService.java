package com.zben.eshop.inventory.service;

import com.zben.eshop.inventory.model.User;

/**
 * @DESC:
 * @AUTHOR: jhon.zhou
 * @DATE: 2019/7/22 0022 16:01
 */
public interface UserService {
    /**
     * 查询用户
     * @return
     */
    public User findUserInfo();

    /**
     * 缓存中获取用户
     * @return
     */
    User getCachedUserInfo();
}
