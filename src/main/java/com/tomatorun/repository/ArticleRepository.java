package com.tomatorun.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lywb on 2015/4/7.
 */
@Repository
public interface ArticleRepository {

    public Map<String,Object> get(Map<String, Object> params);

    /**
     * 获取文章列表
     * @param  params  pageIndex（默认=1）,keyword，type（可选）
     * @return
     */
    public List<Map<String,Object>> list(Map<String, Object> params);

    public void update(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void add(Map<String, Object> params);

    public void check(Map<String, Object> params);
}
