package com.zben.eshop.inventory.request;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @DESC: 请求内存队列
 * @author: jhon.zhou
 * @date: 2019/7/23 0023 9:31
 */
public class RequestQueue {

    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<ArrayBlockingQueue<Request>>();
    /**
     * 标识位
     */
    private Map<Integer, Boolean> flagMap = Maps.newConcurrentMap();

    /**
     * 静态内部类的方式，去初始化单例，绝对线程安全的方法
     */
    private static class Singleton {

        private static RequestQueue instance;

        static {
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance() {
            return instance;
        }
    }

    /**
     * jvm的机制保证多线程并发安全
     * 内部类初始化，一定只会发生一次
     * @return
     */
    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 添加内存队列
     * @param queue
     */
    public void addQueue(ArrayBlockingQueue queue) {
        this.queues.add(queue);
    }

    /**
     * 获取内存队列的数量
     * @return
     */
    public int getQueueSize() {
        return queues.size();
    }

    /**
     * 获取内存队列
     * @param index
     * @return
     */
    public ArrayBlockingQueue<Request> getQueue(int index) {
        return queues.get(index);
    }

    public Map<Integer, Boolean> getFlagMap() {
        return flagMap;
    }

}
