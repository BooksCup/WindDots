package com.wd.winddots.desktop.list.invoice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.invoice.bean.InvoiceListGroupBean;
import com.wd.winddots.desktop.list.invoice.view.InvoiceDetailCell;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: InvoiceDetailAdapter
 * Author: 郑
 * Date: 2020/7/13 1:55 PM
 * Description:
 */
public class InvoiceDetailAdapter extends BaseQuickAdapter<InvoiceListGroupBean.InvoiceDetailData, BaseViewHolder> {

    private String currencyType = "元";//币种

    public void setCurrencyType(String currencyType1){
        currencyType = currencyType1;
    }

    public InvoiceDetailAdapter(int layoutResId, @Nullable List<InvoiceListGroupBean.InvoiceDetailData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceListGroupBean.InvoiceDetailData item) {

        InvoiceDetailCell mGoodserviceNameCell = helper.getView(R.id.cell_goodserviceName);
        mGoodserviceNameCell.setTitle(mContext.getString(R.string.invoice_detail_goodservice_name));
        InvoiceDetailCell mModelCell = helper.getView(R.id.cell_model);
        mModelCell.setTitle(mContext.getString(R.string.invoice_detail_model));
        InvoiceDetailCell mUnitCell = helper.getView(R.id.cell_unit);
        mUnitCell.setTitle(mContext.getString(R.string.invoice_detail_unit));
        InvoiceDetailCell mNumbersCell = helper.getView(R.id.cell_numbers);
        mNumbersCell.setTitle(mContext.getString(R.string.invoice_detail_numbers));
        InvoiceDetailCell mPriceCell = helper.getView(R.id.cell_price);
        mPriceCell.setTitle(mContext.getString(R.string.invoice_detail_price));
        InvoiceDetailCell mSumsCell = helper.getView(R.id.cell_tax_sums);
        mSumsCell.setTitle(mContext.getString(R.string.invoice_detail_sums));
        InvoiceDetailCell mTaxRateCell = helper.getView(R.id.cell_taxRate);
        mTaxRateCell.setTitle(mContext.getString(R.string.invoice_detail_tax_rate));
        InvoiceDetailCell mTaxCell = helper.getView(R.id.cell_tax_tax);
        mTaxCell.setTitle(mContext.getString(R.string.invoice_detail_tax));

        mGoodserviceNameCell.setConten(item.getGoodserviceName());
        mModelCell.setConten(item.getModel());
        mUnitCell.setConten(item.getUnit());
        String number = "";
        if (!StringUtils.isNullOrEmpty(item.getNumber())){
            number = item.getNumber();
        }else if (!StringUtils.isNullOrEmpty(item.getNumbers())){
            number = item.getNumbers();
        }
        String sum = "";
        if (!StringUtils.isNullOrEmpty(item.getSum())){
            sum = item.getSum();
        }else if (!StringUtils.isNullOrEmpty(item.getSums())){
            sum = item.getSums();
        }
        mNumbersCell.setConten(number);
        mPriceCell.setConten(item.getPrice() + currencyType);
        mSumsCell.setConten(sum + currencyType);
        mTaxRateCell.setConten(item.getTaxRate());
        mTaxCell.setConten(item.getTax() + currencyType);
    }
}
