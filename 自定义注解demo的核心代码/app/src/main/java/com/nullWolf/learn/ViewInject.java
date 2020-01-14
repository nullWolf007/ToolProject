package com.nullWolf.learn;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * to be a better man.
 *
 * @author nullWolf
 * @date 2020/1/14
 */
public interface ViewInject {

    /**
     * 注入Activity
     *
     * @param activity
     */
    void inject(Activity activity);

    /**
     * 注入Fragment
     *
     * @param fragment
     */
    View inject(Object fragment, LayoutInflater inflater, ViewGroup container);
}
