package com.meiliwu.installer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author:  ZhangTao
 * Date: 2018/3/5.
 */

public abstract class BottomSheetRecyclerAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    private List<T> dataSource;

    public BottomSheetRecyclerAdapter(List<T> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutID(), parent, false);
        return new CommonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        convert(holder, dataSource.get(position), position);
    }


    public abstract void convert(CommonViewHolder holder, T item, int position);

    public abstract int getItemLayoutID();

    @Override
    public int getItemCount() {
        return dataSource == null ? 0 : dataSource.size();
    }

}
