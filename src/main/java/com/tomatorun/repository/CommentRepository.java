package com.tomatorun.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by table on 2015/4/9.
 */
@Repository
public interface CommentRepository {

    public List<Map<String,Object>> get(Map<String, Object> params);

    /**
     * 获取列表
     * @param  params  pageIndex（默认=1）
     * @return
     */
    public List<Map<String,Object>> list(Map<String, Object> params);

    public void update(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void add(Map<String, Object> params);

    public void check(Map<String, Object> params);
}
