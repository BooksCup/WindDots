package com.wd.winddots.mvp.widget.adapter.rv;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.ApprovalProcessBean;
import com.wd.winddots.mvp.widget.ApprovalProcessFeeActivity;
import com.wd.winddots.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class ApprovalProcessFeeAdapter extends BaseQuickAdapter<ApprovalProcessBean.MoneyData.DetailListBean.AddressesBean, BaseViewHolder> {

    private final ApprovalProcessFeeActivity mActivity;
    private String mCurrency;
    private RecyclerView mInvoiceRv;
    private final int mScreenWidthPixls;
    private RelativeLayout mInvoiceTypeLayout;
    private RelativeLayout mInvoiceTypeSelectLayout;

    public ApprovalProcessFeeAdapter(ApprovalProcessFeeActivity activity ,int layoutResId, @Nullable List<ApprovalProcessBean.MoneyData.DetailListBean.AddressesBean> data) {
        super(layoutResId, data);

        mActivity = activity;
        mCurrency = mActivity.getCurrency();
        //屏幕宽度
        mScreenWidthPixls = CommonUtils.getScreenWidthPixls(mActivity);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApprovalProcessBean.MoneyData.DetailListBean.AddressesBean item) {

        helper.setText(R.id.item_approvalprocess_fee_title_tv,item.getProjectTitle())
                .setText(R.id.item_approvalprocess_fee_money_tv,item.getAmount() + mCurrency)
                .setText(R.id.item_approvalprocess_fee_time_tv,item.getCreateTime());

        //发票类型 是否显示
        mInvoiceTypeLayout = (RelativeLayout) helper.getView(R.id.item_approvalprocess_fee_invoice_type_view);
        mInvoiceTypeSelectLayout = (RelativeLayout) helper.getView(R.id.item_approvalprocess_fee_invoice_select_view);
        LinearLayout invoiceView = (LinearLayout) helper.getView(R.id.item_approvalprocess_fee_invoice_list_ll);
        mInvoiceRv = (RecyclerView) helper.getView(R.id.item_approvalprocess_fee_invoice_rv);
        TextView tvInvoice = ((TextView) helper.getView(R.id.item_approvalprocess_fee_is_invoice_tv));

        String invoice = item.getInvoice();
        switch (invoice){
            case "0":
                tvInvoice.setText("开票已到");
                break;
            case "1":
                tvInvoice.setText("不开票");
                break;
            case "2":
                tvInvoice.setText("开票未到");
                break;
        }


        if ("1".equals(invoice)){
            invoiceView.setVisibility(View.GONE);
        } else {
            invoiceView.setVisibility(View.VISIBLE);
            setInvoiceList(helper,item);
        }



    }

    /**
     * 设置发票列表
     * @param helper
     * @param item
     */
    private void setInvoiceList(BaseViewHolder helper, ApprovalProcessBean.MoneyData.DetailListBean.AddressesBean item) {
        List<ApprovalProcessBean.MoneyData.DetailListBean.AddressesBean.InvoicesBean> invoices = item.getInvoices();

        BaseQuickAdapter<ApprovalProcessBean.MoneyData.DetailListBean.AddressesBean.InvoicesBean,BaseViewHolder> invoiceAdapter =
                new BaseQuickAdapter<ApprovalProcessBean.MoneyData.DetailListBean.AddressesBean.InvoicesBean, BaseViewHolder>
                        (R.layout.item_approvalprocess_invoice,invoices) {
            @Override
            protected void convert(BaseViewHolder helper, ApprovalProcessBean.MoneyData.DetailListBean.AddressesBean.InvoicesBean item) {
                ImageView ivInvoice = (ImageView) helper.getView(R.id.item_approvalprocess_invoice_iv);
                ivInvoice.getLayoutParams().height = (mScreenWidthPixls - 40) / 3;
                GlideApp.with(mContext)
                        .load(item.getInvoiceImage())
                        .into(ivInvoice);

            }
        };

        GridLayoutManager manager = new GridLayoutManager(mContext,3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mInvoiceRv.setLayoutManager(manager);
        mInvoiceRv.setAdapter(invoiceAdapter);

        int hang;//发票列表几行
        int invoicesSze = invoices.size();
        int yu = invoicesSze % 3;
        int shang = invoicesSze / 3;
        if (yu > 0){
            hang = shang + 1;
        } else {
            hang = shang;
        }

        int rvWidth = hang * (mScreenWidthPixls / 3);
        mInvoiceRv.getLayoutParams().height = rvWidth;

        // 判断是否是申请人 是申请人并且需要开票才可以编辑发票类型和图片列表
        if (0 == 1){
            mInvoiceTypeLayout.setVisibility(View.VISIBLE);
            mInvoiceTypeSelectLayout.setOnClickListener(onInvoiceTypeSelectClickListener);
        } else {
            mInvoiceTypeLayout.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener onInvoiceTypeSelectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}
