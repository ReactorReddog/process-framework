package org.processframework.open;

import lombok.extern.slf4j.Slf4j;
import org.processframework.open.annotation.Api;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author apple 
 * @desc 注解扫描
 * @since 1.0.0.RELEASE
 */
@Slf4j
public class OpenApiManageProcessor implements BeanPostProcessor {
    /**
     * 是否开启开放平台
     */
    private boolean enabled;

    public OpenApiManageProcessor(boolean enabled){
        this.enabled = enabled;
    }

    /**
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("====================开放平台系统配置检测中=========================");
        //判断是否开启
        if (!this.enabled){
            log.info("系统检测你已关闭开放平台信息，已退出!");
            return bean;
        }
        //获取api
        Api api = bean.getClass().getAnnotation(Api.class);
        //获取请求接口层注解信息
        RestController restController = bean.getClass().getAnnotation(RestController.class);
        if (Objects.isNull(api)||Objects.isNull(restController)){
            return bean;
        }
        log.info("扫描到开放接口信息[中文名:{},英文名:{}]",api.apiChineseName(),api.apiEnName());
        //获取requestMapping信息
        RequestMapping requestMapping = bean.getClass().getAnnotation(RequestMapping.class);
        //记录请求地址
        String requestUrl = "";
        if (Objects.nonNull(requestMapping)){
            requestUrl = requestMapping.value()[0];
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

}
