package com.zben.eshop.inventory.controller;

import com.zben.eshop.inventory.model.User;
import com.zben.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @DESC:
 * @author: jhon.zhou
 * @date: 2019/7/22 0022 16:03
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserInfo")
    public User getUserInfo() {
        return userService.findUserInfo();
    }

    @GetMapping("/getCachedUserInfo")
    public User getCachedUserInfo() {
        User user = userService.getCachedUserInfo();
        return user;
    }
}
