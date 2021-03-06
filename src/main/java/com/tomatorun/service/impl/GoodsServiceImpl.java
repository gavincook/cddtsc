package com.tomatorun.service.impl;

import com.tomatorun.dto.UserGoods;
import com.tomatorun.repository.GoodsRepository;
import com.tomatorun.service.GoodsService;
import org.moon.base.service.AbstractService;
import org.moon.core.spring.config.annotation.Config;
import org.moon.dictionary.service.DictionaryService;
import org.moon.pagination.Pager;
import org.moon.utils.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl extends AbstractService implements GoodsService {

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private DictionaryService dictionaryService;

    @Config("dic.goodsLevel")
    private String goodsLevelKey ;

    @Config("dic.goodsUnit")
    private String goodsUnit ;

    @Config("attachment.goods")
    private int goodsImageType;

    @Override
    public Map<String, Object> get(Map<String,Object> params) {
        return goodsRepository.get(params);
    }

    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        return goodsRepository.list(params);
    }

    @Override
    public List<Map<String, Object>> listGoods(Map<String, Object> params) {
        System.out.println("service");
        return goodsRepository.listGoods(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        goodsRepository.update(params);
    }

    @Override
    public void delete(Map<String,Object> params) {
        goodsRepository.delete(params);
    }

    @Override
    public void add(Map<String, Object> params) {
        goodsRepository.add(params);
    }

    @Override
    public void select(Map<String, Object> params) {
        goodsRepository.select(params);
    }

    @Override
    public void deleteSelectGoods(Long selectGoodsId) {
        goodsRepository.deleteSelectGoods(selectGoodsId);
    }

    @Override
    public List<UserGoods> getUserGoodsForSpecified(Long[] userGoodsIds) {
        return goodsRepository.getUserGoodsForSpecified(userGoodsIds);
    }

    @Override
    public Pager listForPage(Class clazz, String statementId, Map params) {
        params.put("levelDicId",dictionaryService.getDictionaryByCode(goodsLevelKey).get("id"));
        params.put("unitDicId",dictionaryService.getDictionaryByCode(goodsUnit).get("id"));
        params.put("attachmentType",goodsImageType);
        return super.listForPage(clazz, statementId, params);
    }

    @Override
    public Map<String, Object> getGoodsDetail(Map<String,Object> params) {
        params.put("attachmentType",goodsImageType);
        return goodsRepository.getGoodsDetail(params);
    }

    @Override
    public Map<String, Object> getGoodsForShop(Long userGoodsId) {
        return goodsRepository.getGoodsForShop(userGoodsId,
            dictionaryService.getDictionaryByCode(goodsLevelKey).get("id"),
            dictionaryService.getDictionaryByCode(goodsUnit).get("id"));
    }

    @Override
    public void updatePrice(Double price,Long id) {
        goodsRepository.updatePrice(price,id);
    }

    @Override
    public void updateGoodsId(Long goodsId, Long id) {
        goodsRepository.updateGoodsId(goodsId,id);
    }

    @Override
    public Map<String, Object> getGoodsById(Long id) {
        return goodsRepository.getGoodsById(id);
    }

    @Override
    public void deleteByCategory(String categoryIds) {
        goodsRepository.deleteByCategory(categoryIds);
    }

    @Override
    public List<Map<String, Object>> listSpecForShopGoods(Long goodsId, Long userId) {
        return goodsRepository.listSpecForShopGoods(goodsId, userId);
    }
}
