package com.meiliwu.installer.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meiliwu.installer.utils.DisplayUtil;

/**
 * Author:  ZhangTao
 * Date: 2018/3/9.
 */

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;

    private int recyclerViewHorizontalPadding = 15;
    private int recyclerViewVerticalPadding = 15;
    private int itemHorizontalPadding = 15;
    private int itemVerticalPadding = 10;

    public CustomItemDecoration(int spanCount) {
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int currentPosition = parent.getChildLayoutPosition(view);
        if (currentPosition % spanCount == 0) {
            //第一列
            outRect.left = DisplayUtil.dip2px(parent.getContext(), recyclerViewHorizontalPadding);
            outRect.right = DisplayUtil.dip2px(parent.getContext(), itemHorizontalPadding)/2;
        } else if (currentPosition % spanCount == spanCount - 1) {
            //最后一列
            outRect.left = DisplayUtil.dip2px(parent.getContext(), itemHorizontalPadding)/2;
            outRect.right = DisplayUtil.dip2px(parent.getContext(), recyclerViewHorizontalPadding);
        } else {
            outRect.left = DisplayUtil.dip2px(parent.getContext(), itemHorizontalPadding)/2;
            outRect.right = DisplayUtil.dip2px(parent.getContext(), itemHorizontalPadding)/2;
        }
        if (isFirstRow(parent, currentPosition)) {
            outRect.top = DisplayUtil.dip2px(parent.getContext(), recyclerViewVerticalPadding);
            outRect.bottom = DisplayUtil.dip2px(parent.getContext(), itemVerticalPadding)/2;
        } else if (isLastRow(parent, currentPosition)) {
            outRect.top = DisplayUtil.dip2px(parent.getContext(), itemVerticalPadding)/2;
            outRect.bottom = DisplayUtil.dip2px(parent.getContext(), recyclerViewVerticalPadding);
        } else {
            outRect.top = DisplayUtil.dip2px(parent.getContext(), itemVerticalPadding)/2;
            outRect.bottom = DisplayUtil.dip2px(parent.getContext(), itemVerticalPadding)/2;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    private boolean isFirstRow(RecyclerView parent, int position) {
        return (position - position%spanCount)/spanCount == 0;
    }

    private boolean isLastRow(RecyclerView parent, int position) {
        int childCount = parent.getAdapter().getItemCount();
        return position >= childCount - childCount%spanCount;
    }

    private int getRowIndex(RecyclerView parent, int position) {
        return (position - position%spanCount)/spanCount;
    }
}
