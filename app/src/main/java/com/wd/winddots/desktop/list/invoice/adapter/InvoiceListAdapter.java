package com.wd.winddots.desktop.list.invoice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.invoice.bean.InvoiceListBean;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: InvoiceListAdapter
 * Author: 郑
 * Date: 2020/7/1 11:39 AM
 * Description: 发票列表适配器
 */
public class InvoiceListAdapter extends BaseQuickAdapter<InvoiceListBean.InvoiceItem, BaseViewHolder> {
    public InvoiceListAdapter(int layoutResId, @Nullable List<InvoiceListBean.InvoiceItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceListBean.InvoiceItem item) {
        String invoiceAmountNoTax = StringUtils.isNullOrEmpty(item.getInvoiceAmountNoTax()) ? "0" : item.getInvoiceAmountNoTax();
        invoiceAmountNoTax = "金额: " + invoiceAmountNoTax;
        String taxAmount = StringUtils.isNullOrEmpty(item.getTaxAmount()) ? "0" : item.getTaxAmount();
        taxAmount = "税额: " + taxAmount;
        String invoiceTaxSumAmount = StringUtils.isNullOrEmpty(item.getInvoiceTaxSumAmount()) ? "0" : item.getInvoiceTaxSumAmount();
        invoiceTaxSumAmount = "合计: " + invoiceTaxSumAmount;
        String type = item.getInOutStatus() == 1 ? "进项" : "销项";


        helper.setText(R.id.tv_enterprisename, item.getExchangeEnterpriseName()).
                setText(R.id.tv_amount, invoiceAmountNoTax).
                setText(R.id.tv_tax_amount, taxAmount).
                setText(R.id.tv_total_amount, invoiceTaxSumAmount).
                setText(R.id.tv_type, type);
    }
}
