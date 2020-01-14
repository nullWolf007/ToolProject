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
//绑定布局
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BindContentView {
    int value();
}
