package com.tomatorun.service.impl;

import com.tomatorun.repository.CategoryRepository;
import com.tomatorun.service.CategoryService;
import org.moon.base.service.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends AbstractService implements CategoryService {

    @Resource
    private CategoryRepository categoryRepository;

    @Override
    public Map<String, Object> get(Map<String,Object> params) {
        return categoryRepository.get(params);
    }

    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        return categoryRepository.list(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        categoryRepository.update(params);
    }

    @Override
    public void delete(Map<String,Object> params) {
        categoryRepository.delete(params);
    }

    @Override
    public void add(Map<String, Object> params) {
        categoryRepository.add(params);
    }
}
