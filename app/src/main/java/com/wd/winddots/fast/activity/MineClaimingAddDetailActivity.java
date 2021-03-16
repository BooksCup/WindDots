package com.wd.winddots.fast.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Department;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.fast.bean.CostBean;
import com.wd.winddots.fast.bean.DepartmentBean;
import com.wd.winddots.fast.presenter.impl.MineClaimingAddDetailPresenterImpl;
import com.wd.winddots.fast.presenter.view.MineClaimingAddDetailView;
import com.wd.winddots.fast.view.MineClaimingAddDetailFooterView;
import com.wd.winddots.fast.view.MineClaimingAddDetailItem;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.OSSUploadHelper;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.dialog.ConfirmDialog;
import com.wd.winddots.view.selector.SelectBean;
import com.wd.winddots.view.selector.Selector;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.RequestBody;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: MineClaimingAddDetailActivity
 * Author: 郑
 * Date: 2020/5/7 10:28 AM
 * Description: 报销费用明细
 */
public class MineClaimingAddDetailActivity extends CommonActivity<MineClaimingAddDetailView, MineClaimingAddDetailPresenterImpl>
        implements MineClaimingAddDetailView, MineClaimingAddDetailFooterView.ClaimingAddDetailFooterAddIconViewListener,
        MineClaimingAddDetailItem.MineClaimingAddDetailItemActionListener {


    final static int REQUEST_CODE_PHOTO = 1;


    /*
     * data
     * */
    public CompositeSubscription compositeSubscription;
    public ElseDataManager dataManager;
    private List<ApplyDetailBean.ClaimingDetail> mAddressList = new ArrayList<>();
    private List<SelectBean> mCostList = new ArrayList<>();
    private List<ApplyDetailBean.Invoice> uploadList;
    private int uploadIndex;
    private ApplyDetailBean.ClaimingModel mData;
    private List<SelectBean> mDepartmentList;

    /*
    * 相册选择的最大图片数量
    * */
    private int mMaxCount = 0;

    /*
     * 金额
     * */
    @BindView(R.id.activity_claimingadd_detail_header)
    Selector mHeader;
    /*
    * 部门
    * */
    @BindView(R.id.selector_department)
    Selector mDepartmentSlector;
    /*
    * 备注
    * */
    @BindView(R.id.activity_claimingadd_detail_footer)
    MineClaimingAddDetailFooterView mFooter;
    /*
    * 内容
    * */
    @BindView(R.id.activity_claimingadd_detail_content)
    LinearLayout mContent;


    /*
     * item 相关
     * */

    private TextView mSelectDateText;
    private ApplyDetailBean.ClaimingDetail mSelectItem;
    private MineClaimingAddDetailItem mSelectItemView;

    private VolleyUtil mVolleyUtil;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10086) {
                hideLoading();
                goBack();
            }  else if (msg.what == 10087) {
                hideLoading();
                showToast("上传图片失败,请稍后重试");
                return;
            }
        }
    };




    @Override
    public MineClaimingAddDetailPresenterImpl initPresenter() {
        return new MineClaimingAddDetailPresenterImpl();
    }


    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_claimingadd_detail);

        mVolleyUtil = VolleyUtil.getInstance(this);
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if (StringUtils.isNullOrEmpty(data)) {
            setTitleText("新建报销");
        } else {
            setTitleText("编辑报销");
            Gson gson = new Gson();
            ApplyDetailBean.ClaimingModel model = gson.fromJson(data, ApplyDetailBean.ClaimingModel.class);
            mData = model;
        }
        setTitleText("费用明细");
        mHeader.setTitleText("费用类型");
        mDepartmentSlector.setTitleText("部门");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayout saveBth = findViewById(R.id.activity_claimingadd_detail_save);
        saveBth.setOnClickListener(new OnSaveBtnDidClickListener());

    }

    @Override
    public void initListener() {
        super.initListener();
        mFooter.setOnAddIconClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();

        if (mData == null) {
            ApplyDetailBean.ClaimingDetail item = new ApplyDetailBean.ClaimingDetail();
            mAddressList.add(item);
            MineClaimingAddDetailItem cell = new MineClaimingAddDetailItem(mContext);
            cell.setClaimingAddDetailItemActionListener(this);
            cell.setUpData(item);
            mContent.addView(cell);
        } else {
            for (int i = 0; i < mData.getAddresses().size(); i++) {
                ApplyDetailBean.ClaimingDetail item = mData.getAddresses().get(i);
                mAddressList.add(item);
                MineClaimingAddDetailItem cell = new MineClaimingAddDetailItem(mContext);
                cell.setClaimingAddDetailItemActionListener(this);
                cell.setUpData(item);
                mContent.addView(cell);
            }

            if (!StringUtils.isNullOrEmpty(mData.getRemark())) {
                mFooter.setText(mData.getRemark());
            }

        }
        presenter.getCostType(SpHelper.getInstance(mContext).getEnterpriseId());
        getDepertmentList();
    }

    /*
    * 获取部门列表
    * */
    private void getDepertmentList(){

        String url = Constant.APP_BASE_URL_ELSE +  "cloud-user/department/?enterpriseId="+SpHelper.getInstance(mContext).getEnterpriseId()+"&parentId=0";
        mVolleyUtil.httpGetRequest(url, response -> {
            DepartmentBean departmentBean = JSON.parseObject(response,DepartmentBean.class);
            List<SelectBean> selectBeanList = new ArrayList<>();
            int selectIndex  = 0;
            if (0 == departmentBean.getCode()){
                for (int i = 0; i < departmentBean.getData().size(); i++) {
                    Department bean1 = departmentBean.getData().get(i);
                    SelectBean selectBean = new SelectBean();
                    selectBean.setName(bean1.getName());
                    selectBean.setValue(bean1.getId());
                    selectBeanList.add(selectBean);
                    if (mData != null && null != mData.getDeptName() && mData.getDeptName().equals(bean1.getName())){
                        selectIndex = i;
                    }
                }
            }
            mDepartmentSlector.setSelectList(selectBeanList);
            mDepartmentList = selectBeanList;
            mDepartmentSlector.setDefaultPosition(selectIndex);
        }, volleyError -> {

        });
    }


    @Override
    public void getCostTypsSuccess(CostBean bean) {
        mHeader.setSelectList(bean.getData());
        if (mData != null) {
            for (int i = 0; i < bean.getData().size(); i++) {
                SelectBean bean1 = bean.getData().get(i);
                if (bean1.getName().equals(mData.getCostName())) {
                    mHeader.setDefaultPosition(i);
                    break;
                }
            }
        }
        mCostList = bean.getData();
    }

    @Override
    public void getCostTypsError() {
        showToast("获取费用类型失败");
    }

    @Override
    public void getCostTypsCompleted() {
    }


    /*
     * 添加条目
     * */
    @Override
    public void addIconDidClick() {
        ApplyDetailBean.ClaimingDetail item = new ApplyDetailBean.ClaimingDetail();
        mAddressList.add(item);
        MineClaimingAddDetailItem cell = new MineClaimingAddDetailItem(mContext);
        cell.setClaimingAddDetailItemActionListener(this);
        cell.setUpData(item);
        mContent.addView(cell);
    }

    /*
     * 删除条目
     * */
    @Override
    public void deleIconDidClick(ApplyDetailBean.ClaimingDetail item) {

        if (mAddressList.size() == 1) {
            return;
        }
        int index = mAddressList.indexOf(item);
        mAddressList.remove(item);
        mContent.removeViewAt(index);

    }

    /*
     * 日期选择
     * */
    @Override
    public void datePickerItemDidClick(TextView textView, ApplyDetailBean.ClaimingDetail item) {


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mSelectDateText = textView;
        mSelectItem = item;
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
                mSelectDateText.setText(date);
                mSelectItem.setTime(date);
            }


        }, year, month, day);//2013:初始年份，2：初始月份-1 ，1：初始日期

        dp.show();
    }

    /*
     * 选择图片
     * */
    @Override
    public void imagePickerDidClick(MineClaimingAddDetailItem item) {

        int maxCount = 0;
        if (item.getItemData().getInvoices().size() < 9) {
            maxCount = 9 - item.getItemData().getInvoices().size() + 1;
        } else if (item.getItemData().getInvoices().size() == 9) {
            ApplyDetailBean.Invoice m = mSelectItemView.getItemData().getInvoices().get(8);
            if (m.getUri() != null) {
                return;
            } else {
                maxCount = 1;
            }
        }
        mMaxCount = maxCount;
        mSelectItemView = item;

//        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        requestPermissions(this, permissions,REQUEST_CODE_PHOTO);


        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PHOTO);
            return;
        }else {
            openPhotoLibrary();
        }



    }
    
    private void openPhotoLibrary(){
        Phoenix.with()
                .theme(PhoenixOption.THEME_DEFAULT)// 主题
                .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(mMaxCount)// 最大选择数量
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
                .start(MineClaimingAddDetailActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 1000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }

        if (requestCode == 1000) {
//            List<Uri> pathList = Matisse.obtainResult(data);
//            if (pathList != null && pathList.size() > 0) {
//                for (int i = 0; i < pathList.size(); i++) {
//                    if (mSelectItemView.getItemData().getInvoices().size() == 9) {
//                        ApplyDetailBean.Invoice m = mSelectItemView.getItemData().getInvoices().get(8);
//                        m.setUri(pathList.get(i));
//                    } else {
//                        ApplyDetailBean.Invoice m = new ApplyDetailBean.Invoice();
//                        m.setUri(pathList.get(i));
//                        mSelectItemView.getItemData().getInvoices().add(mSelectItemView.getItemData().getInvoices().size() - 1, m);
//                    }
//                }
//                mSelectItemView.imagePickerChange();
//            }

            List<MediaEntity> result = Phoenix.result(data);
            List<Uri> pathList = new ArrayList<>();
            for (int i = 0;i < result.size();i++){
                MediaEntity mediaEntity = result.get(i);
                pathList.add(Utils.path2uri(mContext,mediaEntity.getFinalPath()));
            }

            if (pathList.size() > 0) {
                for (int i = 0; i < pathList.size(); i++) {
                    if (mSelectItemView.getItemData().getInvoices().size() == 9) {
                        ApplyDetailBean.Invoice m = mSelectItemView.getItemData().getInvoices().get(8);
                        m.setUri(pathList.get(i));
                    } else {
                        ApplyDetailBean.Invoice m = new ApplyDetailBean.Invoice();
                        m.setUri(pathList.get(i));
                        mSelectItemView.getItemData().getInvoices().add(mSelectItemView.getItemData().getInvoices().size() - 1, m);
                    }
                }
                mSelectItemView.imagePickerChange();
            }
        }
    }


    private void uploadImage(Uri uri) {
        File file = uri2File(uri);
        RequestBody body = Utils.uri2requestBody(mContext, uri);
        presenter.upLoadImage(body);
    }


    @Override
    public void uploadImageSuccess(UploadImageBean bean) {
        ApplyDetailBean.Invoice item = uploadList.get(uploadIndex);
        item.setInvoiceImage("http:" + bean.getList().get(0));
        item.setUri(null);
        uploadIndex += 1;
        if (uploadIndex != uploadList.size()) {
            uploadImage(uploadList.get(uploadIndex).getUri());
        } else {
            hideLoading();
            goBack();
        }
    }
    @Override
    public void uploadImageError() {
        hideLoading();
        showToast("上传图片失败,请稍后重试");
    }

    @Override
    public void uploadImageCompleted() {
    }


    /*
     * 点击保存按钮
     * */
    private class OnSaveBtnDidClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final List<ApplyDetailBean.Invoice> uploadList1 = new ArrayList<>();
            for (int i = 0; i < mAddressList.size(); i++) {
                final ApplyDetailBean.ClaimingDetail item = mAddressList.get(i);
                if (StringUtils.isNullOrEmpty(item.getProjectTitle())) {
                    showToast("请先完善费用描述");
                    return;
                }

                if (StringUtils.isNullOrEmpty(item.getAmount())) {
                    showToast("请先输入金额");
                    return;
                }

                if (StringUtils.isNullOrEmpty(item.getTime())) {
                    showToast("请先选择日期");
                    return;
                }

                if (Const.INVOICE_TYPE_ALREADY.equals(item.getInvoice()) && item.getInvoices().size() < 2) {
                    showToast("请先上传发票");
                    return;
                }

                if (Const.INVOICE_TYPE_NO_ALREADY.equals(item.getInvoice()) && StringUtils.isNullOrEmpty(item.getReachTime())) {
                    showToast("请先选择到票日期");
                    return;
                }

                showLoading();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (item.getInvoices() != null && item.getInvoices().size() > 1) {
                            for (int t = 0; t < item.getInvoices().size(); t++) {
                                ApplyDetailBean.Invoice tItem = item.getInvoices().get(t);
                                if (StringUtils.isNullOrEmpty(tItem.getInvoiceImage()) && (tItem.getUri() != null)) {
                                    uploadList1.add(tItem);
                                    String imagePath = Utils.uri2File(mContext,tItem.getUri()).getPath();
                                    String imageUrl = OSSUploadHelper.uploadImage(imagePath);
                                    if (imageUrl != null){
                                        tItem.setInvoiceImage(imageUrl);
                                    }else {
                                        Message msg = new Message();
                                        msg.what = 10087;
                                        mhandler.sendMessage(msg);
                                        return;
                                    }
                                }
                            }
                        }

                        Message msg = new Message();
                        msg.what = 10086;
                        mhandler.sendMessage(msg);
//                        if (mUploadLength == imageUrlList.size()) {
//                            Message msg = new Message();
//                            msg.what = 10086;
//                            mhandler.sendMessage(msg);
//                        }
                    }
                }).start();

            }
//            hideLoading();
//            goBack();
//            if (uploadList1.size() > 0) {
//                uploadList = uploadList1;
//                uploadIndex = 0;
//                uploadImage(uploadList.get(0).getUri());
//                showLoading();
//            } else {
//                goBack();
//            }
        }
    }


    private void goBack() {
        SelectBean costBean = mCostList.get(mHeader.getPosition());
        String remark = mFooter.getText();
        ApplyDetailBean.ClaimingModel model = new ApplyDetailBean.ClaimingModel();
        for (int i = 0;i < mAddressList.size();i++){
            ApplyDetailBean.ClaimingDetail claimingDetail = mAddressList.get(i);
            for (int m = 0;m < claimingDetail.getInvoices().size();m++){
               ApplyDetailBean.Invoice invoice = claimingDetail.getInvoices().get(m);
               invoice.setUri(null);
            }
        }
        model.setRemark(remark);
        model.setCostName(costBean.getName());
        model.setAddresses(mAddressList);
        model.setDeptId(mDepartmentList.get(mDepartmentSlector.getPosition()).getValue());
        Gson gson = new Gson();
        String data = gson.toJson(model);
        Intent intent = new Intent();
        intent.putExtra("data", data);
        setResult(250, intent);
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(me);
    }


    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
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
                    case REQUEST_CODE_PHOTO:
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
                case REQUEST_CODE_PHOTO:
                    // 同意定位权限,进入地图选择器
                    //startActivity(MeAttendanceActivity.class);
                    openPhotoLibrary();
                    break;
            }
        } else {
            // 拒绝授权做的处理，弹出弹框提示用户授权
            handleRejectPermission(MineClaimingAddDetailActivity.this, permissions[0], requestCode);
        }
    }

    public void handleRejectPermission(final Activity context, String permission, int requestCode) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            String content = "";
            switch (requestCode) {
                case REQUEST_CODE_PHOTO:
                    content = "在设置-应用-瓦丁-权限中开启相册访问权限，以正常使用上传发票等功能";
                    break;
            }
            final ConfirmDialog mConfirmDialog = new ConfirmDialog(MineClaimingAddDetailActivity.this, "权限申请",
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
