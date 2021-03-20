package org.example.spring;

import org.example.annotation.RpcService;
import org.example.register.ServiceRegistry;
import org.example.register.zk.ZkServiceRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class RpcBeanPostProcessor implements BeanPostProcessor {

    private ServiceRegistry serviceRegistry;

    public RpcBeanPostProcessor() {
        serviceRegistry = new ZkServiceRegistry();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //存在RpcService标识
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {
            serviceRegistry.registerService(null, bean.getClass().getName());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
