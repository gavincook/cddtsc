package org.moon.dictionary.service;

import org.moon.base.service.BaseDomainService;
import org.moon.dictionary.domain.Dictionary;

import java.util.List;
import java.util.Map;

public interface DictionaryService extends BaseDomainService<Dictionary> {

    /**
     * 根据字典代码获取该字典的字典参数
     * @param code
     * @return
     */
    public List<Map<String,Object>> listChildrenByCode(String code);

    /**
     * 添加字典，并返回主键.该方式不会通过域模型，主要用于系统启动时，加载字典
     * @param params
     * @return
     */
    public Long add(Map<String,Object> params);

    /**
     *根据字典代码获取字典
     * @param code
     * @return
     */
    public Map<String,Object> getDictionaryByCode(String code);

    /**
     * 根据字典代码获取字典ID
     * @param code
     * @return
     */
    public Long getDictionaryIdByCode(String code);
}
