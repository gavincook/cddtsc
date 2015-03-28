package com.tomatorun.dto;

/**
 * @author:Gavin
 * @date 2015/3/28
 */
public class Shop extends BaseDto {
    private String name;

    private Long userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
