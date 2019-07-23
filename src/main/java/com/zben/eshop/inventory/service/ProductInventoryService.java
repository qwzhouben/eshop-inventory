package com.zben.eshop.inventory.service;

import com.zben.eshop.inventory.model.ProductInventory;

/**
 * @DESC: 商品库存Service
 * @AUTHOR: jhon.zhou
 * @DATE: 2019/7/23 0023 10:20
 */
public interface ProductInventoryService {

    /**
     * 更新商品库存
     * @param productInventory
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 删除商品库存缓存
     * @param productInventory
     */
    void clearProductInventoryCache(ProductInventory productInventory);

    /**
     * 根据商品id查询库存
     * @param productId
     * @return
     */
    ProductInventory findProductInventory(Integer productId);

    /**
     * 更新库存缓存
     * @param productInventory
     */
    void refreshCache(ProductInventory productInventory);
}
