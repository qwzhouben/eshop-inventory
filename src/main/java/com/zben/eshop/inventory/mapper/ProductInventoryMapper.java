package com.zben.eshop.inventory.mapper;

import com.zben.eshop.inventory.model.ProductInventory;
import org.apache.ibatis.annotations.Param;

/**
 * @DESC: 商品库存mapper
 * @AUTHOR: jhon.zhou
 * @DATE: 2019/7/23 0023 10:16
 */
public interface ProductInventoryMapper {

    /**
     * 更新商品库存
     * @param productInventory
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 根据商品id查询库存
     * @param productId
     * @return
     */
    ProductInventory findProductInventory(@Param("productId") Integer productId);
}
