package com.tomatorun.service.impl;

import com.tomatorun.dto.Shopcart;
import com.tomatorun.repository.ShopcartRepository;
import com.tomatorun.service.ShopcartService;
import org.moon.base.service.AbstractService;
import org.moon.core.spring.config.annotation.Config;
import org.moon.dictionary.service.DictionaryService;
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

    @Resource
    private DictionaryService dictionaryService;

    @Config("dic.goodsLevel")
    private String goodsLevelKey ;

    @Config("dic.goodsUnit")
    private String goodsUnit ;

    @Override
    public Map<String, Object> get(Map<String,Object> params) {
        return shopcartRepository.get(params);
    }

    @Override
    public Pager list(Map<String,Object> params) {
        params.put("levelDicId",dictionaryService.getDictionaryByCode(goodsLevelKey).get("id"));
        params.put("unitDicId",dictionaryService.getDictionaryByCode(goodsUnit).get("id"));
        params.put("attachmentType",goodsAttachment);
        return super.listForPage(ShopcartRepository.class, "list", params);
    }

    @Override
    public void update(Shopcart shopcart) {
        shopcartRepository.update(shopcart);
    }

    @Override
    public void delete(Long id) {
        shopcartRepository.delete(id);
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

    @Override
    public List<Shopcart> getShopCartGoodsForUser(Long userId) {
        return shopcartRepository.getShopCartGoodsForUser(userId);
    }

}
