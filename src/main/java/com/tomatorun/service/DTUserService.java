package com.tomatorun.service;

import org.moon.base.service.BaseService;
import org.moon.pagination.Pager;

import java.util.List;
import java.util.Map;

public interface DTUserService extends BaseService {

    /**
     * 获取详情
     * @param params
     * @return
     */
    public Map<String,Object> get(Map<String, Object> params);

    /**
     * 获取用户列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> list(Map<String, Object> params);

    /**
     * 更新用户
     * @param params
     */
    public void update(Map<String, Object> params);

    /**
     * 删除用户
     * @param params
     */
    public void delete(Map<String, Object> params);

    /**
     * 添加用户
     * @param params
     */
    public void add(Map<String, Object> params);

    /**
     * 注册
     * @param params
     */
    public void register(Map<String,Object> params);

    /**
     * 检查用户名是否注册
     * @param userName
     * @return
     */
    public boolean isUserRegistered(String userName);

    /**
     * 激活用户
     * @param id
     */
    public void activeUser(Long id);

}
