package com.wd.winddots.desktop.list.invoice.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.desktop.list.invoice.adapter.InvoiceDetailAdapter;
import com.wd.winddots.desktop.list.invoice.bean.InvoiceDetailBean;
import com.wd.winddots.desktop.list.invoice.bean.InvoiceListGroupBean;
import com.wd.winddots.desktop.list.invoice.presenter.impl.InvoiceDetailPresenterImpl;
import com.wd.winddots.desktop.list.invoice.presenter.view.InvoiceDetailView;
import com.wd.winddots.desktop.list.invoice.view.InvoiceDetailCell;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.selector.SelectBean;
import com.wd.winddots.view.selector.Selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: InvoiceDetailActivity
 * Author: 郑
 * Date: 2020/7/1 3:11 PM
 * Description: 发票详情
 */
public class InvoiceDetailActivity extends CommonActivity<InvoiceDetailView, InvoiceDetailPresenterImpl> implements Selector.SelectorOnselectListener,InvoiceDetailView {

    private CompositeSubscription compositeSubscription;
    private MsgDataManager dataManager;
    private ElseDataManager elseDataManager;

    private InvoiceDetailCell mAmountCell;
    private InvoiceDetailCell mTotalTaxNumCell;
    private InvoiceDetailCell mTotalTaxSumCell;
    private InvoiceDetailCell mInvoiceDataCodeCell;
    private InvoiceDetailCell mInvoiceNumberCell;
    private InvoiceDetailCell mBillingTimeCell;
    private InvoiceDetailCell mVoidMarkCell;
    private InvoiceDetailCell mBillMarkCell;
    private InvoiceDetailCell mCheckCodeCell;
    private InvoiceDetailCell mTaxDiskCodeCell;
    private InvoiceDetailCell mPurchaserNameCell;
    private InvoiceDetailCell mTaxpayerNumberCell;
    private InvoiceDetailCell mTaxpayerAddressOrIdCell;
    private InvoiceDetailCell mTaxpayerBankAccountCell;
    private InvoiceDetailCell mSalesNameCell;
    private InvoiceDetailCell mSalesTaxpayerNumCell;
    private InvoiceDetailCell mSalesTaxpayerAddressCell;
    private InvoiceDetailCell mSalesTaxpayerBankAccountCell;
    private InvoiceDetailCell mGoodserviceNameCell;
    private InvoiceDetailCell mModelCell;
    private InvoiceDetailCell mUnitCell;
    private InvoiceDetailCell mNumbersCell;
    private InvoiceDetailCell mPriceCell;
    private InvoiceDetailCell mSumsCell;
    private InvoiceDetailCell mTaxRateCell;
    private InvoiceDetailCell mTaxCell;
    private InvoiceDetailCell mRemarkCell;

    private RecyclerView mRecyclerView;
    private InvoiceDetailAdapter mAdapter;
    private List<InvoiceListGroupBean.InvoiceDetailData> mDataSource = new ArrayList<>();

    private TextView mInvoiceTypeTextView;
    private Selector mSelector;

    private List<SelectBean> selectBeans = new ArrayList<>();
    private InvoiceListGroupBean.InvoiceDetail mData;
    private String mId;
    private String mJsonData; //扫描出来的 json 数据

    private boolean selectTarget = false;



    @Override
    public InvoiceDetailPresenterImpl initPresenter() {
        return new InvoiceDetailPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();

        compositeSubscription = new CompositeSubscription();
        dataManager = new MsgDataManager();
        elseDataManager = new ElseDataManager();

        setTitleText(mContext.getString(R.string.invoice_detail_title));
        addBadyView(R.layout.activity_invoice_detail);

        Intent intent = getIntent();
        String jsonS = intent.getStringExtra("data");
        String id = intent.getStringExtra("id");
        if (!StringUtils.isNullOrEmpty(jsonS)){
            Gson gson = new Gson();
            mData = gson.fromJson(jsonS, InvoiceDetailBean.class).getData();
            mJsonData = jsonS;
        }
        if (!StringUtils.isNullOrEmpty(id)){
            mId = id;
        }

        View headerView = View.inflate(this, R.layout.view_invoice_detail_header, null);
        mSelector = headerView.findViewById(R.id.selector);
        mSelector.setTitleText(mContext.getString(R.string.invoice_add_in_out_status));
        mInvoiceTypeTextView = headerView.findViewById(R.id.tv_invoiceType);
        mAmountCell = headerView.findViewById(R.id.cell_amount);
        mAmountCell.setTitle(mContext.getString(R.string.invoice_detail_amount));
        mTotalTaxNumCell = headerView.findViewById(R.id.cell_tax_amount);
        mTotalTaxNumCell.setTitle(mContext.getString(R.string.invoice_detail_total_tax_num));
        mTotalTaxSumCell = headerView.findViewById(R.id.cell_tax_total);
        mTotalTaxSumCell.setTitle(mContext.getString(R.string.invoice_detail_total_tax_sum));

        mInvoiceDataCodeCell = headerView.findViewById(R.id.cell_invoice_code);
        mInvoiceDataCodeCell.setTitle(mContext.getString(R.string.invoice_detail_invoice_data_code));
        mInvoiceNumberCell = headerView.findViewById(R.id.cell_invoice_number);
        mInvoiceNumberCell.setTitle(mContext.getString(R.string.invoice_detail_invoice_number));
        mBillingTimeCell = headerView.findViewById(R.id.cell_invoice_date);
        mBillingTimeCell.setTitle(mContext.getString(R.string.invoice_detail_billing_time));
        mVoidMarkCell = headerView.findViewById(R.id.cell_invoice_usable);
        mVoidMarkCell.setTitle(mContext.getString(R.string.invoice_detail_void_mark));
        mBillMarkCell = headerView.findViewById(R.id.cell_invoice_billmark);
        mBillMarkCell.setTitle(mContext.getString(R.string.invoice_detail_is_bill_mark));
        mCheckCodeCell = headerView.findViewById(R.id.cell_check_code);
        mCheckCodeCell.setTitle(mContext.getString(R.string.invoice_detail_check_code));
        mTaxDiskCodeCell = headerView.findViewById(R.id.cell_tax_diskCode);
        mTaxDiskCodeCell.setTitle(mContext.getString(R.string.invoice_detail_tax_disk_code));

        mPurchaserNameCell = headerView.findViewById(R.id.cell_purchaserName);
        mPurchaserNameCell.setTitle(mContext.getString(R.string.invoice_detail_purchaser_name));
        mTaxpayerNumberCell = headerView.findViewById(R.id.cell_taxpayerNumber);
        mTaxpayerNumberCell.setTitle(mContext.getString(R.string.invoice_detail_taxpayer_number));
        mTaxpayerAddressOrIdCell = headerView.findViewById(R.id.cell_taxpayerAddressOrId);
        mTaxpayerAddressOrIdCell.setTitle(mContext.getString(R.string.invoice_detail_taxpayer_address_or_id));
        mTaxpayerBankAccountCell = headerView.findViewById(R.id.cell_taxpayerBankAccount);
        mTaxpayerBankAccountCell.setTitle(mContext.getString(R.string.invoice_detail_taxpayer_bank_account));

        mSalesNameCell = headerView.findViewById(R.id.cell_salesName);
        mSalesNameCell.setTitle(mContext.getString(R.string.invoice_detail_sales_name));
        mSalesTaxpayerNumCell = headerView.findViewById(R.id.cell_salesTaxpayerNum);
        mSalesTaxpayerNumCell.setTitle(mContext.getString(R.string.invoice_detail_taxpayer_number));
        mSalesTaxpayerAddressCell = headerView.findViewById(R.id.cell_salesTaxpayerAddress);
        mSalesTaxpayerAddressCell.setTitle(mContext.getString(R.string.invoice_detail_taxpayer_address_or_id));
        mSalesTaxpayerBankAccountCell = headerView.findViewById(R.id.cell_salesTaxpayerBankAccount);
        mSalesTaxpayerBankAccountCell.setTitle(mContext.getString(R.string.invoice_detail_taxpayer_bank_account));

        mRemarkCell = new InvoiceDetailCell(mContext);//findViewById(R.id.cell_invoiceRemarks);
        mRemarkCell.setTitle(mContext.getString(R.string.invoice_detail_invoiceRemarks));

        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new InvoiceDetailAdapter(R.layout.item_invoice_detail, mDataSource);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setFooterView(mRemarkCell);
        mAdapter.setHeaderView(headerView);

        LinearLayout saveBtn = findViewById(R.id.ll_save);
        boolean isScan = intent.getBooleanExtra("isScan",false);
        if (mData != null && StringUtils.isNullOrEmpty(mData.getId())){
            saveBtn.setVisibility(View.VISIBLE);
        }else {
            saveBtn.setVisibility(View.GONE);
        }
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        SelectBean bean1 = new SelectBean("进项","1");
        SelectBean bean2 = new SelectBean("销项","2");
        selectBeans.add(bean1);
        selectBeans.add(bean2);
        mSelector.setSelectList(selectBeans);
        if (mData != null){
            setUpData();
        }
        if (!StringUtils.isNullOrEmpty(mId)){
            presenter.getInvoiceDetail(mId);
        }
    }


    private void setUpData(){
        mInvoiceTypeTextView.setText(mData.getInvoiceTypeName());
        if (mData.getInOutStatus() == 2){
            mSelector.setDefaultPosition(1);
        }else {
            mSelector.setDefaultPosition(0);
        }

        String currencyType = "";
        switch (mData.getCurrency()) {
            case 1:
                currencyType = mContext.getString(R.string.currency_dollar);
                break;
            case 2:
                currencyType = mContext.getString(R.string.currency_euro);
                break;
            case 3:
                currencyType = mContext.getString(R.string.currency_pound);
                break;
            case 4:
                currencyType = mContext.getString(R.string.currency_hkd);
                break;
            case 5:
                currencyType = mContext.getString(R.string.currency_cad);
                break;
            case 6:
                currencyType = mContext.getString(R.string.currency_yen);
                break;
            case 7:
                currencyType = mContext.getString(R.string.currency_ntc);
                break;
            case 8:
                currencyType = mContext.getString(R.string.currency_vnd);
                break;
            case 9:
                currencyType = mContext.getString(R.string.currency_rmb);
                break;
            default:
                currencyType = mContext.getString(R.string.currency_rmb);
                break;
        }
        mAdapter.setCurrencyType(currencyType);

        mAmountCell.setConten(StringUtils.isNullOrEmpty(mData.getTotalAmount())?"":(mData.getTotalAmount() + currencyType));
        mTotalTaxNumCell.setConten(StringUtils.isNullOrEmpty(mData.getTotalTaxNum())?"":(mData.getTotalTaxNum() + currencyType));
        mTotalTaxSumCell.setConten(StringUtils.isNullOrEmpty(mData.getTotalTaxSum())?"":(mData.getTotalTaxSum() + currencyType));

        mInvoiceDataCodeCell.setConten(StringUtils.isNullOrEmpty(mData.getInvoiceDataCode())?"":mData.getInvoiceDataCode());
        mInvoiceNumberCell.setConten(StringUtils.isNullOrEmpty(mData.getTotalTaxSum())?"":mData.getInvoiceNumber());
        mBillingTimeCell.setConten(StringUtils.isNullOrEmpty(mData.getTotalTaxSum())?"":mData.getBillingTime());
        mVoidMarkCell.setConten( "0".equals(mData.getVoidMark()) ? "正常":"作废");
        mBillMarkCell.setConten( "Y".equals(mData.getIsBillMark()) ? "是":"否");
        mCheckCodeCell.setConten(StringUtils.isNullOrEmpty(mData.getCheckCode())?"":mData.getCheckCode());
        mTaxDiskCodeCell.setConten(StringUtils.isNullOrEmpty(mData.getTaxDiskCode())?"":mData.getTaxDiskCode());

        mPurchaserNameCell.setConten(StringUtils.isNullOrEmpty(mData.getPurchaserName())?"":mData.getPurchaserName());
        mTaxpayerNumberCell.setConten(StringUtils.isNullOrEmpty(mData.getTaxpayerNumber())?"":mData.getTaxpayerNumber());
        mTaxpayerAddressOrIdCell.setConten(StringUtils.isNullOrEmpty(mData.getTaxpayerAddressOrId())?"":mData.getTaxpayerAddressOrId());
        mTaxpayerBankAccountCell.setConten(StringUtils.isNullOrEmpty(mData.getTaxpayerBankAccount())?"":mData.getTaxpayerBankAccount());

        mSalesNameCell.setConten(StringUtils.isNullOrEmpty(mData.getSalesName())?"":mData.getSalesName());
        mSalesTaxpayerNumCell.setConten(StringUtils.isNullOrEmpty(mData.getSalesTaxpayerNum())?"":mData.getSalesTaxpayerNum());
        mSalesTaxpayerAddressCell.setConten(StringUtils.isNullOrEmpty(mData.getSalesTaxpayerAddress())?"":mData.getSalesTaxpayerAddress());
        mSalesTaxpayerBankAccountCell.setConten(StringUtils.isNullOrEmpty(mData.getSalesTaxpayerBankAccount())?"":mData.getSalesTaxpayerBankAccount());

//        mGoodserviceNameCell.setConten(StringUtils.isNullOrEmpty(mData.getInvoiceDetails().getGoodserviceName())?"":mData.getInvoiceDetails().getGoodserviceName());
//        mModelCell.setConten(StringUtils.isNullOrEmpty(mData.getInvoiceDetails().getModel())?"":mData.getInvoiceDetails().getModel());
//        mUnitCell.setConten(StringUtils.isNullOrEmpty(mData.getInvoiceDetails().getUnit())?"":mData.getInvoiceDetails().getUnit());
//        mNumbersCell.setConten(StringUtils.isNullOrEmpty(mData.getInvoiceDetails().getNumber())?"":mData.getInvoiceDetails().getNumber());
//        mPriceCell.setConten(StringUtils.isNullOrEmpty(mData.getInvoiceDetails().getPrice())?"":(mData.getInvoiceDetails().getPrice() + currencyType));
//        mSumsCell.setConten(StringUtils.isNullOrEmpty(mData.getInvoiceDetails().getSum())?"":(mData.getInvoiceDetails().getSum() + currencyType));
//        mTaxRateCell.setConten(StringUtils.isNullOrEmpty(mData.getInvoiceDetails().getTaxRate())?"":mData.getInvoiceDetails().getTaxRate());
//        mTaxCell.setConten(StringUtils.isNullOrEmpty(mData.getInvoiceDetails().getTax())?"":(mData.getInvoiceDetails().getTax() + currencyType));
        mRemarkCell.setConten(StringUtils.isNullOrEmpty(mData.getInvoiceRemarks())?"":mData.getInvoiceRemarks());

        List<InvoiceListGroupBean.InvoiceDetailData> list = mData.getInvoiceDetailData();
        if (list != null){
            mDataSource.addAll(list);
            mAdapter.notifyDataSetChanged();
        }

        //selectTarget = true;
    }

    @Override
    public void initListener() {
        super.initListener();
        mSelector.setSelectorOnselectListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_save:
                saveBtnDidClick();
                break;
        }
    }

    private void saveBtnDidClick(){
        Gson gson = new Gson();
        String jsonS = gson.toJson(mData);
        Map data = Utils.getMapForJson(mJsonData);
        SelectBean inOutBean = mSelector.getItem();

        data.put("inOutStatus",Integer.valueOf(inOutBean.getValue()));
        data.put("creatorId", SpHelper.getInstance(mContext).getUserId());
        data.put("invoiceTypeName",Utils.nullOrEmpty(mData.getInvoiceTypeName()));
        data.put("isBillMark",Utils.nullOrEmpty(mData.getIsBillMark()));
        data.put("invoiceDataCode",Utils.nullOrEmpty(mData.getInvoiceDataCode()));
        data.put("invoiceRemarks",Utils.nullOrEmpty(mData.getInvoiceRemarks()));
        data.put("salesTaxpayerAddress",Utils.nullOrEmpty(mData.getSalesTaxpayerAddress()));
        data.put("taxpayerAddressOrId",Utils.nullOrEmpty(mData.getTaxpayerAddressOrId()));
        data.put("billingTime",Utils.nullOrEmpty(mData.getBillingTime()));
        data.put("tollSignName",Utils.nullOrEmpty(mData.getTollSignName()));
        data.put("totalTaxNum",Utils.nullOrEmpty(mData.getTotalTaxNum()));
        data.put("taxpayerNumber",Utils.nullOrEmpty(mData.getTaxpayerNumber()));
        data.put("salesTaxpayerBankAccount",Utils.nullOrEmpty(mData.getSalesTaxpayerBankAccount()));
        data.put("purchaserName",Utils.nullOrEmpty(mData.getPurchaserName()));
        data.put("checkCode",Utils.nullOrEmpty(mData.getCheckCode()));
        data.put("totalTaxSum",Utils.nullOrEmpty(mData.getTotalTaxSum()));
        data.put("totalAmount",Utils.nullOrEmpty(mData.getTotalAmount()));
        data.put("voidMark",Utils.nullOrEmpty(mData.getVoidMark()));
        data.put("salesName",Utils.nullOrEmpty(mData.getSalesName()));
        data.put("invoiceNumber",Utils.nullOrEmpty(mData.getInvoiceNumber()));
        data.put("tollSign",Utils.nullOrEmpty(mData.getTollSign()));
        data.put("invoiceTypeCode",Utils.nullOrEmpty(mData.getInvoiceTypeCode()));
        data.put("salesTaxpayerNum",Utils.nullOrEmpty(mData.getSalesTaxpayerNum()));
        data.put("taxDiskCode",Utils.nullOrEmpty(mData.getTaxDiskCode()));

        List<Map> invoiceDetailData = new ArrayList<>();
        for (int i = 0;i < mDataSource.size();i++){
            InvoiceListGroupBean.InvoiceDetailData item = mDataSource.get(i);
            Map detail = new HashMap();
            detail.put("goodserviceName",Utils.nullOrEmpty(item.getGoodserviceName()));
            detail.put("number",Utils.nullOrEmpty(item.getNumber()));
            detail.put("taxRate",Utils.nullOrEmpty(item.getTaxRate()));
            detail.put("unit",Utils.nullOrEmpty(item.getUnit()));
            detail.put("price",Utils.nullOrEmpty(item.getPrice()));
            detail.put("zeroTaxRateSign",Utils.nullOrEmpty(item.getZeroTaxRateSign()));
            detail.put("zeroTaxRateSignName",Utils.nullOrEmpty(item.getZeroTaxRateSignName()));
            detail.put("lineNum",Utils.nullOrEmpty(item.getLineNum()));
            detail.put("model",Utils.nullOrEmpty(item.getModel()));
            detail.put("sum",Utils.nullOrEmpty(item.getSum()));
            detail.put("tax",Utils.nullOrEmpty(item.getTax()));
            detail.put("isBillLine",Utils.nullOrEmpty(item.getIsBillLine()));
            invoiceDetailData.add(detail);
        }
        data.put("invoiceDetailData",invoiceDetailData);
        RequestBody body = Utils.map2requestBody(data);
        presenter.addinvoice(body);
        showLoading();
    }

    @Override
    public void onselect(int position, Selector selector) {

        if (mData == null){
            return;
        }
        if (StringUtils.isNullOrEmpty(mData.getId())){
            return;
        }

        Map data = new HashMap();
        data.put("id",mData.getId());
        data.put("invoiceDataCode",mData.getInvoiceDataCode());
        data.put("invoiceNumber",mData.getInvoiceNumber());
        int inOutStatus = position + 1;
        data.put("inOutStatus",inOutStatus);
        RequestBody body = Utils.map2requestBody(data);
        compositeSubscription.add(elseDataManager.modifyInvoiceInOutStatus(body).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net777", String.valueOf(e));
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net777",s);
                        Intent intent = new Intent();
                        intent.putExtra("data","data");
                        setResult(100,intent);
                    }
                })
        );
    }


    /*
    * 获取发票详情
    * */
    @Override
    public void getInvoiceDetailSuccess(InvoiceDetailBean invoiceDetailBean) {
        mData = invoiceDetailBean.getData();
        setUpData();
    }

    @Override
    public void getInvoiceDetailError() {

    }

    @Override
    public void getInvoiceDetailComplete() {

    }

    /*
    * 根据二维码新增发票
    * */
    @Override
    public void addInvoiceSuccess() {

        try {
            Thread.sleep(1000);
        }catch (Exception ignored){
        }
        hideLoading();
        showToast(mContext.getString(R.string.invoice_detail_add_success));
        finish();
    }

    @Override
    public void addInvoiceError() {
        showToast(mContext.getString(R.string.invoice_detail_add_error));
        hideLoading();
    }

    @Override
    public void addInvoiceComplete() {
        hideLoading();
    }
}
