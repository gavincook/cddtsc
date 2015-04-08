package com.tomatorun.service.impl;

import com.tomatorun.repository.CommentRepository;
import com.tomatorun.service.CommentService;
import org.moon.base.service.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by table on 2015/4/9.
 */
@Service
public class CommentServiceImpl  extends AbstractService implements CommentService{

    @Resource
    private CommentRepository commentRepository;

    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        return commentRepository.list(params);
    }

    @Override
    public Map<String, Object> get(Map<String, Object> params) {
        return commentRepository.get(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        commentRepository.update(params);
    }

    @Override
    public void delete(Map<String, Object> params) {
        commentRepository.delete(params);
    }

    @Override
    public void add(Map<String, Object> params) {
        commentRepository.add(params);
    }

    @Override
    public void check(Map<String, Object> params) {
        commentRepository.check(params);
    }

}
