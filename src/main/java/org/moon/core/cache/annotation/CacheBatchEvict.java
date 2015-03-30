package org.moon.core.cache.annotation;

import java.lang.annotation.*;

/**
 * 批量缓存值移除
 * @author:Gavin
 * @date 2015/2/3 0003
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CacheBatchEvict {
    /**
     * 缓存名字
     */
    String[] value();


    /**
     * key目前支持spring EL,对于方法参数引用可使用#xxx来使用,该key对应的表达式需要生成一个String数组，
     * 目前提供了#{@link org.moon.utils.Strings#concatPrefix(String, Object[])}进行简单的key生成,也可自定义其他的key生成机制
     * @return
     */
    String key();

}
