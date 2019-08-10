package com.meiliwu.installer.adapter;

import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author:  ZhangTao
 * Date: 2018/3/6.
 */


public class CommonViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mItemChildViews;

    public CommonViewHolder(View itemView) {
        super(itemView);
        mItemChildViews = new SparseArray<>();
    }


    public static CommonViewHolder createHolder(ViewGroup parent, int itemResId) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(itemResId, parent, false);
        return new CommonViewHolder(itemView);
    }

    public static CommonViewHolder createHolder(View itemView) {
        return new CommonViewHolder(itemView);
    }

//    public static CommonViewHolder createSlideMenuHolder(ViewGroup parent, int itemResId, int menuId) {
//        SlideMenuLayout slideMenuLayout = new SlideMenuLayout(parent.getContext());
//        ViewGroup.LayoutParams layoutParams =
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//        slideMenuLayout.setLayoutParams(layoutParams);
//
//        slideMenuLayout.setMenuLayoutId(menuId);
//        View itemView =
//                LayoutInflater.from(parent.getContext()).inflate(itemResId, slideMenuLayout, false);
//        slideMenuLayout.addView(itemView);
//
//        return new EasyViewHolder(slideMenuLayout);
//    }

    @SuppressWarnings("all")
    public <T extends View> T getView(int childViewId) {
        View childView = mItemChildViews.get(childViewId);
        if (childView == null) {
            childView = itemView.findViewById(childViewId);
            mItemChildViews.put(childViewId, childView);
        }
        return (T) childView;
    }

    public View getItemView() {
        return itemView;
    }

    //TextView
    public CommonViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public CommonViewHolder setTextColor(int viewId, @ColorInt int color) {
        TextView textView = getView(viewId);
        textView.setTextColor(color);
        return this;
    }

    //imageView
    public CommonViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    //Tag
    public CommonViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    /**
     * set checkbox checked
     */
    public CommonViewHolder setChecked(int viewId, boolean checked) {
        CheckBox view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * set view visible or gone
     */
    public CommonViewHolder setVisible(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    //background
    public CommonViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * set view onClickListeners
     */
    public CommonViewHolder setOnClickListener(View.OnClickListener listener, @IdRes int... viewIds) {
        for (int id : viewIds) {
            View view = getView(id);
            view.setOnClickListener(listener);
        }
        return this;
    }
}

