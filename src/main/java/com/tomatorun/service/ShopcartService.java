package com.tomatorun.service;

import com.tomatorun.dto.Shopcart;
import org.moon.base.service.BaseService;
import org.moon.pagination.Pager;

import java.util.List;
import java.util.Map;

public interface ShopcartService extends BaseService {

    public Map<String,Object> get(Map<String, Object> params);

    public Pager list(Map<String,Object> params);

    public void update(Shopcart shopcart);

    public void delete(Map<String, Object> params);

    public void add(Shopcart shopcart);

}
