package com.tomatorun.service.impl;

import com.tomatorun.repository.OrderDetailRepository;
import com.tomatorun.service.OrderDetailService;
import org.moon.base.service.AbstractService;
import org.moon.core.spring.config.annotation.Config;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailServiceImpl extends AbstractService implements OrderDetailService {
    @Config("attachment.goods")
    private int goodsAttachment;

    @Resource
    private OrderDetailRepository orderDetailRepository;

    @Override
    public Map<String, Object> get(Map<String,Object> params) {
        return orderDetailRepository.get(params);
    }

    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        params.put("attachmentType",goodsAttachment);
        return orderDetailRepository.list(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        orderDetailRepository.update(params);
    }

    @Override
    public void delete(Map<String,Object> params) {
        orderDetailRepository.delete(params);
    }

    @Override
    public void add(Map<String, Object> params) {
        orderDetailRepository.add(params);
    }
}
