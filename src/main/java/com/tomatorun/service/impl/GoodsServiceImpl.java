package com.tomatorun.service.impl;

import com.tomatorun.repository.GoodsRepository;
import com.tomatorun.service.GoodsService;
import org.moon.base.service.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl extends AbstractService implements GoodsService {

    @Resource
    private GoodsRepository goodsRepository;

    @Override
    public Map<String, Object> get(Map<String,Object> params) {
        return goodsRepository.get(params);
    }

    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        return goodsRepository.list(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        goodsRepository.update(params);
    }

    @Override
    public void delete(Map<String,Object> params) {
        goodsRepository.delete(params);
    }

    @Override
    public void add(Map<String, Object> params) {
        goodsRepository.add(params);
    }

    @Override
    public void select(Map<String, Object> params) {
        goodsRepository.select(params);
    }

    @Override
    public void deleteSelectGoods(Long selectGoodsId) {
        goodsRepository.deleteSelectGoods(selectGoodsId);
    }
}
