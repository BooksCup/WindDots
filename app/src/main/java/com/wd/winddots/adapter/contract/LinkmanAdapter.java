package com.wd.winddots.adapter.contract;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.entity.Linkman;

import java.util.List;

/**
 * 联系人管理
 */
public class LinkmanAdapter extends BaseQuickAdapter<Linkman, BaseViewHolder> {

    public LinkmanAdapter(Context context, int layoutResId, @Nullable List<Linkman> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Linkman item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_phone, item.getPhone())
                .setText(R.id.tv_email, item.getEmail())
                .setText(R.id.tv_company, item.getCompany())
                .setText(R.id.tv_remark, item.getRemark());
    }

}
