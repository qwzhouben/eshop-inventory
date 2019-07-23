package com.zben.eshop.inventory.controller;

import com.zben.eshop.inventory.model.ProductInventory;
import com.zben.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.zben.eshop.inventory.request.ProductInventoryDbUpdateRequest;
import com.zben.eshop.inventory.request.Request;
import com.zben.eshop.inventory.service.ProductInventoryService;
import com.zben.eshop.inventory.service.RequestAsyncProcessService;
import com.zben.eshop.inventory.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @DESC: 商品库存Controller
 * @author: jhon.zhou
 * @date: 2019/7/23 0023 11:26
 */
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
        ProductInventory productInventory = null;
        try {
            Request request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService);
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
                productInventory = productInventoryService.findProductInventory(productId);
                //如果读取到数据，直接返回
                if (productInventory != null) {
                    return productInventory;
                }
                //没有读取到， 等待
                else {
                    Thread.sleep(20);
                    waitTime = System.currentTimeMillis() - startTime;
                }
            }
            //等待200ms都没有从缓存中拿到数据，直接从数据库查询
            return productInventoryService.findProductInventory(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProductInventory(productId, -1L);
    }
}
