package com.zben.eshop.inventory.service.impl;

import com.zben.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.zben.eshop.inventory.request.ProductInventoryDbUpdateRequest;
import com.zben.eshop.inventory.request.Request;
import com.zben.eshop.inventory.request.RequestQueue;
import com.zben.eshop.inventory.service.RequestAsyncProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @DESC: 异步处理请求Service实现
 * @author: jhon.zhou
 * @date: 2019/7/23 0023 10:57
 */
@Service
@Slf4j
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

    @Override
    public void process(Request request) {
        try {
            if (!request.isForceRefresh()) {
                //先做去重读请求
                Map<Integer, Boolean> flagMap = RequestQueue.getInstance().getFlagMap();
                //如果是更新请求, 那么就将标识设置为true
                if (request instanceof ProductInventoryDbUpdateRequest) {
                    flagMap.put(request.getProductId(), true);
                }
                // 如果是读请求，那么就判断，如果标识位不为空，而且是true，说明之前有一个商品的更新请求
                else if (request instanceof ProductInventoryCacheRefreshRequest) {
                    Boolean flag = flagMap.get(request.getProductId());
                    if (flag == null || flag) {
                        flagMap.put(request.getProductId(), false);
                    }

                    //如果是缓存刷新请求，而且发现标识是false
                    //说明前面有一个数据库请求+缓存刷新请求，不需要再加入到内存队列
                    if (!flag) {
                        return ;
                    }
                }
            }

            //做路由请求，根据每个请求的商品id，路由到对应的内存队列中
            //ArrayBlockingQueue是多线程并发安全的
            ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getProductId());
            //将请求加入到内存队列
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取路由到的内存队列
     * @param productId
     * @return
     */
    private ArrayBlockingQueue<Request> getRoutingQueue(Integer productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();
        //先获取productId的hash值
        String key = String.valueOf(productId);
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        //对hash值取模，将hash值路由到指定的内存队列，比如内存队列大小是8
        //用内存队列的数量对hash值取模之后，结果一定在0~7
        //所以任何一个商品id都会路由到同一个内存队列
        log.info("======日志===== 路由内存队列的商品：{}, 内存队列索引：{}", productId, requestQueue.getQueueSize() - 1);
        return requestQueue.getQueue((requestQueue.getQueueSize() - 1) & hash);
    }
}
