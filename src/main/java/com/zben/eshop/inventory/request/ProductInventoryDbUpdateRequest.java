package com.zben.eshop.inventory.request;

import com.zben.eshop.inventory.model.ProductInventory;
import com.zben.eshop.inventory.service.ProductInventoryService;
import lombok.extern.slf4j.Slf4j;

/**
 * @DESC: 商品库存更新请求: 比如一个商品发生了交易，那么就要修改这个商品对应的库存
 * cache aside pattern
 * (1) 删除缓存
 * (2) 更新数据库
 * @author: jhon.zhou
 * @date: 2019/7/23 0023 10:14
 */
@Slf4j
public class ProductInventoryDbUpdateRequest implements  Request {

    /**
     * 商品库存
     */
    private ProductInventory productInventory;
    /**
     * 商品库存Service
     */
    private ProductInventoryService productInventoryService;

    public ProductInventoryDbUpdateRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        log.info("=====日志===== 库存更新开始执行 productId:{}, count:{}", productInventory.getProductId(), productInventory.getInventoryCnt());
        //删除库存缓存
        productInventoryService.clearProductInventoryCache(productInventory);
        log.info("=====日志===== 已删除redis中的缓存");
        //更新库存
        productInventoryService.updateProductInventory(productInventory);
        log.info("=====日志===== 已修改数据库的库存, productId:{}", productInventory.getProductId());
    }

    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }

    @Override
    public boolean isForceRefresh() {
        return false;
    }
}
