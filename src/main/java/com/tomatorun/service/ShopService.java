package com.tomatorun.service;

import com.tomatorun.dto.Shop;

import java.util.List;
import java.util.Map;

/**
 * @author:Gavin
 * @date 2015/3/28 0028
 */
public interface ShopService {

    public Shop add(Shop shop);

    public Shop update(Shop shop);

    public Shop delete(Shop shop);

    /**
     * 根据userId 获取对应用户的店铺
     * @param userId
     * @return
     */
    public Shop getForUser(Long userId);
}
