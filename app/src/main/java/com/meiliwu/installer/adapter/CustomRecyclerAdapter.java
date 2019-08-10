package com.meiliwu.installer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meiliwu.installer.R;

import java.util.List;

/**
 * Author:  ZhangTao
 * Date: 2018/3/5.
 */

public abstract class CustomRecyclerAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    private List<T> dataSource;

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public static final int LOADING = 1;
    // 加载完成
    public static final int LOADING_COMPLETE = 2;
    // 加载到底
    public static final int LOADING_END = 3;

    public CustomRecyclerAdapter(List<T> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutID(), parent, false);
                return new CommonViewHolder(itemView);
            case TYPE_FOOTER:
                View footerView = LayoutInflater.from(parent.getContext()).inflate(getFootViewLayoutID(), parent, false);
                return new CommonViewHolder(footerView);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                convert(holder, dataSource.get(position), position);
                break;
            case TYPE_FOOTER:
                switch (loadState) {
                    case LOADING: // 正在加载
                        holder.setVisible(R.id.progressBar, View.VISIBLE);
                        holder.setVisible(R.id.tv_loadMore, View.VISIBLE);
                        holder.setVisible(R.id.tv_loadEnd, View.GONE);
                        break;

                    case LOADING_COMPLETE: // 加载完成,footerview 隐藏
                        holder.setVisible(R.id.progressBar, View.INVISIBLE);
                        holder.setVisible(R.id.tv_loadMore, View.INVISIBLE);
                        holder.setVisible(R.id.tv_loadEnd, View.GONE);
                        break;

                    case LOADING_END: // 加载到底
                        holder.setVisible(R.id.progressBar, View.GONE);
                        holder.setVisible(R.id.tv_loadMore, View.GONE);
                        holder.setVisible(R.id.tv_loadEnd, View.VISIBLE);
                        break;

                    default:
                        break;
                }


        }
    }


    public abstract void convert(CommonViewHolder holder, T item, int position);

    public abstract int getItemLayoutID();

    public abstract int getFootViewLayoutID();

    @Override
    public int getItemCount() {
        return dataSource == null ? 0 : dataSource.size() + 1;
    }


    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}
