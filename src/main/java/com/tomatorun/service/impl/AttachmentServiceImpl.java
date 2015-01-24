package com.tomatorun.service.impl;


import com.tomatorun.repository.AttachmentRepository;
import com.tomatorun.service.AttachmentService;
import org.moon.base.service.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AttachmentServiceImpl extends AbstractService implements AttachmentService {

    @Resource
    private AttachmentRepository attachmentRepository;

    @Override
    public Map<String, Object> get(Map<String,Object> params) {
        return attachmentRepository.get(params);
    }

    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        return attachmentRepository.list(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        attachmentRepository.update(params);
    }

    @Override
    public void delete(Map<String,Object> params) {
        attachmentRepository.delete(params);
    }

    @Override
    public void add(Map<String, Object> params) {
        attachmentRepository.add(params);
    }

    @Override
    public void deleteByParams(Map<String, Object> params) {
        attachmentRepository.deleteByParams(params);
    }
}
