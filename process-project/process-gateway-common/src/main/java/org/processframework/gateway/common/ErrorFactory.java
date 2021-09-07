package org.processframework.gateway.common;

import cn.hutool.core.lang.Assert;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author apple
 * @desc 构建错误信息
 * @since 1.0.0.RELEASE
 */
@Slf4j
@NoArgsConstructor
public class ErrorFactory {
    /**
     * 默认错误异常
     */
    public static final String SYS_ERR_MSG = "Service Currently Unavailable";
    /**
     * 默认错误信息
     */
    public static final String sys_err_sub_msg = "系统繁忙";
    /**
     * i18n 路径
     */
    private static final String I18N_OPEN_ERROR = "i18n/open/error";
    /**
     * 下划线连接
     */
    public static final String UNDERLINE = "_";
    /**
     * 模块缓存
     */
    private static Set<String>noModuleCache = new HashSet<>();
    /**
     * 错误信息缓存
     */
    private static Map<String,Error> errorCache = new HashMap<>(128);
    /**
     * 初始化语言 默认采用英语和简体中文
     */
    private static List<Locale> localeList = Arrays.asList(Locale.ENGLISH,Locale.SIMPLIFIED_CHINESE);
    /**
     * 错误信息->国际化信息
     */
    private static MessageSourceAccessor errMessageSourceAccessor;

    /**
     * 初始化国际化资源信息
     * @param isvModules 错误模块
     */
    public static void initMessageSource(List<String> isvModules){
        Set<String>baseNamesSet = new HashSet<>();
        if (isvModules.size()>0){
            baseNamesSet.addAll(isvModules);
        }

        String[] totalBaseNames = baseNamesSet.toArray(new String[0]);

        log.info("加载错误码国际化资源:[{}]", StringUtils.arrayToCommaDelimitedString(totalBaseNames));
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setBasenames(totalBaseNames);
        MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(bundleMessageSource);
        setErrorMessageSourceAccessor(messageSourceAccessor);
    }

    /**
     * 通过errorMeta locale params构建国际化错误信息消息
     * @param errorMeta 错误信息
     * @param locale 本地化
     * @param params 参数
     * @return 如果未配置国际化消息 则直接返回errorMeta中的内容
     */
    public static Error getError(ErrorMeta errorMeta,Locale locale,Object... params){
        locale = Objects.nonNull(locale)?locale:Locale.SIMPLIFIED_CHINESE;
        //判断本地化是否在初始化语言列表中 若不存在则使用简体英文
        if (!localeList.contains(locale)){
            locale = Locale.ENGLISH;
        }

        String key = String.join("",errorMeta.getModulePrefix(),errorMeta.getCode(),errorMeta.getSubCode(),locale.toString());

        Error error = errorCache.get(key);
        if (Objects.isNull(error)){
            Assert.isNull(locale,"locale缓存未配置!");
            String modulePrefix = errorMeta.getModulePrefix();
            String code = errorMeta.getCode();
            // open.error_20000 = Service Currently Unavailable
            String msg = getErrorMessage(String.join("", modulePrefix, code), locale);
            String subCode = errorMeta.getSubCode();
            // open.error_20000_isp.unknown-error = Service Currently Unavailable
            String subMsg = getErrorMessage(String.join("", modulePrefix, code, UNDERLINE, subCode), locale, params);
            if (StringUtils.isEmpty(msg)) {
                msg = SYS_ERR_MSG;
            }

            if (StringUtils.isEmpty(subMsg)){
                subMsg = sys_err_sub_msg;
            }
            //solution
            //open.error_20000_isp.unknown-error_solution = Service Currently Unavailable
            String solution = getErrorMessage(String.join("",modulePrefix,code,UNDERLINE,subCode,UNDERLINE,"solution"),locale,params);
            error = new SimpleError(code,msg,subCode,subMsg,solution);
            errorCache.put(key,error);
        }
        return error;
    }

    /**
     * 设置错误信息的国际化信息
     * @param errMessageSourceAccessor 国际化错误信息实体类对象
     */
    public static void setErrorMessageSourceAccessor(MessageSourceAccessor errMessageSourceAccessor){
        ErrorFactory.errMessageSourceAccessor = errMessageSourceAccessor;
    }

    /**
     * 返回本地化信息
     * @param module 错误模块
     * @param locale 本地化
     * @param parames 参数
     * @return 信息返回
     */
    public static String getErrorMessage(String module,Locale locale,Object... parames){
        if (noModuleCache.contains(module)){
            return null;
        }
        try {
            return errMessageSourceAccessor.getMessage(module,parames,locale);
        }catch (Exception e){
            noModuleCache.add(module);
            return null;
        }
    }
}
