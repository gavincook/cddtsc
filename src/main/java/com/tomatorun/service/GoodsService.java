package com.tomatorun.service;

import com.tomatorun.dto.UserGoods;
import org.moon.base.service.BaseService;

import java.util.List;
import java.util.Map;

public interface GoodsService extends BaseService {

    public Map<String,Object> get(Map<String, Object> params);

    public List<Map<String,Object>> list(Map<String, Object> params);

    public List<Map<String, Object>> listGoods(Map<String, Object> params) ;

    public void update(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void add(Map<String, Object> params);

    /**
     * 供商选择商品
     * @param params
     */
    public void select(Map<String, Object> params);

    public void deleteSelectGoods(Long selectGoodsId);

    /**
     * 获取指定的用户商品列表
     * @param userGoodsIds
     * @return
     */
    public List<UserGoods> getUserGoodsForSpecified(Long[] userGoodsIds);

    /**
     * 获取该商品的详细信息
     * @param params
     * @return
     */
    public  Map<String,Object> getGoodsDetail(Map<String, Object> params);

    /**
     * 根据userGoodsId获取商品信息
     * @param userGoodsId
     * @return
     */
    public Map<String,Object> getGoodsForShop(Long userGoodsId);

    public void updatePrice(Double price,Long id);
}
