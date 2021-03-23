package com.wd.winddots.adapter.contract;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.entity.Contract;
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
                .setText(R.id.tv_sign_date, TimeUtil.formatTimeToDate(item.getCreateTime()));
        String currency = CurrencyEnum.getSymbol(item.getExchangeRateType());
        if (TextUtils.isEmpty(currency)) {
            currency = CurrencyEnum.DEFAULT.getSymbol();
        }
        String amount = currency + item.getContractAmount();

        helper.setText(R.id.tv_amount, amount);



    }

}
