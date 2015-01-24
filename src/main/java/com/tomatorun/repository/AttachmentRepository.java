package com.tomatorun.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface AttachmentRepository {

    public Map<String,Object> get(Map<String, Object> params);

    public List<Map<String,Object>> list(Map<String, Object> params);

    public void update(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void add(Map<String, Object> params);

    /**
     * 根据传入条件删除附件,
     * 可传入的条件包括：
     * referenceId :必须
     * type:必须
     * url:可选
     * @param params
     */
    public void deleteByParams(Map<String, Object> params);
}
