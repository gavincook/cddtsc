package com.tomatorun.service;

import com.tomatorun.dto.Order;
import org.moon.base.service.BaseService;

import java.util.List;
import java.util.Map;

public interface OrderService extends BaseService {

    public Map<String,Object> get(Map<String, Object> params);

    public List<Map<String,Object>> list(Map<String, Object> params);

    public void update(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void add(Map<String, Object> params);

    public void paidOrder(Map<String, Object> params);

    public void distributeOrder(Map<String, Object> params);

    public void orderArrived(Map<String, Object> params);

    public void confirmOrder(Map<String, Object> params);

    public void cancelOrder(Long id);

    public void pay(Long[] orderIds,Long userId);

    public Order get(Long id);

    /**
     * 店铺删除订单
     * @param order
     * @return
     */
    public Order deleteByShop(Order order);

    /**
     * 用户删除订单
     * @param order
     * @return
     */
    public Order deleteByUser(Order order);

}
