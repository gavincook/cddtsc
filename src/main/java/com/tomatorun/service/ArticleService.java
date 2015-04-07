package com.tomatorun.service;

import org.moon.base.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by lywb on 2015/4/7.
 */
public interface ArticleService extends BaseService{

    public List<Map<String,Object>> list(Map<String, Object> params);

    public Map<String,Object> get(Map<String, Object> params);

    public void update(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void add(Map<String, Object> params);

    public void check(Map<String, Object> params);

}
