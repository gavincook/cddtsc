package com.tomatorun.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface GoodsRepository {

    public Map<String,Object> get(Map<String, Object> params);

    public List<Map<String,Object>> list(Map<String, Object> params);

    public void update(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void add(Map<String, Object> params);
}
