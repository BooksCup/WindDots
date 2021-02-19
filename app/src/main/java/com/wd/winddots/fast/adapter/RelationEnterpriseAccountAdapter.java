package com.wd.winddots.fast.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.fast.bean.RelationEnterpriseBean;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: RelationEnterpriseAccountAdapter
 * Author: 郑
 * Date: 2020/5/13 3:14 PM
 * Description:
 */
public class RelationEnterpriseAccountAdapter extends BaseQuickAdapter<RelationEnterpriseBean.Account, BaseViewHolder> {


    private RelationEnterpriseBean.Enterprise mEnterprise;

    public RelationEnterpriseBean.Enterprise getEnterprise() {
        return mEnterprise;
    }

    public RelationEnterpriseAccountAdapter(int layoutResId, @Nullable List<RelationEnterpriseBean.Account> data, RelationEnterpriseBean.Enterprise enterprise) {
        super(layoutResId, data);
        mEnterprise = enterprise;
    }

    @Override
    protected void convert(BaseViewHolder helper, RelationEnterpriseBean.Account item) {


        String bank = "开户行: " + (StringUtils.isNullOrEmpty(item.getBank()) ? "" : item.getBank());
        String number = "账号: " + (StringUtils.isNullOrEmpty(item.getNumber()) ? "" : item.getNumber());

        helper.setText(R.id.item_mine_claiming_enterpriseaccount_name, item.getName()).
                setText(R.id.item_mine_claiming_enterpriseaccount_bank, bank).
                setText(R.id.item_mine_claiming_enterpriseaccount_number, number);
    }
}
