package com.wd.winddots.desktop.list.check.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.check.adapter.CheckQuestionAdapter;
import com.wd.winddots.desktop.list.check.adapter.CheckQuestionImageAdapter;
import com.wd.winddots.desktop.list.check.bean.CheckQuestionBean;
import com.wd.winddots.desktop.list.check.bean.CheckQuestionImageBean;
import com.wd.winddots.utils.OSSUploadHelper;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.LoadingDialog;
import com.wd.winddots.view.dialog.ConfirmDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: CheckQuestionActivity
 * Author: 郑
 * Date: 2021/1/15 2:25 PM
 * Description:
 */
public class CheckQuestionActivity extends FragmentActivity implements BaseQuickAdapter.OnItemClickListener {


    //跳转新增问题页面
    private final static int REQUEST_CODE_ADD_QUESTION = 10086;
    private final static int REQUEST_CODE_PHOTO = 10087;
    private final static int REQUEST_CODE_PHOTO_SELECT = 10088;

    private VolleyUtil mVolleyUtil;


    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.rv_question)
    RecyclerView mQuestionRv;

    @BindView(R.id.rv_images)
    RecyclerView mImageListRv;

    @BindView(R.id.et_remark)
    EditText mRemarkEt;


    private LoadingDialog mLoadingDialog;

    private CheckQuestionAdapter mQuestionAdapter;
    private CheckQuestionImageAdapter mImagePickerAdapter;

    private List<CheckQuestionBean> mQuestionData = new ArrayList<>();
    private List<CheckQuestionImageBean> mImageData = new ArrayList<>();



    //选中的问题和等级
    private CheckQuestionBean mSelectQuestion;
    private TextView mSelectLevel;

    //recordId
    String mRecordId = "";
    private String mCylinderNumber = "";
    private String mGoodsName = "";
    private String mGoodsNo = "";
    private String mSno = "";


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10086) {
                addQuestion();
            } else if (msg.what == 10087) {
                mLoadingDialog.hide();
                Toast.makeText(CheckQuestionActivity.this, getString(R.string.check_question_upload_image_fail), Toast.LENGTH_LONG).show();
                return;
            }
        }
    };


    private int mItemS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_question);
        ButterKnife.bind(this);

        mVolleyUtil = VolleyUtil.getInstance(this);
        mLoadingDialog = LoadingDialog.getInstance(this);

        Intent intent = getIntent();
        mRecordId = intent.getStringExtra("data");
        mGoodsName = intent.getStringExtra("goodsName");
        mGoodsNo = intent.getStringExtra("goodsNo");
        mCylinderNumber = intent.getStringExtra("cylinderNumber");
        mSno = intent.getStringExtra("sno");

        initView();
        getQuestionList();
    }

    private void initView() {

        String title = Utils.nullOrEmpty(mGoodsName) + "  "+ Utils.nullOrEmpty(mCylinderNumber) + "  " + Utils.nullOrEmpty(mSno);
        mTitleTv.setText(title);

        CheckQuestionBean bean7 = new CheckQuestionBean();
        mQuestionData.add(bean7);
        mQuestionAdapter = new CheckQuestionAdapter(R.layout.item_check_question, mQuestionData);
        mQuestionAdapter.setOnItemClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mQuestionRv.setLayoutManager(manager);
        mQuestionRv.setAdapter(mQuestionAdapter);

        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;
        //int heightPixel = outMetrics.heightPixels;
        mItemS = widthPixel / 3;
        mImageData.add(new CheckQuestionImageBean());
        mImagePickerAdapter = new CheckQuestionImageAdapter(R.layout.item_image_pcker, mImageData, mItemS);
        mImagePickerAdapter.setOnItemClickListener(this);
        GridLayoutManager imageManage = new GridLayoutManager(this, 3);
        mImageListRv.setLayoutManager(imageManage);
        mImageListRv.setAdapter(mImagePickerAdapter);
        imagePickerChange();

    }

    @OnClick({R.id.iv_back, R.id.level_a, R.id.level_b, R.id.level_c, R.id.level_d, R.id.ll_add_question})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.level_a:
            case R.id.level_b:
            case R.id.level_c:
            case R.id.level_d:
                onLevleDidClick(v.getId());
                break;
            case R.id.ll_add_question:
                onSaveDidClick();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /*
     * 点击等级
     * */
    private void onLevleDidClick(int viewId) {
        TextView selectLvele = findViewById(viewId);
        selectLvele.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        selectLvele.setTextColor(getResources().getColor(R.color.colorWhite));
        if (mSelectLevel == null) {
        } else {
            if (selectLvele == mSelectLevel) {
                return;
            }
            mSelectLevel.setBackgroundResource(R.drawable.shape_select_border);
            mSelectLevel.setTextColor(getResources().getColor(R.color.color50));
        }
        mSelectLevel = selectLvele;
    }


    /*
     * 获取问题数据
     * */
    private void getQuestionList() {

        String url = Constant.APP_BASE_URL + "fabricQcProblemConfig?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId();
        mVolleyUtil.httpGetRequest(url, response -> {
            List<CheckQuestionBean> data = JSON.parseArray(response, CheckQuestionBean.class);
            data.add(new CheckQuestionBean());
            mQuestionAdapter.setNewData(data);
        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }

    public void imagePickerChange() {
        int rowCount = ((mImagePickerAdapter.getData().size() - 1) / 3) + 1;
        mImageListRv.getLayoutParams().height = rowCount * mItemS;
        mImagePickerAdapter.notifyDataSetChanged();
    }


    /*
     * item 点击事件
     * */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter == mQuestionAdapter) {//点击问题
            CheckQuestionBean questionBean = mQuestionAdapter.getData().get(position);
            if (StringUtils.isNullOrEmpty(questionBean.getTag())) {
                Intent intent = new Intent(this, EditQuestionActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_QUESTION);
            } else {
                if (mSelectQuestion != null) {
                    mSelectQuestion.setSelect(false);
                }

                if (mSelectQuestion == questionBean) {
                    return;
                }

                questionBean.setSelect(true);
                mSelectQuestion = questionBean;
                mQuestionAdapter.notifyDataSetChanged();
            }
        } else {
            CheckQuestionImageBean imageBean = mImagePickerAdapter.getData().get(position);
            if (StringUtils.isNullOrEmpty(imageBean.getImageUrl()) && null == imageBean.getImageUri()) {

                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PHOTO);
                    return;
                } else {
                    openPhotoLibrary();
                }

            }
        }
    }

    /*
     * 打开相册
     * */
    private void openPhotoLibrary() {
        Phoenix.with()
                .theme(PhoenixOption.THEME_DEFAULT)// 主题
                .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(10 - mImagePickerAdapter.getData().size())// 最大选择数量
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
                .start(this, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE_PHOTO_SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_QUESTION) {
            getQuestionList();
        } else if (requestCode == REQUEST_CODE_PHOTO_SELECT) {
            if (data == null) {
                return;
            }

            List<MediaEntity> result = Phoenix.result(data);
            List<Uri> pathList = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                MediaEntity mediaEntity = result.get(i);
                pathList.add(Utils.path2uri(this, mediaEntity.getFinalPath()));
            }

            if (pathList.size() > 0) {
                for (int i = 0; i < pathList.size(); i++) {
                    if (mImagePickerAdapter.getData().size() == 9) {
                        CheckQuestionImageBean m = mImagePickerAdapter.getData().get(8);
                        m.setImageUri(pathList.get(i));
                    } else {
                        CheckQuestionImageBean m = new CheckQuestionImageBean();
                        m.setImageUri(pathList.get(i));
                        mImagePickerAdapter.getData().add(mImagePickerAdapter.getData().size() - 1, m);
                    }
                }
                imagePickerChange();
            }
        }
    }


    /*
     * 点击保存按钮
     * */
    private void onSaveDidClick() {

        if (null == mSelectQuestion) {
            Toast.makeText(this, getString(R.string.check_question_question_empty), Toast.LENGTH_LONG).show();
            return;
        }
        if (null == mSelectLevel) {
            Toast.makeText(this, getString(R.string.check_question_level_empty), Toast.LENGTH_LONG).show();
            return;
        }

        mLoadingDialog.show();
        if (mImagePickerAdapter.getData().size() > 1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int t = 0; t < mImagePickerAdapter.getData().size(); t++) {
                        CheckQuestionImageBean tItem = mImagePickerAdapter.getData().get(t);
                        if (StringUtils.isNullOrEmpty(tItem.getImageUrl()) && (tItem.getImageUri() != null)) {
                            String imagePath = Utils.uri2File(CheckQuestionActivity.this, tItem.getImageUri()).getPath();
                            String imageUrl = OSSUploadHelper.uploadImage(imagePath);
                            if (imageUrl != null) {
                                tItem.setImageUrl(imageUrl);
                            } else {
                                Message msg = new Message();
                                msg.what = 10087;
                                mhandler.sendMessage(msg);
                                return;
                            }
                        }
                    }
                    Message msg = new Message();
                    msg.what = 10086;
                    mhandler.sendMessage(msg);
                }
            }).start();
        } else {
            addQuestion();
        }


    }

    private void addQuestion() {

        Map<String,String> params = new HashMap<String, String>();
        List<String> imageList = new ArrayList<>();
        for (int i = 0;i < mImagePickerAdapter.getData().size();i++){
            CheckQuestionImageBean bean = mImagePickerAdapter.getData().get(i);
            if (!StringUtils.isNullOrEmpty(bean.getImageUrl())){
                imageList.add(bean.getImageUrl());
            }
        }
        params.put("image",JSON.toJSONString(imageList));
        params.put("tag",mSelectQuestion.getTag());
        String remark = mRemarkEt.getText().toString().trim();
        params.put("remark",Utils.nullOrEmpty(remark));
        switch (mSelectLevel.getId()){
            case R.id.level_a:
                params.put("tagATimes","1");
                break;
            case R.id.level_b:
                params.put("tagBTimes","1");
                break;
            case R.id.level_c:
                params.put("tagCTimes","1");
                break;
            case R.id.level_d:
                params.put("tagDTimes","1");
                break;
        }
        params.put("recordId",mRecordId);
        String url = Constant.APP_BASE_URL + "fabricCheckRecordProblem";
        mVolleyUtil.httpPostRequest(url,params,response -> {
            mLoadingDialog.hide();
            finish();
        }, volleyError->{
            mLoadingDialog.hide();
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
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
            handleRejectPermission(this, permissions[0], requestCode);
        }
    }

    public void handleRejectPermission(final Activity context, String permission, int requestCode) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            String content = "";
            switch (requestCode) {
                case REQUEST_CODE_PHOTO:
                    content = "在设置-应用-瓦丁-权限中开启相册访问权限，以正常使用上传图片等功能";
                    break;
            }
            final ConfirmDialog mConfirmDialog = new ConfirmDialog(this, "权限申请",
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
