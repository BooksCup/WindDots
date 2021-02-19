package com.wd.winddots.desktop.list.goods.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.goods.adapter.GoodsTypeAdapter;
import com.wd.winddots.desktop.list.goods.bean.GoodsTypeBean;
import com.wd.winddots.desktop.view.filter.FilterAdapter;
import com.wd.winddots.desktop.view.filter.FilterBean;
import com.wd.winddots.desktop.view.filter.FilterView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: GoodsFilterView
 * Author: 郑
 * Date: 2020/7/27 10:15 AM
 * Description:
 */
public class GoodsFilterView extends LinearLayout implements FilterAdapter.FilterAdapterOnItemDeleteListener , BaseQuickAdapter.OnItemClickListener {

    private TextView mTitleTextView;
    private RecyclerView mRecyclerView;
    private RecyclerView mTypeRecyclerView;

    private GoodsTypeAdapter mTypeAdapter;
    private List<GoodsTypeBean.GoodsTypeItem> mGoodsTypeList = new ArrayList<>();
    private GoodsTypeBean.GoodsTypeItem mSelectItem;

    private FilterAdapter mAdapter;
    private List<FilterBean> mDataSource = new ArrayList<>();

    private FilterView.FilterViewBottomBarActionListener listener;

    public void setFilterViewBottomBarActionListener(FilterView.FilterViewBottomBarActionListener listener1){
        listener = listener1;
    }


    public void setGoodsTypeList(List<GoodsTypeBean.GoodsTypeItem> list){
        if (list != null && list.size() > 0){
            mSelectItem = list.get(0);
            mGoodsTypeList.addAll(list);
            mTypeAdapter.notifyDataSetChanged();
            mDataSource.addAll(list.get(0).getFilterBeanList());
            mAdapter.notifyDataSetChanged();
        }
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


    public GoodsFilterView(Context context) {
        super(context);
        initView();
    }

    public GoodsFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GoodsFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.view_desktop_goods_filter, this, false);
        mRecyclerView  = view.findViewById(R.id.rlist);
        mTitleTextView  = view.findViewById(R.id.tv_title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new FilterAdapter(R.layout.item_filter, mDataSource);
        mAdapter.setFilterAdapterOnItemDeleteListener(this);
        mRecyclerView.setAdapter(mAdapter);


        mTypeRecyclerView = view.findViewById(R.id.type_list);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        mTypeRecyclerView.setLayoutManager(layoutManager1);
        mTypeRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mTypeAdapter = new GoodsTypeAdapter(R.layout.item_filter_goods_type,mGoodsTypeList);
        mTypeRecyclerView.setAdapter(mTypeAdapter);
        mTypeAdapter.setOnItemClickListener(this);


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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GoodsTypeBean.GoodsTypeItem item = mGoodsTypeList.get(position);
        if (item.getId().equals(mSelectItem.getId())){
            return;
        }
        mSelectItem.setSelect(false);
        mSelectItem.getFilterBeanList().clear();
        mSelectItem.getFilterBeanList().addAll(mDataSource);
        item.setSelect(true);
        mSelectItem = item;
        mDataSource.clear();
        mDataSource.addAll(item.getFilterBeanList());
        mAdapter.notifyDataSetChanged();
        mTypeAdapter.notifyDataSetChanged();

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
