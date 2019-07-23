package com.zben.eshop.inventory.listener;

import com.zben.eshop.inventory.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @DESC: 系统初始化监听器
 * @author: jhon.zhou
 * @date: 2019/7/22 0022 17:45
 */
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("=================初始化监听器==================");
        //初始化内存队列和线程池
        RequestProcessorThreadPool.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
