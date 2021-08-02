package com.cn.processframework.pay;

import java.util.Map;

/**
 * @author apple
 * @package com.cn.processframework.pay
 * @desc <p>回调，可用于类型转换</p>
 * @since
 */
@FunctionalInterface
public interface Callback<T> {
    /**
     * 执行者
     * @param map 需要转化的map
     * @return 处理过后的类型对象
     */
    T perform(Map<String, Object> map);
}
