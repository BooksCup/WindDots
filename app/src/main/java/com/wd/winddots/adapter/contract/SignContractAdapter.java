package com.wd.winddots.adapter.contract;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.entity.Contract;
import com.wd.winddots.enums.ContactTypeEnum;
import com.wd.winddots.enums.CurrencyEnum;
import com.wd.winddots.enums.SendStatusEnum;
import com.wd.winddots.utils.TimeUtil;

import java.util.List;

public class SignContractAdapter extends BaseQuickAdapter<Contract, BaseViewHolder> {

    private String mKeyword;

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }

    public SignContractAdapter(Context context, int layoutResId, @Nullable List<Contract> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Contract item) {
        if (!TextUtils.isEmpty(item.getThemeTitleStr())) {
            helper.setText(R.id.tv_contract_title, item.getThemeTitleStr());
        } else {
            helper.setText(R.id.tv_contract_title, item.getContractNo());
        }

        helper.setText(R.id.tv_goods_info, item.getGoodsNamesStr())
                .setText(R.id.tv_related_company, item.getToEnterpriseName())
                .setText(R.id.tv_sign_date, TimeUtil.formatTimeToDate(item.getCreateTime()));
        String currency = CurrencyEnum.getSymbol(item.getExchangeRateType());
        if (TextUtils.isEmpty(currency)) {
            currency = CurrencyEnum.DEFAULT.getSymbol();
        }
        String amount = currency + item.getContractAmount();

        helper.setText(R.id.tv_amount, amount);

        TextView mContactTypeTv = helper.getView(R.id.tv_contact_type);
        mContactTypeTv.setVisibility(View.GONE);//合同类型
        TextView mSendStatusTv = helper.getView(R.id.tv_send_status);
        mSendStatusTv.setVisibility(View.GONE);//发送状态
        /*if (ContactTypeEnum.SELL.getCode().equals(item.getContractType())) {
            // 销售合同
            mContactTypeTv.setVisibility(View.VISIBLE);
            mContactTypeTv.setText("销售");
            mContactTypeTv.setBackgroundResource(R.drawable.bg_label_green);

        } else if (ContactTypeEnum.PURCHASE.getCode().equals(item.getContractType())) {
            // 采购合同
            mContactTypeTv.setVisibility(View.VISIBLE);
            mContactTypeTv.setText("采购");
            mContactTypeTv.setBackgroundResource(R.drawable.bg_label_orange);

        } else if (ContactTypeEnum.PROCESSING.getCode().equals(item.getContractType())) {
            // 加工合同
            mContactTypeTv.setVisibility(View.VISIBLE);
            mContactTypeTv.setText("加工");
            mContactTypeTv.setBackgroundResource(R.drawable.bg_label_red);

        } else {
            mContactTypeTv.setVisibility(View.GONE);
        }*/

        // 发送状态
       /* if (SendStatusEnum.UN_SENT.getCode().equals(item.getSendStatus())) {
            // 未发送
            mSendStatusTv.setVisibility(View.VISIBLE);
            mSendStatusTv.setText(SendStatusEnum.UN_SENT.getName());
            mSendStatusTv.setTextColor(mContext.getResources().getColor(R.color.tag_grey));
            mSendStatusTv.setBackgroundResource(R.drawable.bg_tag_grey);
        } else if (SendStatusEnum.SENTED.getCode().equals(item.getSendStatus())) {
            // 未确认
            mSendStatusTv.setVisibility(View.VISIBLE);
            mSendStatusTv.setText(SendStatusEnum.SENTED.getName());
            mSendStatusTv.setTextColor(mContext.getResources().getColor(R.color.tag_green));
            mSendStatusTv.setBackgroundResource(R.drawable.bg_tag_green);
        } else if (SendStatusEnum.CONFIRMED.getCode().equals(item.getSendStatus())) {
            // 已确认
            mSendStatusTv.setVisibility(View.VISIBLE);
            mSendStatusTv.setText(SendStatusEnum.CONFIRMED.getName());
            mSendStatusTv.setTextColor(mContext.getResources().getColor(R.color.tag_orange));
            mSendStatusTv.setBackgroundResource(R.drawable.bg_tag_orange);
        } else {
            // 未知
            mSendStatusTv.setVisibility(View.GONE);
        }*/

    }


}
