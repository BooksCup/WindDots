package com.wd.winddots.desktop.list.disk;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.OSSUploadHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.dialog.ConfirmDialog;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: DiskListActivity
 * Author: 郑
 * Date: 2021/1/6 2:52 PM
 * Description: 网盘
 */
public class DiskListActivity extends CommonActivity {

    private CompositeSubscription compositeSubscription;
    private ElseDataManager dataManager;

    private static final int REQUEST_CODE_STORAGE = 10010;


    final String DOC = "application/msword";
    final String XLS = "application/vnd.ms-excel";
    final String PPT = "application/vnd.ms-powerpoint";
    final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    final String XLSX = "application/x-excel";
    final String XLS1 = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    final String PDF = "application/pdf";



    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_disk_list);
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();

        TextView textView = findViewById(R.id.tv_select);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStorage();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。

            String path = Utils.diskUri2FilePath(mContext,uri);
            File file = new File(path);

            RequestBody requestBody3 = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody multipartBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("picture", file.getName(), requestBody3)
                    .build();


            compositeSubscription.add(dataManager.uploadBigOSSImage(multipartBody).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            Log.e("net666",s);
                            Gson gson = new Gson();
                            //UploadImageBean bean = gson.fromJson(s, UploadImageBean.class);
//                            if (bean.getList() != null && bean.getList().size() > 0){
//                                uploadImageSuccess(bean);
//                            }else {
//                                uploadImageError();
//                            }

                        }
                    })
            );

        }

    }




    private void openFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        String[] mimeTypes = {DOC, DOCX, PDF, PPT, PPTX, XLS, XLS1, XLSX};
        intent.setType("application/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, 1);
    }

    private void checkStorage() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            // 先判断有没有权限
////            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
////                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
////                openFile();
////            } else {
////                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
////            }
////        } else {
////            openFile();
////        }

        String[] cameraPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

        requestPermissions(this, cameraPermissions, REQUEST_CODE_STORAGE);
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
                    case REQUEST_CODE_STORAGE:
//                        Intent intent1 = new Intent(mContext, CaptureActivity.class);
//                        Objects.requireNonNull(getActivity()).startActivityForResult(intent1, REQUEST_CODE_SCAN);
                        openFile();
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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
//                case REQUEST_CODE_CAMARE:
////                    Intent intent1 = new Intent(mContext, CaptureActivity.class);
////                    Objects.requireNonNull(this).startActivityForResult(intent1, REQUEST_CODE_SCAN);
//                    takePhoto();
//                    break;
                case REQUEST_CODE_STORAGE:
//                    Intent intent1 = new Intent(mContext, CaptureActivity.class);
//                    Objects.requireNonNull(this).startActivityForResult(intent1, REQUEST_CODE_SCAN);
                    openFile();
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
//                case REQUEST_CODE_CAMARE:
//                    content = "在设置-应用-瓦丁-权限中开启打开相机权限，以正常使用拍照等功能";
//                    break;
//                case REQUEST_CODE_RECORD_AUDIO:
//                    content = "在设置-应用-瓦丁-权限中开启打开录取权限，以正常使用发送语音等功能";
//                    break;
                case REQUEST_CODE_STORAGE:
                    content = "在设置-应用-瓦丁-权限中开启打开录取权限，以正常使用发送语音等功能";
                    break;
            }
            final ConfirmDialog mConfirmDialog = new ConfirmDialog(getApplicationContext(), "权限申请",
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
                }//"a2c3b2f6d32345b2818be757f5adb54f"

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
