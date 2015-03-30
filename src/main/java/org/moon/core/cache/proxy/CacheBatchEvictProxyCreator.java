package org.moon.core.cache.proxy;

import org.moon.utils.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.framework.ProxyConfig;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * #{@link org.moon.core.cache.annotation.CacheBatchEvict}代理创建器
 * <p>如果启动了<code>cache:annotation-driven</code>则使用了缓存注解的，如
 * #{@link org.springframework.cache.annotation.Cacheable},#{@link org.springframework.cache.annotation.CachePut}等的对象
 * 会被spring代理，此时对#{@link org.moon.core.cache.annotation.CacheBatchEvict}进行代理时，会得到代理对象的熟悉，而无法得到原始的对，
 * 象, 因此使用#{@link org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(Object, String)},
 * 在bean准备的最后一个环节，得到每个bean对象，然后解析其原始对象进行代理过滤。该代理创建可支持多重代理创建.</p>
 * @author:Gavin
 * @date 2015/2/3 0003
 */
public class CacheBatchEvictProxyCreator extends ProxyConfig implements BeanPostProcessor,AopInfrastructureBean,BeanFactoryAware {

    private static final Field interceptorNamesField = ReflectionUtils.findField(ProxyFactoryBean.class,
            "interceptorNames");

    private String[] interceptorNames = new String[0];

    private final Logger log = LoggerFactory.getLogger(getClass());

    private BeanFactory beanFactory;
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(AopUtils.getTargetClass(bean).isAnnotationPresent(Service.class)) {
            if (ProxyFactoryBean.class.isAssignableFrom(bean.getClass())) {
                ProxyFactoryBean proxyFactoryBean = (ProxyFactoryBean) bean;
                //原始的拦截器
                String[] originInterceptors = getInterceptorNames(proxyFactoryBean);
                String[] newInterceptors = new String[originInterceptors.length + interceptorNames.length];
                System.arraycopy(originInterceptors, 0, newInterceptors, 0, originInterceptors.length);
                System.arraycopy(interceptorNames, 0, newInterceptors, originInterceptors.length, interceptorNames.length);
                proxyFactoryBean.setInterceptorNames(newInterceptors);
                return proxyFactoryBean;
            } else {
                ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
                proxyFactoryBean.setBeanFactory(beanFactory);
                proxyFactoryBean.setBeanClassLoader(ClassUtils.getDefaultClassLoader());
                proxyFactoryBean.setInterceptorNames(interceptorNames);
                proxyFactoryBean.copyFrom(this); // 拷贝对应的一些Proxy config
                proxyFactoryBean.setTarget(bean);
                return proxyFactoryBean.getObject();
            }
        }
        return bean;
    }

    /**
     * 获取proxyFactoryBean的拦截器
     * @param proxyFactoryBean
     * @return
     */
    private String[] getInterceptorNames(ProxyFactoryBean proxyFactoryBean){
        interceptorNamesField.setAccessible(true);
        try {
            Object obj = interceptorNamesField.get(proxyFactoryBean);
            return Objects.nonNull(obj) ? (String[])obj : new String[0];
        } catch (IllegalAccessException e) {
            log.error("Get the interceptor names for failed.");
            e.printStackTrace();
        }
        return new String[0];
    }

    public void setInterceptorNames(String[] interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
