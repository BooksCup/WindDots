package com.wd.winddots.desktop.list.goods.fragment;

import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.list.goods.adapter.GoodsDetailAttrAdapter;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderItemDecoration;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * FileName: GoodsAttrFragment
 * Author: 郑
 * Date: 2020/7/28 11:27 AM
 * Description: 物品属性规格
 */
public class GoodsAttrFragment extends BaseFragment {


    private PinnedHeaderRecyclerView mPinnedHeaderRecyclerView;
    private GoodsDetailAttrAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    public static BaseFragment newInstance() {
        GoodsAttrFragment frament = new GoodsAttrFragment();
        return frament;
    }


    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_goods_detail_attr;
    }

    @Override
    public void initView() {
        mPinnedHeaderRecyclerView = mView.findViewById(R.id.rlist);
        mPinnedHeaderRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(mContext));
        mPinnedHeaderRecyclerView.addItemDecoration(new PinnedHeaderItemDecoration());
        mAdapter = new GoodsDetailAttrAdapter();
        //mRecordDetailAdapter.setContext(mContext);
        mPinnedHeaderRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mPinnedHeaderRecyclerView.setOnPinnedHeaderClickListener(new PinnedHeaderRecyclerView.OnPinnedHeaderClickListener() {
            @Override
            public void onPinnedHeaderClick(int adapterPosition) {
                mAdapter.switchExpand(adapterPosition);
                //标题栏被点击之后，滑动到指定位置
                mLayoutManager.scrollToPositionWithOffset(adapterPosition, 0);
            }
        });
    }

    @Override
    public void initData() {

    }


    public void setData(List<String> attrList, List<String> speList) {

        List<ExpandGroupItemEntity<String, String>> dataList = new ArrayList<>();
        ExpandGroupItemEntity<String, String> attrItem = new ExpandGroupItemEntity<>();
        attrItem.setParent(mContext.getString(R.string.goods_attr));
        attrItem.setChildList(attrList);
        attrItem.setExpand(true);

        ExpandGroupItemEntity<String, String> speItem = new ExpandGroupItemEntity<>();
        speItem.setParent(mContext.getString(R.string.goods_spe));
        speItem.setChildList(speList);
        speItem.setExpand(true);
        dataList.add(attrItem);
        dataList.add(speItem);
        mAdapter.setData(dataList);
    }
}
