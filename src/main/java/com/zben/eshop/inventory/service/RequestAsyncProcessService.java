package com.zben.eshop.inventory.service;

import com.zben.eshop.inventory.request.Request;

/**
 * @DESC: 异步处理请求Service
 * @AUTHOR: jhon.zhou
 * @DATE: 2019/7/23 0023 10:56
 */
public interface RequestAsyncProcessService {

    void process(Request request);
}
