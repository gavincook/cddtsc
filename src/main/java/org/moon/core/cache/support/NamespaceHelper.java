package org.moon.core.cache.support;

import org.moon.core.spring.ApplicationContextHelper;
import org.moon.utils.Objects;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;

/**
 * 缓存命名空间辅助类
 * @author:Gavin
 * @date 2015/2/11 0011
 */
public class NamespaceHelper {


    private static Cache cache;

    public static void init(){
        if(Objects.isNull(cache)) {
            CacheManager cacheManager = ApplicationContextHelper.getApplicationContext().getBean(CacheManager.class);
            cache = cacheManager.getCache("namespace");
        }
    }

    /**
     * 添加缓存命名空间,格式为： #{namespaceKey}{times}:{key}如：#area2:8
     * @param namespaceKey
     * @param key
     * @return
     */
    public static String withNP(String namespaceKey,Object key){
        init();
        Integer times = Objects.getDefault(cache.get(namespaceKey, Integer.class), 0);
        StringBuilder stringKey = new StringBuilder();
        stringKey.append("#").append(namespaceKey).append(times).append(":").append(key);
        return stringKey.toString();
    }

    /**
     * 移除一个命名空间下的缓存
     * @param namespaceKey
     * @return
     */
    public static String removeNP(String namespaceKey){
        init();
        Integer times = Objects.getDefault(cache.get(namespaceKey,Integer.class),0);
        if(times < 100) {
            times++;
        }else {
            times = 0;
        }
        cache.put(namespaceKey,times);
        return "";
    }
}
