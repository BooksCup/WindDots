package com.wd.winddots.fast.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.view.selector.SelectBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: MineClaimingAddDetailAdapter
 * Author: 郑
 * Date: 2020/5/7 11:12 AM
 * Description:
 */
public class MineClaimingAddDetailAdapter extends BaseQuickAdapter<ApplyDetailBean.ClaimingDetail, BaseViewHolder> {


    private List<SelectBean> list = new ArrayList<>();



    public MineClaimingAddDetailAdapter(int layoutResId, @Nullable List<ApplyDetailBean.ClaimingDetail> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final ApplyDetailBean.ClaimingDetail item) {




    }








}
