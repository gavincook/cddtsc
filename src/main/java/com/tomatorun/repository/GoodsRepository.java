package com.tomatorun.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface GoodsRepository {

    public Map<String,Object> get(Map<String, Object> params);

    public List<Map<String,Object>> list(Map<String, Object> params);

    public void update(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void add(Map<String, Object> params);

    /**
     * 供商选择商品
     * @param params
     */
    public void select(Map<String, Object> params);

    /**
     * 删除供商选择的商品
     * @param selectGoodsId
     */
    public void deleteSelectGoods(@Param("selectGoodsId")Long selectGoodsId);
}
