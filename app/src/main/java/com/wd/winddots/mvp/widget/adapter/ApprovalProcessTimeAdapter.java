package com.wd.winddots.mvp.widget.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.ApprovalProcessBean;

import java.util.List;

public class ApprovalProcessTimeAdapter extends BaseQuickAdapter<ApprovalProcessBean.TimeData.UsersBean, BaseViewHolder> {


    public ApprovalProcessTimeAdapter(int layoutResId, @Nullable List<ApprovalProcessBean.TimeData.UsersBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApprovalProcessBean.TimeData.UsersBean item) {

        helper.setText(R.id.item_approvalpecess_user_name_tv,item.getUserName());

        ImageView ivState = (ImageView) helper.getView(R.id.item_approvalpecess_state_iv);
        TextView tvState = (TextView) helper.getView(R.id.item_approvalpecess_state_tv);
        TextView tvCause = ((TextView) helper.getView(R.id.item_approvalpecess_cause_tv));
        LinearLayout llCause = (LinearLayout) helper.getView(R.id.item_approvalpecess_cause_ll);
        TextView tvTime = (TextView) helper.getView(R.id.item_approvalpecess_time_tv);


        String approvalStatus = item.getApprovalStatus();//审核状态
        switch (approvalStatus){
            case "0"://待处理
                ivState.setImageResource(R.mipmap.waiting);
                tvState.setText("等待处理");
                tvState.setTextColor(Color.parseColor("#FB9637"));
                break;
            case "1"://已通过
                ivState.setImageResource(R.mipmap.success);
                tvState.setText("已通过");
                tvState.setTextColor(Color.parseColor("#329934"));
                break;
            case "2"://已驳回
                ivState.setImageResource(R.mipmap.fail);
                tvState.setText("已驳回");
                tvState.setTextColor(Color.parseColor("#D74049"));
                break;
        }

        String cause = item.getApprovalRemark();
        if (TextUtils.isEmpty(cause)){
            llCause.setVisibility(View.GONE);
        } else {
            llCause.setVisibility(View.VISIBLE);
            tvCause.setText("理由:" + cause);
        }

        String approvalTime = item.getApprovalTime();
        if (TextUtils.isEmpty(approvalTime) || approvalStatus.equals("0")){
            tvTime.setText("");
        } else {
            tvTime.setText(approvalTime);
        }
    }
}
