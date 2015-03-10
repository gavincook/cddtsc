package com.tomatorun.service.impl;

import com.tomatorun.dto.Shopcart;
import com.tomatorun.repository.ShopcartRepository;
import com.tomatorun.service.ShopcartService;
import org.moon.base.service.AbstractService;
import org.moon.core.spring.config.annotation.Config;
import org.moon.pagination.Pager;
import org.moon.utils.Maps;
import org.moon.utils.Objects;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ShopcartServiceImpl extends AbstractService implements ShopcartService {
    @Config("attachment.goods")
    private int goodsAttachment;

    @Resource
    private ShopcartRepository shopcartRepository;

    @Override
    public Map<String, Object> get(Map<String,Object> params) {
        return shopcartRepository.get(params);
    }

    @Override
    public Pager list(Map<String,Object> params) {
        params.put("attachmentType",goodsAttachment);
        return super.listForPage(ShopcartRepository.class, "list", params);
    }

    @Override
    public void update(Shopcart shopcart) {
        shopcartRepository.update(shopcart);
    }

    @Override
    public void delete(Map<String,Object> params) {
        shopcartRepository.delete(params);
    }

    @Override
    public void add(Shopcart shopcart) {
        Shopcart oldShopcart = shopcartRepository.checkIfExists(shopcart);
        if(Objects.nonNull(oldShopcart)){
            oldShopcart.setNumber(oldShopcart.getNumber()+shopcart.getNumber());
            update(oldShopcart);
            shopcart.setNumber(oldShopcart.getNumber());
            shopcart.setId(oldShopcart.getId());
        }else {
            shopcartRepository.add(shopcart);
        }
    }

}
