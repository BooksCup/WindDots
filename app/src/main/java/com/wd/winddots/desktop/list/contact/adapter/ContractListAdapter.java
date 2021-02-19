package com.wd.winddots.desktop.list.contact.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.contact.bean.ContractListBean;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: ContractListAdapter
 * Author: 郑
 * Date: 2020/12/15 9:48 AM
 * Description:
 */
public class ContractListAdapter extends BaseQuickAdapter<ContractListBean.ContractListItem, BaseViewHolder> {
    public ContractListAdapter(int layoutResId, @Nullable List<ContractListBean.ContractListItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContractListBean.ContractListItem item) {

        String userId = SpHelper.getInstance(mContext).getUserId();
        String enterpriseId = SpHelper.getInstance(mContext).getEnterpriseId();
        String str2 = "品名: " + Utils.nullOrEmpty(item.getGoodsNamesStr());
        String str3 = "金额: ¥" + Utils.numberNullOrEmpty(item.getContractAmount());
        String str4 = "";
        if (!StringUtils.isNullOrEmpty(item.getCreateTime())){
            if (item.getCreateTime().length() > 10){
                str4 = "时间: " + item.getCreateTime().substring(0,10);
            }else {
                str4 = "时间: " + item.getCreateTime();
            }
        }else {
            str4 = "时间: ";
        }
        String str5 = "";
        if ("P".equals(item.getContractType())) {
            if (enterpriseId.equals(item.getFromEnterpriseId())) {
                str5 = "供应: " + Utils.nullOrEmpty(item.getToEnterpriseName());
            } else {
                str5 = "客户: " + Utils.numberNullOrEmpty(item.getFromEnterpriseId());
            }
        } else {
            if (enterpriseId.equals(item.getFromEnterpriseId())) {
                str5 = "客户: " + Utils.nullOrEmpty(item.getToEnterpriseName());
            } else {
                str5 = "供应: " + Utils.numberNullOrEmpty(item.getFromEnterpriseId());
            }
        }

        String str6 = "";
        if (!StringUtils.isNullOrEmpty(item.getStatus())){
            switch (item.getStatus()) {
                case "0":
                    str6 = "未发送";
                    break;
                case "1":
                    str6 = "发送中";
                    break;
                case "2":
                    str6 = "已确认";
                    break;
                default:
                    str6 = "未发送";
            }
        }else{
            str6 = "未发送";
        }

        helper.setText(R.id.tv_1, Utils.nullOrEmpty(item.getMaterialNumberStr()))
                .setText(R.id.tv_2,str2)
                .setText(R.id.tv_3,str3)
                .setText(R.id.tv_4,str4)
                .setText(R.id.tv_5,str5)
                .setText(R.id.tv_status,str6);
    }
}
