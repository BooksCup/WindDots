package com.wd.winddots.desktop.list.invoice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.invoice.bean.InvoiceListGroupBean;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: InvoiceListGroupAdapter
 * Author: 郑
 * Date: 2020/7/1 12:14 PM
 * Description:
 */
public class InvoiceListGroupAdapter extends BaseQuickAdapter<InvoiceListGroupBean.InvoiceDetail, BaseViewHolder> {
    public InvoiceListGroupAdapter(int layoutResId, @Nullable List<InvoiceListGroupBean.InvoiceDetail> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceListGroupBean.InvoiceDetail item) {
        String invoiceNumber = StringUtils.isNullOrEmpty(item.getInvoiceNumber()) ? "" : item.getInvoiceNumber();
        String invoiceTypeName = StringUtils.isNullOrEmpty(item.getInvoiceTypeName()) ? "" : item.getInvoiceTypeName();
        String totalAmount = StringUtils.isNullOrEmpty(item.getTotalAmount()) ? "" : item.getTotalAmount();
        String salesName = StringUtils.isNullOrEmpty(item.getSalesName()) ? "" : item.getSalesName();
        String billingTime = StringUtils.isNullOrEmpty(item.getBillingTime()) ? "" : item.getBillingTime();

        helper.setText(R.id.tv_type, invoiceNumber + " " + invoiceTypeName).
                setText(R.id.tv_amount, "金额: " + totalAmount).
                setText(R.id.tv_enterprisename, "开票方: " + salesName).
                setText(R.id.tv_date, "收到日期: " + billingTime);
    }
}
