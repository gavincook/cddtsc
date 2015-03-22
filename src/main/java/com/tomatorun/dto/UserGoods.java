package com.tomatorun.dto;

/**
 * @author:Gavin
 * @date 2015/3/23
 */
public class UserGoods extends BaseDto {
    private Long goodsId;
    private Long userId;
    private Integer inventory;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

}
