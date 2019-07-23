package com.zben.eshop.inventory.request;

import com.zben.eshop.inventory.model.ProductInventory;
import com.zben.eshop.inventory.service.ProductInventoryService;

/**
 * @DESC: 缓存中更新商品库存请求
 * @author: jhon.zhou
 * @date: 2019/7/23 0023 10:35
 */
public class ProductInventoryCacheRefreshRequest implements Request {

    private Integer productId;
    private ProductInventoryService productInventoryService;

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
    }


    @Override
    public void process() {
        //在数据库中查询库存
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        //更新缓存
        productInventoryService.refreshCache(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productId;
    }
}
