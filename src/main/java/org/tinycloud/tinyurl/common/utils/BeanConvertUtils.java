package org.tinycloud.tinyurl.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 对象转换工具类
 * <p>
 *     单个对象：
 *     B b = BeanConvertUtils.convertTo(a, B::new);
 *     或者
 *     B b = BeanConvertUtils.convertTo(a, B::new, (s, t) -> {
 * 	      t.setXxx(s.getXxs());
 *     });
 * </p>
 *
 * <p>
 *     List 数组
 *     List<B> list = BeanConvertUtils.convertListTo(a, B::new);
 *     或者
 *     List<B> list = BeanConvertUtils.convertListTo(a, B::new, (s, t) -> {
 * 	     t.setXxx(s.getXxs());
 *     });
 * </p>
 *
 * @author liuxingyu01
 * @version 2023-12-11 12:38
 **/
public class BeanConvertUtils {

    /**
     * 属性拷贝
     *
     * @param source         源对象
     * @param targetSupplier 目标对象供应方
     * @param <S>            源对象类型
     * @param <T>            目标对象类型
     * @return 目标对象
     */
    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier) {
        return convertTo(source, targetSupplier, null);
    }

    /**
     * 转换对象
     *
     * @param source         源对象
     * @param targetSupplier 目标对象供应方
     * @param callBack       回调方法
     * @param <S>            源对象类型
     * @param <T>            目标对象类型
     * @return 目标对象
     */
    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        if (null == source || null == targetSupplier) {
            return null;
        }
        T target = targetSupplier.get();
        BeanUtils.copyProperties(source, target);
        if (null != callBack) {
            callBack.callBack(source, target);
        }
        return target;
    }

    /**
     * 属性拷贝
     *
     * @param sources        源对象
     * @param targetSupplier 目标对象供应方
     * @param <S>            源对象类型
     * @param <T>            目标对象类型
     * @return 目标对象list
     */
    public static <S, T> List<T> convertListTo(List<S> sources, Supplier<T> targetSupplier) {
        return convertListTo(sources, targetSupplier, null);
    }

    /**
     * 转换对象
     *
     * @param sources        源对象list
     * @param targetSupplier 目标对象供应方
     * @param callBack       回调方法
     * @param <S>            源对象类型
     * @param <T>            目标对象类型
     * @return 目标对象list
     */
    public static <S, T> List<T> convertListTo(List<S> sources, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        if (null == sources || null == targetSupplier) {
            return null;
        }
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T target = targetSupplier.get();
            BeanUtils.copyProperties(source, target);
            if (null != callBack) {
                callBack.callBack(source, target);
            }
            list.add(target);
        }
        return list;
    }

    /**
     * 回调接口
     *
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     */
    @FunctionalInterface
    public interface ConvertCallBack<S, T> {

        /**
         * 回调方法
         *
         * @param t 源对象
         * @param s 目标对象
         */
        void callBack(S t, T s);
    }

}
