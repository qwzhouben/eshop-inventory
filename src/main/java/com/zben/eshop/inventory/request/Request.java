package com.zben.eshop.inventory.request;

/**
 * @DESC: 请求接口
 * @AUTHOR: jhon.zhou
 * @DATE: 2019/7/23 0023 9:19
 */
public interface Request {

    void process();

    Integer getProductId();

    boolean isForceRefresh();
}
