package com.wd.winddots.adapter.contract;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.entity.Linkman;

import java.util.List;

/**
 * 联系人管理
 *
 * @author zhou
 */
public class LinkmanAdapter extends BaseQuickAdapter<Linkman, BaseViewHolder> {

    public LinkmanAdapter(Context context, int layoutResId, @Nullable List<Linkman> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Linkman item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_phone, item.getPhone());

        if (TextUtils.isEmpty(item.getEmail())) {
            helper.setText(R.id.tv_email, "暂无邮箱");
        } else {
            helper.setText(R.id.tv_email, item.getEmail());
        }

        if (TextUtils.isEmpty(item.getCompanyName())) {
            helper.setText(R.id.tv_company_name, "暂无企业");
        } else {
            helper.setText(R.id.tv_company_name, item.getCompanyName());
        }

        if (TextUtils.isEmpty(item.getRemark())) {
            helper.setText(R.id.tv_remark, "暂无备注");
        } else {
            helper.setText(R.id.tv_remark, item.getRemark());
        }
    }

}
