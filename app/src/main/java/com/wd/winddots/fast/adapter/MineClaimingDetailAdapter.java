package com.wd.winddots.fast.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.fast.bean.ApplyDetailBean;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: MineClaimingDetailAdapter
 * Author: 郑
 * Date: 2020/5/5 1:48 PM
 * Description:
 */
public class MineClaimingDetailAdapter extends BaseQuickAdapter<ApplyDetailBean.User, BaseViewHolder> {


    public MineClaimingDetailAdapter(int layoutResId, @Nullable List<ApplyDetailBean.User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyDetailBean.User item) {

        ImageView statusIcon = helper.getView(R.id.item_mine_claiming_statusicon);
        TextView statusText = helper.getView(R.id.item_mine_claiming_statustext);
        TextView remarkText = helper.getView(R.id.item_mine_claiming_remark);

        if ("0".equals(item.getApprovalStatus())){
            statusIcon.setImageResource(R.mipmap.waiting);
            statusText.setText("等待处理");
            statusText.setTextColor(Color.parseColor("#FB9637"));
        }else if ("1".equals(item.getApprovalStatus())){
            statusIcon.setImageResource(R.mipmap.success);
            statusText.setText("已通过");
            statusText.setTextColor(Color.parseColor("#329934"));
        }else if ("2".equals(item.getApprovalStatus())){
            statusIcon.setImageResource(R.mipmap.fail);
            statusText.setText("已驳回");
            statusText.setTextColor(Color.parseColor("#D74049"));
        }

        if (StringUtils.isNullOrEmpty(item.getApprovalRemark())){
            remarkText.setVisibility(View.GONE);
        }else {
            remarkText.setVisibility(View.VISIBLE);
            String remark = "理由: "+item.getApprovalRemark();
            remarkText.setText(remark);
        }







        helper.setText(R.id.item_mine_claiming_name, item.getUserName()).
                setText(R.id.item_mine_claiming_time, item.getApprovalTime());
    }
}
