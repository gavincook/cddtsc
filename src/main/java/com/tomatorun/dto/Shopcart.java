package com.tomatorun.dto;

/**
 * @author:Gavin
 * @date 2015/2/25
 */
public class Shopcart extends BaseDto {
    private Long userId;
    private Long userGoodsId;
    private Integer number;

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
}

