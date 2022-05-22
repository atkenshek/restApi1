package com.example.restapi1.annotations.impl;

import com.example.restapi1.annotations.AnnotationProcessor;
import com.example.restapi1.annotations.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class MetricBeanPostProcessor implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AnnotationProcessor metricAnnotationProcessor;
    private Map<String, Class> map = new HashMap<>();

    public MetricBeanPostProcessor(AnnotationProcessor metricAnnotationProcessor) {
        this.metricAnnotationProcessor = metricAnnotationProcessor;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        Arrays.stream(methods).forEach(method ->{
            Metric annotation = method.getAnnotation(Metric.class);
            if(annotation != null){
                map.put(beanName, bean.getClass());
            }
        });
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                if (method.isAnnotationPresent(Metric.class)) {
                    metricAnnotationProcessor.initCounter(method.getAnnotation(Metric.class).name());
                    metricAnnotationProcessor.process(method);
                    Object value = method.invoke(bean, args);
                    return value;
                } else {
                    return method.invoke(bean, args);
                }
            });
        }
        return bean;
    }
}
