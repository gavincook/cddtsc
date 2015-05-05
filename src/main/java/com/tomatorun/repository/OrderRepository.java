package com.tomatorun.repository;

import com.tomatorun.dto.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface OrderRepository {

    public Map<String,Object> getWidthShop(Map<String, Object> params);

    public List<Map<String,Object>> list(Map<String, Object> params);

    public void update(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void add(Map<String, Object> params);

    public Order get(@Param("id")Long id);

    public void updateFlag(@Param("order")Order order);

    /**
     * 获取是否有库存不足的商品
     * @param orderIds
     * @return
     */
    public List<Map<String,String>> getInsufficientGoodsForOrder(@Param("orderIds")Long[] orderIds);

    /**
     * 消耗库存
     * @param orderIds
     */
    public void consumeInventory(@Param("orderIds")Long[] orderIds);

}
