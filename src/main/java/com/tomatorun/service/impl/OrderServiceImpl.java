package com.tomatorun.service.impl;

import com.tomatorun.repository.OrderRepository;
import com.tomatorun.service.OrderService;
import org.moon.base.service.AbstractService;
import org.moon.core.spring.config.annotation.Config;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends AbstractService implements OrderService {

    @Config("orderType.unpaid")
    private int unpaidOrderType;

    @Config("orderType.undistribute")
    private int undistributeOrderType;

    @Config("orderType.distributing")
    private int distributingOrderType;

    @Config("orderType.arrived")
    private int arrivedOrderType;

    @Config("orderType.confirmed")
    private int confirmedOrderType;

    @Resource
    private OrderRepository orderRepository;

    @Override
    public Map<String, Object> get(Map<String,Object> params) {
        return orderRepository.get(params);
    }

    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        return orderRepository.list(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        orderRepository.update(params);
    }

    @Override
    public void delete(Map<String,Object> params) {
        orderRepository.delete(params);
    }

    @Override
    public void add(Map<String, Object> params) {
        params.put("status", unpaidOrderType);
        orderRepository.add(params);
    }

    @Override
    public void paidOrder(Map<String, Object> params) {
        params.put("status", undistributeOrderType);
        orderRepository.update(params);
    }

    @Override
    public void distributeOrder(Map<String, Object> params) {
        params.put("status", distributingOrderType);
        orderRepository.update(params);
    }

    @Override
    public void orderArrived(Map<String, Object> params) {
        params.put("status", arrivedOrderType);
        orderRepository.update(params);
    }

    @Override
    public void confirmOrder(Map<String, Object> params) {
        params.put("status", confirmedOrderType);
        orderRepository.update(params);
    }
}
