package com.tomatorun.service.impl;

import com.tomatorun.repository.ArticleRepository;
import com.tomatorun.service.ArticleService;
import org.moon.base.service.AbstractService;
import org.moon.core.spring.config.annotation.Config;
import org.moon.dictionary.service.DictionaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lywb on 2015/4/7.
 */
@Service
public class ArticleServiceImpl extends AbstractService implements ArticleService {

    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private DictionaryService dictionaryService;

    @Config("dic.articleType")
    private String articleTypeDicKey;

    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        return articleRepository.list(params);
    }

    @Override
    public Map<String, Object> get(Map<String, Object> params) {
        params.put("articleTypeDicId", dictionaryService.getDictionaryIdByCode(articleTypeDicKey));
        return articleRepository.get(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        articleRepository.update(params);
    }

    @Override
    public void delete(Map<String, Object> params) {
        articleRepository.delete(params);
    }

    @Override
    public void add(Map<String, Object> params) {
        articleRepository.add(params);
    }

    @Override
    public void check(Map<String, Object> params) {
        articleRepository.check(params);
    }
}
