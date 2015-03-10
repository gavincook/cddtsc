package com.tomatorun.repository;

import com.tomatorun.dto.Shopcart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ShopcartRepository {

    public Map<String,Object> get(Map<String, Object> params);

    public List<Map<String,Object>> list(Map<String, Object> params);

    public void update(@Param("shopcart")Shopcart shopcart);

    public void delete(Map<String, Object> params);

    public void add(@Param("shopcart")Shopcart shopcart);

    /**
     * 检查是否已经添加购物车
     * @return
     */
    public Shopcart checkIfExists(@Param("shopcart")Shopcart shopcart);
}
