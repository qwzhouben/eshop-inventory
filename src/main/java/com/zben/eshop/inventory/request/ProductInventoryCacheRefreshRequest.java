package com.zben.eshop.inventory.request;

import com.zben.eshop.inventory.model.ProductInventory;
import com.zben.eshop.inventory.service.ProductInventoryService;
import lombok.extern.slf4j.Slf4j;

/**
 * @DESC: 缓存中更新商品库存请求
 * @author: jhon.zhou
 * @date: 2019/7/23 0023 10:35
 */
@Slf4j
public class ProductInventoryCacheRefreshRequest implements Request {

    private Integer productId;
    private ProductInventoryService productInventoryService;
    private boolean forceRefresh;

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService,
                                               boolean forceRefresh) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
        this.forceRefresh = forceRefresh;
    }


    @Override
    public void process() {
        //在数据库中查询库存
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        log.info("=====日志====== 已查询到新的库存数: productId:{}, count:{}", productInventory.getProductId(), productInventory.getInventoryCnt());
        //更新缓存
        productInventoryService.refreshCache(productInventory);
        log.info("=====日志===== 已更新商品的缓存 productId:{}, count:{}", productInventory.getProductId(), productInventory.getInventoryCnt());
    }

    @Override
    public Integer getProductId() {
        return productId;
    }

    @Override
    public boolean isForceRefresh() {
        return forceRefresh;
    }
}
