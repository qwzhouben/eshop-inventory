package com.zben.eshop.inventory.controller;

import com.zben.eshop.inventory.model.ProductInventory;
import com.zben.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.zben.eshop.inventory.request.ProductInventoryDbUpdateRequest;
import com.zben.eshop.inventory.request.Request;
import com.zben.eshop.inventory.service.ProductInventoryService;
import com.zben.eshop.inventory.service.RequestAsyncProcessService;
import com.zben.eshop.inventory.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @DESC: 商品库存Controller
 * @author: jhon.zhou
 * @date: 2019/7/23 0023 11:26
 */
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductInventoryController {

    @Autowired
    private ProductInventoryService productInventoryService;
    @Autowired
    private RequestAsyncProcessService requestAsyncProcessService;

    /**
     * 更新商品库存
     * @param productInventory
     * @return
     */
    @GetMapping("/updateProductInventory")
    public Response updateProductInventory(ProductInventory productInventory) {
        log.info("=========日志=======：更新商品库存请求：productInventory:{}", productInventory);
        try {
            Request request = new ProductInventoryDbUpdateRequest(productInventory, productInventoryService);
            requestAsyncProcessService.process(request);
            return Response.success(Response.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail(Response.FAIL);
        }
    }

    /**
     * 查询商品库存
     * @param productId
     * @return
     */
    @GetMapping("/findProductInventory/{productId}")
    public ProductInventory findProductInventory(@PathVariable Integer productId) {
        log.info("=========日志=======：查询商品库存请求：productId:{}", productId);
        ProductInventory productInventory = null;
        Request request = null;
        try {
            request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService, false);
            requestAsyncProcessService.process(request);
            //请求交给service异步去处理以后，就需要while(true)一会，在这里hang住
            //去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷到缓存中
            long startTime = System.currentTimeMillis();
            long waitTime = 0L;
            while (true) {
                //如果等待超过200ms
                if (waitTime > 200) {
                    break;
                }
                //尝试一次从redis中查询缓存数据
                productInventory = productInventoryService.findProductInventoryCache(productId);
                //如果读取到数据，直接返回
                if (productInventory != null) {
                    log.info("====日志==== 在200ms内读取到了商品缓存 productInventory:{}", productInventory);
                    return productInventory;
                }
                //没有读取到， 等待
                else {
                    Thread.sleep(20);
                    waitTime = System.currentTimeMillis() - startTime;
                }
            }
            //等待200ms都没有从缓存中拿到数据，直接从数据库查询
            productInventory = productInventoryService.findProductInventory(productId);
            if (productInventory != null) {
                //强制刷入缓存
                request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService, true);
                requestAsyncProcessService.process(request);
                /**
                 * 运行到这里，只有三种情况：
                 * 1. 上一次也是读请求，数据刷入了redis，但是redis LRU算法给清理掉了，标志位还是false
                 * 所以此时下一个读请求是从缓存中拿不到数据的，再放一个读Request进队列，让数据去刷新一下
                 * 2. 可能200ms内，就是读请求在队列中一直积压，没有等待它执行（在实际生产环境中，基本玩完）
                 * 所以就直接查一次库，然后给队列里塞进去一个刷新缓存请求
                 * 3. 数据库本身没有，缓存穿透，穿透redis，请求到达mysql
                 */
                return productInventory;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProductInventory(productId, -1L);
    }
}
