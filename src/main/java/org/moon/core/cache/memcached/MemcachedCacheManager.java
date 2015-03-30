package org.moon.core.cache.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Memcached缓存管理器
 * @author:Gavin
 * @date 2015/1/20 0020
 */
public class MemcachedCacheManager implements CacheManager,InitializingBean{

    private final ConcurrentHashMap<String,MemcachedCache> cacheMap= new ConcurrentHashMap<String,MemcachedCache>(16);

    private MemcachedClient memcachedClient;

    //缓存有效时间
    private int expiry = 60 * 60 ;


    @Override
    public Cache getCache(String key) {
        MemcachedCache cache = cacheMap.get(key);
        if(cache == null){
            cache = createCache(key,expiry);
            cacheMap.put(key,cache);
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableCollection(cacheMap.keySet());
    }

    /**
     * 根据缓存名字配置缓存，采用全局的expiry过期设置
     * @param cacheNames
     */
    public void setCacheNames(Collection<String> cacheNames) {
        if (cacheNames != null) {
            for (String name : cacheNames) {
                this.cacheMap.put(name, createCache(name,expiry));
            }
        }
    }

    /**
     * 配置缓存，可针对每个缓存设置不同的过期时间
     * @param cacheSettings
     */
    public void setCacheSettings(Collection<CacheSetting> cacheSettings) {
        if (cacheSettings != null) {
            for (CacheSetting cacheSetting : cacheSettings) {
                this.cacheMap.put(cacheSetting.getName(), createCache(cacheSetting.getName(),cacheSetting.getExpiry()));
            }
        }
    }
    public void setExpiry(int expiry){
        this.expiry = expiry;
    }

    public int getExpiry(){
        return this.expiry;
    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(memcachedClient,"Must set the memcachedClient for MemcachedCacheManager");
    }

    private MemcachedCache createCache(String name,int expiry){
        return new MemcachedCache(name, memcachedClient,expiry);
    }
}
