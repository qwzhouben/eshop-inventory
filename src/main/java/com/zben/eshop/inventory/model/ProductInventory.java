package com.zben.eshop.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @DESC: 商品库存
 * @author: jhon.zhou
 * @date: 2019/7/23 0023 10:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventory {

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 库存
     */
    private Long inventoryCnt;
}
