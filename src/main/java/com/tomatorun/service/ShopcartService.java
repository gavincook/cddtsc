package com.tomatorun.service;

import org.moon.base.service.BaseService;

import java.util.List;
import java.util.Map;

public interface ShopcartService extends BaseService {

    public Map<String,Object> get(Map<String, Object> params);

    public List<Map<String,Object>> list(Map<String, Object> params);

    public void update(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void add(Map<String, Object> params);

}
