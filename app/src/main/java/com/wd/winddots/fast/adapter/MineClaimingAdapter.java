package com.wd.winddots.fast.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.fast.bean.ClaimingListBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: MineClaimingAdapter
 * Author: 郑
 * Date: 2020/5/4 6:01 PM
 * Description:
 */
public class MineClaimingAdapter extends BaseQuickAdapter<ClaimingListBean.ListBean, BaseViewHolder> {


    public MineClaimingAdapter(int layoutResId, @Nullable List<ClaimingListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClaimingListBean.ListBean item) {

        GlideApp.with(mContext).load(item.getVoucher() + Utils.OSSImageSize(200)).
                placeholder(R.mipmap.default_img).
                into((ImageView) helper.getView(R.id.item_mine_claiming_icon));

        String title2 = "金额: ";
        String currency = StringUtils.isNullOrEmpty(item.getCurrency()) ? "人民币" : item.getCurrency();
        String amount = StringUtils.isNullOrEmpty(item.getAmount()) ? "0" : item.getAmount();

        String time = item.getModifyTime();

        if (time.length() > 16){
            time= time.substring(0,16);
        }

        String status;

        switch (item.getApprovalStatus()){
            case "0":
                status = "未完成";
                break;
            case "1":
                status = "已完成";
                break;
            case "2":
                status = "驳回";
                break;
            default:
                status = "未完成";
        }

        title2 = title2 + amount + currency;

        helper.setText(R.id.item_mine_claiming_title1, item.getTitle()).
                setText(R.id.item_mine_claiming_title2, title2).
                setText(R.id.item_mine_claiming_title3, "日期: " + time).
                setText(R.id.item_mine_claiming_title4, status);

    }


}
