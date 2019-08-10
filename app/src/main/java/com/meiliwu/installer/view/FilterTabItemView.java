package com.meiliwu.installer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliwu.installer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Implementation of App Widget functionality.
 */
public class FilterTabItemView extends RelativeLayout {

 @BindView(R.id.tv_filter_title)
    TextView tvFilterTitle;

    private Context mContext;
    private String mTitle;
    private boolean isHighlight;

    public FilterTabItemView(Context context) {
        super(context);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_filter_tab_item, this);
        ButterKnife.bind(this, view);
    }

    public FilterTabItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_filter_tab_item, this);
        ButterKnife.bind(this, view);
    }

    public FilterTabItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_filter_tab_item, this);
        ButterKnife.bind(this, view);
    }


    public void setTitle(String title) {
        this.mTitle = title;
        tvFilterTitle.setText(title);
    }

    public boolean isHighlight() {
        return isHighlight;
    }

    public void setHighlight(boolean highlight) {
        isHighlight = highlight;
        if (isHighlight) {
            tvFilterTitle.setTextColor(getResources().getColor(R.color.titleTextSelectedColor));
            tvFilterTitle.getCompoundDrawables()[2].setLevel(1);
        } else {
            tvFilterTitle.setTextColor(getResources().getColor(R.color.titleTextDefaultColor));
            tvFilterTitle.getCompoundDrawables()[2].setLevel(0);
        }
    }
}
