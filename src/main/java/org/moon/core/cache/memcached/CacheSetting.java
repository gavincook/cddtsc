package org.moon.core.cache.memcached;

/**
 * 缓存配置
 * @author:Gavin
 * @date 2015/1/22 0022
 */
public class CacheSetting {

    private String name;

    private int expiry;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpiry() {
        return expiry;
    }

    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }
}
