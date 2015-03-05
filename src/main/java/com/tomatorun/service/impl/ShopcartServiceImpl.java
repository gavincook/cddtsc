package com.tomatorun.service.impl;

import com.tomatorun.repository.ShopcartRepository;
import com.tomatorun.service.ShopcartService;
import org.moon.base.service.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ShopcartServiceImpl extends AbstractService implements ShopcartService {
    @Resource
    private ShopcartRepository shopcartRepository;

    @Override
    public Map<String, Object> get(Map<String,Object> params) {
        return shopcartRepository.get(params);
    }

    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        return shopcartRepository.list(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        shopcartRepository.update(params);
    }

    @Override
    public void delete(Map<String,Object> params) {
        shopcartRepository.delete(params);
    }

    @Override
    public void add(Map<String, Object> params) {
        shopcartRepository.add(params);
    }

}
