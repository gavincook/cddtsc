package com.tomatorun.service.impl;

import com.tomatorun.repository.DTUserRepository;
import com.tomatorun.service.DTUserService;
import org.moon.base.service.AbstractService;
import org.moon.pagination.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DTUserServiceImpl extends AbstractService implements DTUserService {

    @Resource
    private DTUserRepository dTUserRepository;

    @Override
    public Map<String, Object> get(Map<String,Object> params) {
        return dTUserRepository.get(params);
    }

    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        return dTUserRepository.list(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        dTUserRepository.update(params);
    }

    @Override
    public void delete(Map<String,Object> params) {
        dTUserRepository.delete(params);
    }

    @Override
    public void add(Map<String, Object> params) {
        dTUserRepository.add(params);
    }

    @Override
    public void register(Map<String, Object> params) {
        dTUserRepository.register(params);
    }

    @Override
    public boolean isUserRegistered(String userName) {
        return dTUserRepository.isUserRegistered(userName);
    }
}
