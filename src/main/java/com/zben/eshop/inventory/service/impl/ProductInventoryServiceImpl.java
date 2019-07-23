package com.zben.eshop.inventory.service.impl;

import com.zben.eshop.inventory.mapper.ProductInventoryMapper;
import com.zben.eshop.inventory.model.ProductInventory;
import com.zben.eshop.inventory.service.ProductInventoryService;
import com.zben.eshop.inventory.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @DESC: 商品库存Service实现
 * @author: jhon.zhou
 * @date: 2019/7/23 0023 10:22
 */
@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private static final String PRODUCT_INVENTORY_CACHE_KEY = "product:inventory:";

    @Autowired
    private ProductInventoryMapper productInventoryMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
    }

    @Override
    public void clearProductInventoryCache(ProductInventory productInventory) {
        redisService.del(PRODUCT_INVENTORY_CACHE_KEY + productInventory.getProductId());
    }

    @Override
    public ProductInventory findProductInventory(Integer productId) {
        return productInventoryMapper.findProductInventory(productId);
    }

    @Override
    public void refreshCache(ProductInventory productInventory) {
        redisService.set(PRODUCT_INVENTORY_CACHE_KEY + productInventory.getProductId(), String.valueOf(productInventory.getInventoryCnt()));
    }

    @Override
    public ProductInventory findProductInventoryCache(Integer productId) {
        String productInventoryStr = redisService.get(PRODUCT_INVENTORY_CACHE_KEY + productId);
        if (!StringUtils.isEmpty(productInventoryStr)) {
            try {
                return new ProductInventory(productId, Long.valueOf(productInventoryStr));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
