package com.wd.winddots.desktop.list.enterprise.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseDetailBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: CompanyChangeInfoAdapter
 * Author: 郑
 * Date: 2020/12/25 3:09 PM
 * Description: 变更记录
 */
public class EnterpriseChangeInfoAdapter extends BaseQuickAdapter<EnterpriseDetailBean.CompanyChangeInfo, BaseViewHolder> {
    public EnterpriseChangeInfoAdapter(int layoutResId, @Nullable List<EnterpriseDetailBean.CompanyChangeInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnterpriseDetailBean.CompanyChangeInfo item) {
        String title = (helper.getAdapterPosition() + 1) + ". " + Utils.nullOrEmpty(item.getChangeItem());
        String contentBefore = "变更前:" + Utils.nullOrEmpty(item.getContentBefore());
        String contentAfter = "变更后:" + Utils.nullOrEmpty(item.getContentAfter());
        helper.setText(R.id.tv_title, title).
                setText(R.id.tv_before, contentBefore).
                setText(R.id.tv_after, contentAfter).
                setText(R.id.tv_time, item.getChangeTime());
    }
}
