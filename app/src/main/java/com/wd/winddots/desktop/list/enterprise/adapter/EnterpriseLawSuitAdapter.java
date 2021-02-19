package com.wd.winddots.desktop.list.enterprise.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseDetailBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: EnterpriseLawSuitAdapter
 * Author: 郑
 * Date: 2020/12/25 5:23 PM
 * Description:
 */
public class EnterpriseLawSuitAdapter extends BaseQuickAdapter<EnterpriseDetailBean.CompanyLawSuitItem, BaseViewHolder> {


    public EnterpriseLawSuitAdapter(int layoutResId, @Nullable List<EnterpriseDetailBean.CompanyLawSuitItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnterpriseDetailBean.CompanyLawSuitItem item) {
        helper.setText(R.id.tv_title, Utils.nullOrEmpty(item.getTitle())).
                setText(R.id.tv_caseno, "案号:" + Utils.nullOrEmpty(item.getCaseno())).
                setText(R.id.tv_casereason, "案由:" + Utils.nullOrEmpty(item.getCasereason())).
                setText(R.id.tv_ids, "案件身份:" + Utils.nullOrEmpty(item.getPlaintiffs()) +" , "+ Utils.nullOrEmpty(item.getDefendants())).
                setText(R.id.tv_date, "发布日期:" + Utils.nullOrEmpty(item.getJudgetime()));
    }
}
