package com.tomatorun.dto;

/**
 * @author:Gavin
 * @date 2015/2/25
 */
public class Shopcart extends BaseDto {
    private Long userId;
    private Long userGoodsId;
    private Integer number;
    private Goods goods;
    private Long shopId;//店铺id，现在取为该店铺的用户id

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserGoodsId() {
        return userGoodsId;
    }

    public void setUserGoodsId(Long userGoodsId) {
        this.userGoodsId = userGoodsId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}

