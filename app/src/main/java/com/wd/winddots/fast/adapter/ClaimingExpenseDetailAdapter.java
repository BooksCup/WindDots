package com.wd.winddots.fast.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.view.TitleTextView;
import com.wd.winddots.view.selector.SelectBean;
import com.wd.winddots.view.selector.Selector;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: ClaimingExpenseDetailAdapter
 * Author: 郑
 * Date: 2020/5/19 4:37 PM
 * Description:
 */
public class ClaimingExpenseDetailAdapter extends BaseQuickAdapter<ApplyDetailBean.ClaimingDetail, BaseViewHolder> {


    private ApplyDetailBean claimingDetailBean;
    private ClaimingExpenseDetailAdapterActionListener listener;
    private int mItemS;
    private String userId;

    public void setClaimingExpenseDetailAdapterActionListener(ClaimingExpenseDetailAdapterActionListener listener) {
        this.listener = listener;
    }

    public void setmItemS(int mItemS) {
        this.mItemS = mItemS;
    }

    public void setClaimingDetailBean(ApplyDetailBean claimingDetailBean) {
        this.claimingDetailBean = claimingDetailBean;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ClaimingExpenseDetailAdapter(int layoutResId, @Nullable List<ApplyDetailBean.ClaimingDetail> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ApplyDetailBean.ClaimingDetail item) {

        TitleTextView amountCell = helper.getView(R.id.item_claiming_expense_anount);
        TitleTextView timeCell = helper.getView(R.id.item_claiming_expense_time);
        TitleTextView invoiceCell = helper.getView(R.id.item_claiming_expense_invoice);
        Selector invoiceTypeCell = helper.getView(R.id.item_claiming_expense_invoic_selector);
        TextView invouceTextView = helper.getView(R.id.item_claiming_expense_invoicetext);
        final RecyclerView recyclerView = helper.getView(R.id.item_claiming_expense_rlist);
        amountCell.setTitle("金额");
        timeCell.setTitle("时间");
        invoiceCell.setTitle("是否开票");
        invoiceCell.setContent("不开票");
        invoiceTypeCell.setTitleText("发票类型");
        final List<SelectBean> selectBeanList = new ArrayList<>();
        selectBeanList.add(new SelectBean("开票已到", "0"));
        selectBeanList.add(new SelectBean("开票未到", "2"));
        invoiceTypeCell.setSelectList(selectBeanList);

        helper.setText(R.id.item_claiming_expense_detail_title, item.getProjectTitle());
        amountCell.setContent(item.getAmount() + claimingDetailBean.getCurrency());
        timeCell.setContent(item.getTime());


        if (Const.INVOICE_TYPE_NO_INVOICE.equals(item.getInvoice())) {
            invoiceCell.setVisibility(View.VISIBLE);
            invoiceTypeCell.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            invouceTextView.setVisibility(View.GONE);
        } else {
            invoiceCell.setVisibility(View.GONE);
            invoiceTypeCell.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            invouceTextView.setVisibility(View.VISIBLE);
            if (Const.INVOICE_TYPE_NO_ALREADY.equals(item.getInvoice())) {
                invoiceTypeCell.setDefaultPosition(1);
            } else {
                invoiceTypeCell.setDefaultPosition(0);
            }
        }

        invoiceTypeCell.setSelectorOnselectListener(new Selector.SelectorOnselectListener() {
            @Override
            public void onselect(int position, Selector selector) {
                if (listener != null) {
                    listener.updateApplyInvoiceType(item, selectBeanList.get(position).getValue());
                }
            }
        });

        final List<ApplyDetailBean.Invoice> invoiceList = item.getInvoices();


        final MineClaimingImagePickerAdpater adpater = new MineClaimingImagePickerAdpater(R.layout.item_image_pcker, item.getInvoices(), mItemS);
        if (userId.equals(claimingDetailBean.getCreatedBy())) {
            adpater.setDeleteIconVisibility(View.VISIBLE);
            invoiceList.add(new ApplyDetailBean.Invoice());
        } else {
            adpater.setDeleteIconVisibility(View.GONE);
        }


        //adpater.setOnItemClickListener(this);
        //adpater.setMineClaimingImagePickerAdpaterDeleteListener(this);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adpater);
        int rowCount = ((invoiceList.size() - 1) / 3) + 1;
        recyclerView.getLayoutParams().height = rowCount * mItemS;

        adpater.setMineClaimingImagePickerAdpaterDeleteListener(new MineClaimingImagePickerAdpater.MineClaimingImagePickerAdpaterDeleteListener() {
            @Override
            public void deleIconDidClick(ApplyDetailBean.Invoice invoice) {

                invoiceList.remove(invoice);
                adpater.notifyDataSetChanged();
                int rowCount = ((invoiceList.size() - 1) / 3) + 1;
                recyclerView.getLayoutParams().height = rowCount * mItemS;
                if (listener != null){
                    listener.updateApplyInvoiceImage(helper);
                }
            }
        });



        adpater.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ApplyDetailBean.Invoice invoice = invoiceList.get(position);
                if (invoice.getUri() == null && StringUtils.isNullOrEmpty(invoice.getInvoiceImage())){
                    if (listener != null){
                        listener.addApplyInvoiceImage(helper);
                    }
                }
            }
        });
    }

    public interface ClaimingExpenseDetailAdapterActionListener {
        void updateApplyInvoiceType(ApplyDetailBean.ClaimingDetail item, String invoice);
        void updateApplyInvoiceImage(BaseViewHolder holder);
        void addApplyInvoiceImage(BaseViewHolder holder);
    }


}
