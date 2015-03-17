package com.tomatorun.service;

import com.tomatorun.dto.Shopcart;
import org.moon.base.service.BaseService;
import org.moon.pagination.Pager;

import java.util.List;
import java.util.Map;

public interface ShopcartService extends BaseService {

    public Map<String,Object> get(Map<String, Object> params);

    public Pager list(Map<String,Object> params);

    public void update(Shopcart shopcart);

    public void delete(Long id);

    public void add(Shopcart shopcart);

    /**
     * 获取用户购物车物品
     * @param userId
     * @return
     */
    public List<Shopcart> getShopCartGoodsForUser(Long userId);
}
