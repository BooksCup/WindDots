package com.wd.winddots.desktop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wd.winddots.R;
import com.wd.winddots.desktop.adapter.ListBottomBarAdapter;
import com.wd.winddots.desktop.view.filter.FilterBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: ListBottomBar
 * Author: 郑
 * Date: 2020/7/7 2:04 PM
 * Description: 办公列表底部工具条
 */
public class ListBottomBar extends LinearLayout {

    private ImageView mAddIcon;
    private ImageView mSeaechIcon;
    private RecyclerView mRecyclerView;
    private ListBottomBarAdapter mAdapter;
    private ListBottomBarActionListener iconClickListener;

    private List<FilterBean> mDataSource = new ArrayList<>();

    public void setData(List<FilterBean> data){
        if (data == null){
            data = new ArrayList<>();
        }
        mDataSource.clear();
        mDataSource.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    public void setListBottomBarActionListener(ListBottomBarActionListener listener){
        iconClickListener = listener;
    }

    public ListBottomBar(Context context) {
        super(context);
        initView();
    }

    public ListBottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ListBottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_list_bottom_bar, this, false);
        mAddIcon = view.findViewById(R.id.bottom_search_add);
        mSeaechIcon = view.findViewById(R.id.bottom_search_search);
        mAddIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iconClickListener != null) {
                    iconClickListener.onAddIconDidClick();
                }
            }
        });

        mSeaechIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iconClickListener != null) {
                    iconClickListener.onSearchIconDidClick();
                }
            }
        });

        mRecyclerView = view.findViewById(R.id.rlist);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager1);
        mAdapter = new ListBottomBarAdapter(R.layout.item_list_bottom_bar,mDataSource);
        mRecyclerView.setAdapter(mAdapter);
        this.addView(view);
    }


    public interface ListBottomBarActionListener{
        /*
         * 点击添加按钮
         * */
        void onAddIconDidClick();

        /*
         * 点击搜索按钮
         * */
        void onSearchIconDidClick();
    }

}
