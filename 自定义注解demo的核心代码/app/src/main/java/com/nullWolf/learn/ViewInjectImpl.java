package com.nullWolf.learn;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ContentView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * to be a better man.
 *
 * @author nullWolf
 * @date 2020/1/14
 */
public class ViewInjectImpl implements ViewInject {
    public static ViewInjectImpl instance;

    public ViewInjectImpl() {
    }

    public static ViewInjectImpl getInstance() {
        if (instance == null) {
            synchronized (ViewInjectImpl.class) {
                if (instance == null) {
                    instance = new ViewInjectImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void inject(Activity activity) {
        Class<?> clazz = activity.getClass();
        try {
            BindContentView bindContentView = findContentView(clazz);
            if (bindContentView != null) {
                int layoutId = bindContentView.value();
                if (layoutId > 0) {
                    Method setContentView = clazz.getMethod("setContentView", int.class);
                    setContentView.invoke(activity, layoutId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        injectObject(activity, clazz, new ViewFinder(activity));
    }

    @Override
    public View inject(Object fragment, LayoutInflater inflater, ViewGroup container) {
        Class<?> clazz = fragment.getClass();
        // fragment设置布局
        View view = null;
        BindContentView contentView = findContentView(clazz);
        if (contentView != null) {
            int layoutId = contentView.value();
            if (layoutId > 0) {
                view = inflater.inflate(layoutId, container, false);
            }
        }
        injectObject(fragment, clazz, new ViewFinder(view));
        return view;
    }

    /**
     * 从类中获取ContentView注解
     *
     * @param clazz
     * @return
     */
    private static BindContentView findContentView(Class<?> clazz) {
        return clazz != null ? clazz.getAnnotation(BindContentView.class) : null;
    }

    public static void injectObject(Object target, Class<?> clazz, ViewFinder finder) {
        try {
            injectView(target, clazz, finder);
            injectEvent(target, clazz, finder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置findViewById
     *
     * @param target
     * @param clazz
     * @param finder
     */
    @SuppressWarnings("ConstantConditions")
    private static void injectView(Object target, Class<?> clazz, ViewFinder finder) throws Exception {
        //获取class的所有属性
        Field[] fields = clazz.getDeclaredFields();
        // 遍历并找到所有的BindView注解的属性
        for (Field field : fields) {
            BindView viewById = field.getAnnotation(BindView.class);
            if (viewById != null) {
                // 获取View
                View view = finder.findViewById(viewById.value(), viewById.parentId());
                if (view != null) {
                    // 反射注入view
                    field.setAccessible(true);
                    field.set(target, view);
                } else {
                    throw new Exception("Invalid @Bind for "
                            + clazz.getSimpleName() + "." + field.getName());
                }
            }
        }
    }

    /**
     * 设置Event
     *
     * @param target
     * @param clazz
     * @param finder
     */
    @SuppressWarnings("ConstantConditions")
    private static void injectEvent(Object target, Class<?> clazz, ViewFinder finder) throws Exception {
        // 获取class所有的方法
        Method[] methods = clazz.getDeclaredMethods();
        // 遍历找到onClick注解的方法
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                // 获取注解中的value值
                int[] views = onClick.value();
                int[] parentIds = onClick.parentId();
                int parentLen = parentIds == null ? 0 : parentIds.length;
                for (int i = 0; i < views.length; i++) {
                    // findViewById找到View
                    int viewId = views[i];
                    int parentId = parentLen > i ? parentIds[i] : 0;
                    View view = finder.findViewById(viewId, parentId);
                    if (view != null) {
                        // 设置setOnClickListener反射注入方法
                        view.setOnClickListener(new MyOnClickListener(method, target));
                    } else {
                        throw new Exception("Invalid @OnClick for "
                                + clazz.getSimpleName() + "." + method.getName());
                    }
                }
            }
        }
    }

    private static class MyOnClickListener implements View.OnClickListener {
        private Method method;
        private Object target;

        public MyOnClickListener(Method method, Object target) {
            this.method = method;
            this.target = target;
        }

        @Override
        public void onClick(View v) {
            // 注入方法
            try {
                method.setAccessible(true);
                method.invoke(target, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
