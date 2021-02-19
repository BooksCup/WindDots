package com.wd.winddots.desktop.view.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.winddots.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FilterView
 * Author: 郑
 * Date: 2020/7/2 11:45 AM
 * Description: 筛选侧滑
 */
public class FilterView extends LinearLayout implements FilterAdapter.FilterAdapterOnItemDeleteListener {

    private TextView mTitleTextView;
    private RecyclerView mRecyclerView;

    private FilterAdapter mAdapter;
    private List<FilterBean> mDataSource = new ArrayList<>();

    private FilterViewBottomBarActionListener listener;

    public void setFilterViewBottomBarActionListener(FilterViewBottomBarActionListener listener1){
        listener = listener1;
    }



    public void setFilterList(List<FilterBean> list){
        if (list == null){
            list = new ArrayList<>();
        }
        mDataSource.clear();
        mDataSource.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    public List<FilterBean> getFilterList(){
        List<FilterBean> list = new ArrayList<>();
        for (int i = 0;i < mDataSource.size();i++){
            FilterBean bean = mDataSource.get(i);
            if (!StringUtils.isNullOrEmpty(bean.getValue())){
                list.add(bean);
            }
        }
        return list;
    }


    public FilterView(Context context) {
        super(context);
        initView();
    }

    public FilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_desktop_filter, this, false);
        mRecyclerView  = view.findViewById(R.id.rlist);
        mTitleTextView  = view.findViewById(R.id.tv_title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new FilterAdapter(R.layout.item_filter, mDataSource);
        mAdapter.setFilterAdapterOnItemDeleteListener(this);
        mRecyclerView.setAdapter(mAdapter);
        View footer = View.inflate(getContext(), R.layout.view_filter_footer, null);
        footer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterBean bean1 = new FilterBean();
                mDataSource.add(bean1);
                mAdapter.notifyDataSetChanged();
            }
        });

        TextView resetBtn = view.findViewById(R.id.tv_reset);
        TextView confirmBtn = view.findViewById(R.id.tv_commit);
        resetBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSource.clear();
                mAdapter.notifyDataSetChanged();
            }
        });
        confirmBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onCommitBtnDidClick();
                }
            }
        });
        mAdapter.setFooterView(footer);
        this.addView(view);
    }

    public void setTitle(String title){
        mTitleTextView.setText(title);
    }

    @Override
    public void onDelete(FilterBean item) {
        mDataSource.remove(item);
        mAdapter.notifyDataSetChanged();
    }

    public interface FilterViewBottomBarActionListener{
        /*
        * 点击重置按钮
        * */
        void onResetBtnDidClick();

        /*
         * 点击确认按钮
         * */
        void onCommitBtnDidClick();

    }
}
