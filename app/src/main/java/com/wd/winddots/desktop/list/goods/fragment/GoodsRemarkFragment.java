package com.wd.winddots.desktop.list.goods.fragment;

import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;

/**
 * FileName: GoodsRemarkFragment
 * Author: 郑
 * Date: 2020/7/30 9:33 AM
 * Description:
 */
public class GoodsRemarkFragment extends BaseFragment {

    private String mRemark;
    private TextView mContentTv;

    public static BaseFragment newInstance(String remark) {
        GoodsRemarkFragment frament = new GoodsRemarkFragment();
//        frament.compositeSubscription = new CompositeSubscription();
//        frament.dataManager = new ElseDataManager();
//        frament.mGoodsId = goodsId;
        frament.mRemark = remark;
        return frament;
    }

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_goods_detail_remark;
    }

    @Override
    public void initView() {
        mContentTv = mView.findViewById(R.id.tv_content);
        mContentTv.setText(mRemark);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    public void setRemark(String remark){
        mContentTv.setText(remark);
    }
}
