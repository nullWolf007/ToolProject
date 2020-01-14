package com.nullWolf.learn;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * to be a better man.
 *
 * @author nullWolf
 * @date 2020/1/14
 */
//点击事件
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnClick {
    int[] value();

    int[] parentId() default 0;
}
