package com.nullWolf.learn;

import android.app.Activity;
import android.view.View;

/**
 * to be a better man.
 *
 * @author nullWolf
 * @date 2020/1/14
 */

public class ViewFinder {
    private View view;
    private Activity activity;

    public ViewFinder(View view) {
        this.view = view;
    }

    public ViewFinder(Activity activity) {
        this.activity = activity;
    }

    public View findViewById(int viewId) {
        if (view != null) {
            return view.findViewById(viewId);
        }
        if (activity != null) {
            return activity.findViewById(viewId);
        }
        return null;
    }

    public View findViewById(int viewId, int parentId) {
        View parentView = null;
        if (parentId > 0) {
            parentView = findViewById(parentId);
        }
        View childView = null;
        if (parentView != null && viewId > 0) {
            childView = parentView.findViewById(viewId);
        } else if (viewId > 0) {
            childView = findViewById(viewId);
        }
        return childView;
    }
}