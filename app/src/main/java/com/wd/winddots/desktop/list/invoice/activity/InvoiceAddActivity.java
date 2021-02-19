package com.wd.winddots.desktop.list.invoice.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.desktop.list.invoice.presenter.impl.InvoiceAddPresenterImpl;
import com.wd.winddots.desktop.list.invoice.presenter.view.InvoiceAddView;
import com.wd.winddots.fast.activity.RelationEnterprise;
import com.wd.winddots.fast.adapter.MineClaimingImagePickerAdpater;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.fast.bean.RelationEnterpriseBean;
import com.wd.winddots.utils.OSSUploadHelper;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.EditCell;
import com.wd.winddots.view.TitleTextView;
import com.wd.winddots.view.dialog.ConfirmDialog;
import com.wd.winddots.view.selector.SelectBean;
import com.wd.winddots.view.selector.Selector;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.RequestBody;

/**
 * FileName: InvoiceAddActivity
 * Author: 郑
 * Date: 2020/7/7 3:05 PM
 * Description: 新建发票
 */
public class InvoiceAddActivity extends CommonActivity<InvoiceAddView,InvoiceAddPresenterImpl>
        implements InvoiceAddView,
        BaseQuickAdapter.OnItemClickListener,
        Selector.SelectorOnselectListener,
        MineClaimingImagePickerAdpater.MineClaimingImagePickerAdpaterDeleteListener{

    private final static int REQUEST_CODE_ENTERPRISE = 1; //选择往来企业
    private final static int REQUEST_CODE_PERMISSIONS_PHOTO = 2; //相册权限
    private final static int REQUEST_CODE_PHOTO_SELECT = 3; //选择相册

    private Selector mInOutSelector;
    private Selector mTypeSelector;
    private Selector mCostTypeSelector;
    private Selector mCurrencySelector;

    private EditCell mInviceNumberCell;
    private EditCell mAmountCell;
    private EditCell mTaxAmountCell;
    private TitleTextView mSalesNameCell;
    private TitleTextView mPurchaserNameCell;
    private EditCell mRemarkCell;
    private RecyclerView mRecyclerView;
    private View mLine;//分割线
    private List<ApplyDetailBean.Invoice> imageList = new ArrayList<>();
    private MineClaimingImagePickerAdpater mImageListAdapter;


    private TitleTextView selectCell;

    private TitleTextView mOpenDateCell;
    private TitleTextView mReceiveDateCell;

    private List<SelectBean> inOutBeans = new ArrayList<>();
    private List<SelectBean> typeBeans = new ArrayList<>();
    private List<SelectBean> serviceBeans = new ArrayList<>();

    private RelationEnterpriseBean.Enterprise mSaleName;
    private RelationEnterpriseBean.Enterprise mPurchaserName;
    private String mOpenTime;
    private String mReciveTime;
    private Map mDataBody;
    private List<Map> invoices;
    private int mUploadLength;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
             if (msg.what == 10087) {
                hideLoading();
                showToast("上传图片失败,请稍后重试");
                return;
            }
        }
    };


    //增值税发票  普通发票

    @Override
    public InvoiceAddPresenterImpl initPresenter() {
        return new InvoiceAddPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_invoice_add);

        setTitleText(mContext.getString(R.string.invoice_add_title));

        mInOutSelector  = findViewById(R.id.selector_in_out_status);
        mTypeSelector  = findViewById(R.id.selector_type);
        mCostTypeSelector  = findViewById(R.id.selector_const_type);
        mCurrencySelector  = findViewById(R.id.selector_currency);
        mInviceNumberCell  = findViewById(R.id.et_invoice_number);
        mAmountCell  = findViewById(R.id.et_amount);
        mTaxAmountCell  = findViewById(R.id.et_tax_amount);
        mSalesNameCell  = findViewById(R.id.et_sales_name);
        mPurchaserNameCell  = findViewById(R.id.et_purchaser_name);
        mOpenDateCell = findViewById(R.id.view_open_date);
        mReceiveDateCell = findViewById(R.id.view_receive_date);
        mRemarkCell = findViewById(R.id.et_remarks);
        mRecyclerView = findViewById(R.id.imagelist);
        mLine = findViewById(R.id.line);

//        mInOutSelector.selectorToLeft(true);
//        mTypeSelector.selectorToLeft(true);
//        mCostTypeSelector.selectorToLeft(true);
//        mCurrencySelector.selectorToLeft(true);

        mInOutSelector.setTitleText(mContext.getString(R.string.invoice_add_in_out_status));
        mTypeSelector.setTitleText(mContext.getString(R.string.invoice_add_type));
        mCostTypeSelector.setTitleText(mContext.getString(R.string.invoice_add_cost_type));
        mCurrencySelector.setTitleText(mContext.getString(R.string.invoice_add_cueerncy));
        mInviceNumberCell.setTitle(mContext.getString(R.string.invoice_add_invoice_number));
        mAmountCell.setTitle(mContext.getString(R.string.invoice_add_amount));
        mTaxAmountCell.setTitle(mContext.getString(R.string.invoice_add_tax_amount));
        mSalesNameCell.setTitle(mContext.getString(R.string.invoice_add_sale_name));
        mPurchaserNameCell.setTitle(mContext.getString(R.string.invoice_add_pay_name));
        mOpenDateCell.setTitle(mContext.getString(R.string.invoice_add_invoice_date));
        mReceiveDateCell.setTitle(mContext.getString(R.string.invoice_add_recevie_date));
        mRemarkCell.setTitle(mContext.getString(R.string.invoice_add_remarks));

        mAmountCell.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mTaxAmountCell.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        mInviceNumberCell.setHint(mContext.getString(R.string.invoice_add_invoice_number_hint));
        mAmountCell.setHint(mContext.getString(R.string.invoice_add_amount_hint));
        mTaxAmountCell.setHint(mContext.getString(R.string.invoice_add_tax_amount_hint));
        mRemarkCell.setHint(mContext.getString(R.string.invoice_add_remarks_hint));
        mSalesNameCell.setContent(mContext.getString(R.string.invoice_add_sale_name_hint));
        mPurchaserNameCell.setContent(mContext.getString(R.string.invoice_add_pay_name_hint));


        mOpenDateCell.setContent(mContext.getString(R.string.invoice_add_invoice_date_hint));
        mReceiveDateCell.setContent(mContext.getString(R.string.invoice_add_recevie_date_hint));

        int itemS = getScreenWidth() / 3;
        imageList.add(new ApplyDetailBean.Invoice());
        mImageListAdapter = new MineClaimingImagePickerAdpater(R.layout.item_image_pcker, imageList, itemS);
        mImageListAdapter.setOnItemClickListener(this);
        mImageListAdapter.setMineClaimingImagePickerAdpaterDeleteListener(this);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mImageListAdapter);
        int rowCount = ((imageList.size() - 1) / 3) + 1;
        mRecyclerView.getLayoutParams().height = rowCount * itemS;

        LinearLayout saveBtn = findViewById(R.id.ll_save);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        SelectBean inOutBean1 = new SelectBean("进项","1");
        SelectBean inOutBean2 = new SelectBean("销项","2");
        inOutBeans.add(inOutBean1);
        inOutBeans.add(inOutBean2);
        mInOutSelector.setSelectList(inOutBeans);

        SelectBean typeBean1 = new SelectBean("普通发票","2");
        SelectBean typeBean2 = new SelectBean("增值税发票","1");
        typeBeans.add(typeBean1);
        typeBeans.add(typeBean2);
        mTypeSelector.setSelectList(typeBeans);

        SelectBean service1 = new SelectBean("服装","1");
        SelectBean service2 = new SelectBean("面辅料","2");
        SelectBean service3 = new SelectBean("加工费","3");
        SelectBean service4 = new SelectBean("其他","4");
        serviceBeans.add(service1);
        serviceBeans.add(service2);
        serviceBeans.add(service3);
        serviceBeans.add(service4);
        mCostTypeSelector.setSelectList(serviceBeans);




        String enterpriseId = SpHelper.getInstance(mContext).getEnterpriseId();

        presenter.getCurrencyList(enterpriseId);
        //presenter.getCostTypeList(enterpriseId);

    }

    @Override
    public void initListener() {
        super.initListener();
        mTypeSelector.setSelectorOnselectListener(this);
        mSalesNameCell.setOnClickListener(this);
        mPurchaserNameCell.setOnClickListener(this);
        mOpenDateCell.setOnClickListener(this);
        mReceiveDateCell.setOnClickListener(this);
    }

    @Override
    public void onselect(int position, Selector selector) {
        if (position == 0){
            mTaxAmountCell.setVisibility(View.GONE);
            mLine.setVisibility(View.GONE);
        }else {
            mTaxAmountCell.setVisibility(View.VISIBLE);
            mLine.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.et_sales_name:
            case R.id.et_purchaser_name:
                onEnterpriseSelect(v);
                break;
            case R.id.view_open_date:
            case R.id.view_receive_date:
                onDateSelect(v);
                break;
            case R.id.ll_save:
                onSave();
                break;
        }
    }

    /*
    * 选择开票方和付款方
    * */
    private void onEnterpriseSelect(View v){
        selectCell = (TitleTextView) v;
        Intent intent = new Intent(mContext, RelationEnterprise.class);
        startActivityForResult(intent, REQUEST_CODE_ENTERPRISE);
    }

    /*
    * 选择日期
    * */
    private void onDateSelect(final View v){
        final TitleTextView titleTextView = (TitleTextView) v;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                long maxDate = datePicker.getMaxDate();//日历最大能设置的时间的毫秒值
                int year = datePicker.getYear();//年
                int month = datePicker.getMonth() + 1;//月-1
                int day = datePicker.getDayOfMonth();//日*
                //iyear:年，monthOfYear:月-1，dayOfMonth:日
                String monthS;
                String dayS;
                if (month < 10) {
                    monthS = "0" + month;
                } else {
                    monthS = "" + month;
                }

                if (day < 10) {
                    dayS = "0" + day;
                } else {
                    dayS = "" + day;
                }
                String date = year + "-" + monthS + '-' + dayS;
                titleTextView.setContent(date);
                //mSelectItem.setTime(date);
                if (v.getId() == R.id.view_open_date){
                    mOpenTime = date;
                }else {
                    mReciveTime = date;
                }
            }


        }, year, month, day);//2013:初始年份，2：初始月份-1 ，1：初始日期
        dp.show();
    }

    private void onSave(){
        SelectBean inOutBean = mInOutSelector.getItem();
        SelectBean typeBean = mTypeSelector.getItem();
        SelectBean currencyBean = mCurrencySelector.getItem();
        SelectBean seviceBean = mCostTypeSelector.getItem();
        String invoiceNumber = mInviceNumberCell.getText();
        String amount = mAmountCell.getText();
        String taxAmount = mTaxAmountCell.getText();
        String invoiceRemarks = mRemarkCell.getText();
        if (StringUtils.isNullOrEmpty(invoiceRemarks)){
            invoiceRemarks = "";
        }

        if (StringUtils.isNullOrEmpty(invoiceNumber)){
            showToast(mContext.getString(R.string.invoice_add_invoice_number_toast));
            return;
        }
        if (StringUtils.isNullOrEmpty(amount)){
            showToast(mContext.getString(R.string.invoice_add_amount_toast));
            return;
        }
        if (typeBean.getValue().equals("1")){//增值税发票
            if (StringUtils.isNullOrEmpty(taxAmount)){
                showToast(mContext.getString(R.string.invoice_add_tax_amount_toast));
                return;
            }
        }


        if (mSaleName == null){
            showToast(mContext.getString(R.string.invoice_add_sale_name_toast));
            return;
        }
        if (mPurchaserName == null){
            showToast(mContext.getString(R.string.invoice_add_pay_name_toast));
            return;
        }
        if (StringUtils.isNullOrEmpty(mOpenTime)){
            showToast(mContext.getString(R.string.invoice_add_invoice_date_toast));
            return;
        }
        if (StringUtils.isNullOrEmpty(mReciveTime)){
            showToast(mContext.getString(R.string.invoice_add_recevie_date_toast));
            return;
        }

        Map data = new HashMap();
        data.put("inOutStatus",Integer.valueOf(inOutBean.getValue()));
        data.put("invoiceTypeCode",Integer.valueOf(typeBean.getValue()));
        int currencyCode = 9;
        if (currencyBean.getName().equals(mContext.getString(R.string.currency_dollar))){
            currencyCode = 1;
        }else if (currencyBean.getName().equals(mContext.getString(R.string.currency_euro))){
            currencyCode = 2;
        }else if (currencyBean.getName().equals(mContext.getString(R.string.currency_pound))){
            currencyCode = 3;
        }else if (currencyBean.getName().equals(mContext.getString(R.string.currency_hkd))){
            currencyCode = 4;
        }else if (currencyBean.getName().equals(mContext.getString(R.string.currency_cad))){
            currencyCode = 5;
        }else if (currencyBean.getName().equals(mContext.getString(R.string.currency_yen))){
            currencyCode = 6;
        }else if (currencyBean.getName().equals(mContext.getString(R.string.currency_ntc))){
            currencyCode = 7;
        }else if (currencyBean.getName().equals(mContext.getString(R.string.currency_vnd))){
            currencyCode = 8;
        }else if (currencyBean.getName().equals(mContext.getString(R.string.currency_rmb))){
            currencyCode = 9;
        }
        data.put("currency",currencyCode);
        data.put("invoiceNumber",invoiceNumber);
        data.put("totalAmount",amount);
        if (typeBean.getValue().equals("1")){//增值税发票
            data.put("totalTaxNum",taxAmount);
        }
        data.put("salesName",mSaleName.getName());
        data.put("openBillEnterpriseId",mSaleName.getId());
        data.put("exchangeEnterpriseId",mSaleName.getId());
        data.put("exchangeEnterpriseName",mSaleName.getName());
        data.put("purchaserName",mPurchaserName.getName());
        data.put("payEnterpriseId",mPurchaserName.getId());
        data.put("billingTime",mOpenTime);
        data.put("receiveTime",mReciveTime);

        Float totalTaxSum;
        Float amountFloat = Float.parseFloat(amount);
        if (typeBean.getValue().equals("2")){
            totalTaxSum = amountFloat;
        }else {
            totalTaxSum = amountFloat + Float.parseFloat(taxAmount);
        }
        data.put("totalTaxSum",totalTaxSum);
        data.put("relateStatus",0);
        data.put("invoiceRemarks",invoiceRemarks);
        data.put("serviceType",seviceBean.getValue());
        data.put("creatorId",SpHelper.getInstance(mContext).getUserId());

        showLoading();
        if (imageList.size() == 1){
            Gson gson = new Gson();
            String jsonS = gson.toJson(data);
            Log.e("net666",jsonS);
            RequestBody body = Utils.map2requestBody(data);
            presenter.addinvoice(body);
        }else {
            mDataBody = data;
            invoices = new ArrayList<>();
            if (imageList.size() == 9){
                if (imageList.get(8).getUri() == null){
                    mUploadLength = 8;
                }else {
                    mUploadLength = 9;
                }
            }else {
                mUploadLength = imageList.size() -1;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < imageList.size(); i++) {
                        ApplyDetailBean.Invoice invoice = imageList.get(i);
                        if (invoice.getUri() != null) {
                            String imagePath = Utils.uri2File(mContext, invoice.getUri()).getPath();
                            String imageUrl = OSSUploadHelper.uploadImage(imagePath);
                            if (imageUrl != null) {
                                Map invoiceMap = new HashMap();
                                invoiceMap.put("invoiceImage",imageUrl);
                                invoices.add(invoiceMap);
                            }else {
                                Message msg = new Message();
                                msg.what = 10087;
                                msg.arg1 = i;
                                mhandler.sendMessage(msg);
                                return;
                            }
                        }
                    }
                    mDataBody.put("invoices",invoices);
                    RequestBody body = Utils.map2requestBody(mDataBody);
                    presenter.addinvoice(body);
                }
            }).start();





//            for (int i = 0;i < imageList.size();i++){
//                ApplyDetailBean.Invoice invoice = imageList.get(i);
//                if (invoice.getUri() != null){
//                    RequestBody imageBody = Utils.uri2requestBody(mContext,invoice.getUri());
//                    presenter.upLoadImage(imageBody,i);
//                }
//
//            }
        }

    }



    /*
    * 获取币种
    * */
    @Override
    public void getCurrencyListSuccess(List<SelectBean> list) {
        mCurrencySelector.setSelectList(list);
    }
    @Override
    public void getCurrencyListerror() {
    }
    @Override
    public void getCurrencyListComplete() {
    }

    /*
    * 获取费用类型
    * */
    @Override
    public void getCostTypeListSuccess(List<SelectBean> list) {
        mCostTypeSelector.setSelectList(list);
    }
    @Override
    public void getCostTypeListError() {
    }
    @Override
    public void getCostTypeListComplete() {
    }


    /*
    * 上传图片
    * */
    @Override
    public void uploadImageSuccess(UploadImageBean bean,int index) {
        //imageList.get(index).setInvoiceImage(bean.getData());
        Map invoice = new HashMap();
        invoice.put("invoiceImage","http:" + bean.getList().get(0));
        invoices.add(invoice);
        if (mUploadLength-1 == index){
            mDataBody.put("invoices",invoices);
            RequestBody body = Utils.map2requestBody(mDataBody);
            presenter.addinvoice(body);
        }
    }

    @Override
    public void uploadImageError() {
        hideLoading();
        showToast(mContext.getString(R.string.invoice_detail_add_error));
    }

    @Override
    public void uploadImageCompleted() {
    }


    /*
    * 新增发票
    * */
    @Override
    public void addInvoiceSuccess() {
        try {
            Thread.sleep(1000);
        }catch (Exception ignored){
        }
        hideLoading();
        showToast(mContext.getString(R.string.invoice_detail_add_success));
        Intent intent = new Intent();
        intent.putExtra("data","date");
        setResult(100,intent);
        finish();
    }
    @Override
    public void addInvoiceError() {
        showToast(mContext.getString(R.string.invoice_detail_add_error));
    }
    @Override
    public void addInvoiceComplete() {
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ApplyDetailBean.Invoice item = imageList.get(position);
        if (item.getUri() == null && StringUtils.isNullOrEmpty(item.getInvoiceImage())) {
            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions( this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS_PHOTO);
                return;
            }else {
                openPhotoLibrary();
            }
        }
    }
    private void openPhotoLibrary(){
        int maxCount = 9 - imageList.size() + 1;
//        Matisse.from(this).
//                choose(MimeType.ofImage()).
//                showSingleMediaType(true).
//                capture(false).
//                countable(true).
//                maxSelectable(maxCount).
//                restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT).
//                thumbnailScale(0.8f).
//                theme(R.style.Matisse_Dracula).
//                imageEngine(new WdGlideEngine()).
//                forResult(REQUEST_CODE_PHOTO_SELECT);

        Phoenix.with()
                .theme(PhoenixOption.THEME_DEFAULT)// 主题
                .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(maxCount)// 最大选择数量
                .minPickNumber(0)// 最小选择数量
                .spanCount(4)// 每行显示个数
                .enablePreview(true)// 是否开启预览
                .enableCamera(true)// 是否开启拍照
                .enableAnimation(true)// 选择界面图片点击效果
                .enableCompress(true)// 是否开启压缩
                .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                .compressVideoFilterSize(2018)//多少kb以下的视频不压缩
                .thumbnailHeight(160)// 选择界面图片高度
                .thumbnailWidth(160)// 选择界面图片宽度
                .enableClickSound(false)// 是否开启点击声音
                .videoFilterTime(0)//显示多少秒以内的视频
                .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
                .start(InvoiceAddActivity.this, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE_PHOTO_SELECT);
    }

    public void imagePickerChange() {
        int rowCount = ((imageList.size() - 1) / 3) + 1;
        mRecyclerView.getLayoutParams().height = rowCount * getScreenWidth() / 3;
        mImageListAdapter.notifyDataSetChanged();
    }



    @Override
    public void deleIconDidClick(ApplyDetailBean.Invoice item) {
        if (imageList.size() == 1) {
            ApplyDetailBean.Invoice newItem = new ApplyDetailBean.Invoice();
            imageList.add(newItem);
            return;
        } else if (imageList.size() == 9) {
            ApplyDetailBean.Invoice m = imageList.get(8);
            if (m.getUri() == null && m.getInvoiceImage() == null) {
            } else {
                ApplyDetailBean.Invoice newItem = new ApplyDetailBean.Invoice();
                imageList.add(newItem);
            }
        }
        imageList.remove(item);
        imagePickerChange();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }

        if (requestCode == REQUEST_CODE_ENTERPRISE){
            String json = data.getStringExtra("data");
            Gson gson = new Gson();
            RelationEnterpriseBean.Enterprise enterprise = gson.fromJson(json, RelationEnterpriseBean.Enterprise.class);
            selectCell.setContent(enterprise.getName());
            if (selectCell.getId() == R.id.et_sales_name){
                mSaleName = enterprise;
            }else {
                mPurchaserName = enterprise;
            }
        }else if (requestCode == REQUEST_CODE_PHOTO_SELECT) {
            List<MediaEntity> result = Phoenix.result(data);
            List<Uri> pathList = new ArrayList<>();
            for (int i = 0;i < result.size();i++){
                MediaEntity mediaEntity = result.get(i);
                pathList.add(Utils.path2uri(mContext,mediaEntity.getFinalPath()));
            }
            if (pathList != null && pathList.size() > 0) {
                for (int i = 0; i < pathList.size(); i++) {
                    if (imageList.size() == 9) {
                        ApplyDetailBean.Invoice m = imageList.get(8);
                        m.setUri(pathList.get(i));
                    } else {
                        ApplyDetailBean.Invoice m = new ApplyDetailBean.Invoice();
                        m.setUri(pathList.get(i));
                        imageList.add(imageList.size() - 1, m);
                    }
                }
                imagePickerChange();
            }
        }
    }

    /**
     * 动态权限
     */
    public void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //Android 6.0开始的动态权限，这里进行版本判断
            ArrayList<String> mPermissionList = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(activity, permissions[i])
                        != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            if (mPermissionList.isEmpty()) {
                // 非初次进入App且已授权
                switch (requestCode) {
                    case REQUEST_CODE_PERMISSIONS_PHOTO:
                        //startActivity(MeAttendanceActivity.class);
                        openPhotoLibrary();
                        break;
                }

            } else {
                // 请求权限方法
                String[] requestPermissions = mPermissionList.toArray(new String[mPermissionList.size()]);
                // 这个触发下面onRequestPermissionsResult这个回调
                ActivityCompat.requestPermissions(activity, requestPermissions, requestCode);
            }
        }
    }

    /**
     * requestPermissions的回调
     * 一个或多个权限请求结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        // 判断是否拒绝  拒绝后要怎么处理 以及取消再次提示的处理
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                break;
            }
        }
        if (hasAllGranted) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSIONS_PHOTO:
                    //startActivity(MeAttendanceActivity.class);
                    openPhotoLibrary();
                    break;
            }
        } else {
            // 拒绝授权做的处理，弹出弹框提示用户授权
            handleRejectPermission(InvoiceAddActivity.this, permissions[0], requestCode);
        }
    }

    public void handleRejectPermission(final Activity context, String permission, int requestCode) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            String content = "";
            switch (requestCode) {
                case REQUEST_CODE_PERMISSIONS_PHOTO:
                    content = "在设置-应用-瓦丁-权限中开启相册访问权限，以正常使用上传发票等功能";
                    break;
            }
            final ConfirmDialog mConfirmDialog = new ConfirmDialog(mContext, "权限申请",
                    content,
                    "确定", "取消");
            mConfirmDialog.setOnDialogClickListener(new ConfirmDialog.OnDialogClickListener() {
                @Override
                public void onOkClick() {
                    mConfirmDialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
                    intent.setData(uri);
                    context.startActivity(intent);
                }

                @Override
                public void onCancelClick() {
                    mConfirmDialog.dismiss();
                }
            });
            // 点击空白处消失
            mConfirmDialog.setCancelable(false);
            mConfirmDialog.show();
        }
    }


}
