package com.zben.eshop.inventory.thread;

import com.zben.eshop.inventory.request.Request;
import com.zben.eshop.inventory.request.RequestQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @DESC: 单例：请求处理线程池
 * @author: jhon.zhou
 * @date: 2019/7/22 0022 18:12
 */
public class RequestProcessorThreadPool {

    /**
     * 线程池数量
     */
    private static final Integer THREAD_POOL_VAL = 10;
    /**
     * 内存队列容量
     */
    private static final Integer QUEUE_VAL = 100;

    /**
     * 在实际项目中，你设置的线程池的大小，每个线程监控的那个内存队列的大小
     * 都可以做到一个外部的配置文件中
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_VAL);

    public RequestProcessorThreadPool() {
        //创建内存队列
        for (int i = 0; i < THREAD_POOL_VAL; i++) {
            RequestQueue requestQueues = RequestQueue.getInstance();
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(QUEUE_VAL);
            requestQueues.addQueue(queue);
            threadPool.submit(new RequestProcessorThread(queue));
        }
    }

    /**
     * 静态内部类的方式，去初始化单例，绝对线程安全的方法
     */
    private static class Singleton {

        private static RequestProcessorThreadPool instance;

        static {
            instance = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance() {
            return instance;
        }
    }

    /**
     * jvm的机制保证多线程并发安全
     * 内部类初始化，一定只会发生一次
     * @return
     */
    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getInstance();
    }

    public static void init() {
        getInstance();
    }
}
