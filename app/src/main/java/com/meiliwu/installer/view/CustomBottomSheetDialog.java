package com.meiliwu.installer.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.meiliwu.installer.R;
import com.meiliwu.installer.adapter.BottomSheetRecyclerAdapter;
import com.meiliwu.installer.adapter.CommonViewHolder;
import com.meiliwu.installer.adapter.CustomItemDecoration;
import com.meiliwu.installer.adapter.OnRecyclerViewItemClickListener;
import com.meiliwu.installer.entity.ISelectable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:  ZhangTao
 * Date: 2018/3/13.
 */

public class CustomBottomSheetDialog extends View {
    @BindView(R.id.bottom_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetRecyclerAdapter<ISelectable> adapter;
    private final List<ISelectable> dataSource = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private String selectItemName;

    public CustomBottomSheetDialog(Context context) {
        super(context);
        init();
    }

    public CustomBottomSheetDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomBottomSheetDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDataList(List<? extends ISelectable> dataList) {
        if (dataList != null) {
            showProgressBar(false);
            dataSource.clear();
            this.dataSource.addAll(dataList);
            adapter.notifyDataSetChanged();
        }
    }

    private void init() {
        bottomSheetDialog = new BottomSheetDialog(this.getContext());
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.laytou_bottom_sheet_dialog, null);
        ButterKnife.bind(this, view);
        bottomSheetDialog.setContentView(view);
        initAdapter();
    }

    private void initAdapter() {
        adapter = new BottomSheetRecyclerAdapter<ISelectable>(dataSource) {
            @Override
            public void convert(CommonViewHolder holder, ISelectable item, int position) {
                if (item.getName().equals(selectItemName)) {
                    holder.setTextColor(R.id.tv_packageName, ContextCompat.getColor(CustomBottomSheetDialog.this.getContext(), R.color.primaryColor));
                } else {
                    holder.setTextColor(R.id.tv_packageName, ContextCompat.getColor(CustomBottomSheetDialog.this.getContext(), R.color.titleTextDefaultColor));
                }

                holder.setText(R.id.tv_packageName, item.getName());
            }

            @Override
            public int getItemLayoutID() {
                return R.layout.layout_bottom_sheet_dialog_item;
            }

        };
        recyclerView.setLayoutManager(new GridLayoutManager(CustomBottomSheetDialog.this.getContext(), 2));
        recyclerView.addItemDecoration(new CustomItemDecoration(2));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnRecyclerViewItemClickListener(recyclerView) {
            @Override
            public void onItemClick(View view, int position) {
                ISelectable entity = dataSource.get(position);
                selectItemName = entity.getName();
                onItemClickListener.ontItemClick(view, entity, position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    public void show() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    public void dismiss() {
        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    @OnClick(R.id.btn_cancel)
    public void onViewClicked() {
        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    public void  showProgressBar(boolean flag){
        if (flag){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public interface OnItemClickListener {
        void ontItemClick(View view, ISelectable packageEntity, int position);
    }
}
