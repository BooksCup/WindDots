package com.wd.winddots.adapter.contract;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.entity.Contract;
import com.wd.winddots.enums.ContactTypeEnum;
import com.wd.winddots.enums.CurrencyEnum;
import com.wd.winddots.utils.TimeUtil;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 合同
 *
 * @author zhou
 */
public class ContractAdapter extends BaseQuickAdapter<Contract, BaseViewHolder> {

    private String mKeyword;

    public ContractAdapter(Context context, int layoutResId, @Nullable List<Contract> data) {
        super(layoutResId, data);
        mContext = context;
    }

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
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

        if (ContactTypeEnum.SELL.getCode().equals(item.getContractType())) {
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
        }

    }

}
