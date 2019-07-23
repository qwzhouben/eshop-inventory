package com.zben.eshop.inventory.thread;

import com.zben.eshop.inventory.request.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * @DESC: 执行线程入口
 * @author: jhon.zhou
 * @date: 2019/7/23 0023 9:22
 */
@Slf4j
public class RequestProcessorThread implements Callable<Boolean> {

    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            while (true) {
                //ArrayBlockingQueue
                //Blocking说明，队列满了，或者是空的，那么在执行操作的时候被阻塞住
                Request request = queue.take();
                log.info("=======日志====== 工作线程处理请求 productId:{}", request.getProductId());
                request.process();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
