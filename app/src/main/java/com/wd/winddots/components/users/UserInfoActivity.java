package com.wd.winddots.components.users;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.bean.UserInfoBean;
import com.wd.winddots.message.activity.PrivateChatActivity;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.VisitingCardView;
import com.wd.winddots.view.dialog.ConfirmDialog;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: UserInfoActivity
 * Author: 郑
 * Date: 2020/6/22 12:17 PM
 * Description:
 */
public class UserInfoActivity extends CommonActivity {

    private static final int REQUEST_CODE_CALL_PHONE = 1;

    public CompositeSubscription compositeSubscription;
    public MsgDataManager dataManager;


    private String mFromUserId;
    private String mApplyId;
    private UserInfoBean infoBean;

    private VisitingCardView mVisitingCardView;
    private TextView mNameTextView;
    private ImageView mGenderIconl;
    private TextView mEnterpriseTextView;
    private TextView mDepartmentTextView;
    private TextView mPositionTextView;
    private TextView mJobNumTextView;
    private TextView mPhoneTextView;
    private TextView mEmailTextView;
    private TextView mAddressTextView;
    private TextView mRelationTextView;
    private LinearLayout body;

    private PopupWindow mPopupWindow;


    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }


    @Override
    public void initView() {
        super.initView();
        setTitleText(mContext.getString(R.string.user_info_title));
        addBadyView(R.layout.activity_user_info);

        compositeSubscription = new CompositeSubscription();
        dataManager = new MsgDataManager();

        Intent intent = getIntent();
        mFromUserId = intent.getStringExtra("id");
        mApplyId = intent.getStringExtra("applyId");

        body = findViewById(R.id.body);

        mVisitingCardView = findViewById(R.id.view_visiting_card);
        LinearLayout bottomBar = findViewById(R.id.ll_bottomBar);
        if (SpHelper.getInstance(mContext).getUserId().equals(mFromUserId)){
            bottomBar.setVisibility(View.GONE);
        }
        mGenderIconl = findViewById(R.id.iv_gender);
        mNameTextView = findViewById(R.id.tv_name);
        mEnterpriseTextView = findViewById(R.id.tv_enterprise);
        mDepartmentTextView = findViewById(R.id.tv_department);
        mPositionTextView = findViewById(R.id.tv_position);
        mJobNumTextView = findViewById(R.id.tv_jobnumber);
        mPhoneTextView = findViewById(R.id.tv_phone);
        mEmailTextView = findViewById(R.id.tv_email);
        mAddressTextView = findViewById(R.id.tv_address);
        mRelationTextView = findViewById(R.id.tv_relation);

        LinearLayout callPhone = findViewById(R.id.ll_call_phone);
        LinearLayout relation = findViewById(R.id.ll_relation);
        LinearLayout share = findViewById(R.id.ll_share);
        callPhone.setOnClickListener(this);
        relation.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_call_phone:
                onCallPhoneDidClick();
                break;
            case R.id.ll_relation:
                onRelationDidClick();
                break;
            case R.id.ll_share:
                initPopupWindow();
                break;
            case R.id.background:
                dismissPopupWindow();
                break;
            case R.id.ll_sharetosession:
                share(SendMessageToWX.Req.WXSceneSession);
                dismissPopupWindow();
                break;
            case R.id.ll_sharetotimeline:
                share(SendMessageToWX.Req.WXSceneTimeline);
                dismissPopupWindow();
                break;
            case R.id.ll_copyurl:
                //initPopupWindow();
                copyUrl();
                dismissPopupWindow();
                break;
        }
    }

    /*
    * 添加好友 同意好友请求 发送消息
    * */
    private void onRelationDidClick(){
        if (0 == infoBean.getIsFriend()){


            if (StringUtils.isNullOrEmpty(mApplyId)){
                postFriendApplys();
            }else {
                agreeFriendApplys();
            }
        }else { //已经是好友,发送消息
            String singleName = infoBean.getName();
            String toId = infoBean.getId();
            Intent intent = new Intent(mContext, PrivateChatActivity.class);
            intent.putExtra("title",singleName);
            intent.putExtra("targetId",toId);
            intent.putExtra("avatar",infoBean.getAvatar());
            startActivity(intent);
        }
    }

    /*
    * 拨打电话
    * */
    private void onCallPhoneDidClick() {
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
        requestPermissions(this, permissions,REQUEST_CODE_CALL_PHONE);
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
                    case REQUEST_CODE_CALL_PHONE:
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + infoBean.getPhone());
                        intent.setData(data);
                        startActivity(intent);
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
                case REQUEST_CODE_CALL_PHONE:
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + infoBean.getPhone());
                    intent.setData(data);
                    startActivity(intent);
                    break;
            }
        } else {
            // 拒绝授权做的处理，弹出弹框提示用户授权
            handleRejectPermission(UserInfoActivity.this, permissions[0], requestCode);
        }
    }

    public void handleRejectPermission(final Activity context, String permission, int requestCode) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            String content = "";
            switch (requestCode) {
                case REQUEST_CODE_CALL_PHONE:
                    content = "在设置-应用-瓦丁-权限中开启拨打电话";
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

    @Override
    public void initData() {
        super.initData();
        getData();
    }

    void getData() {
        String userId = SpHelper.getInstance(mContext).getUserId();
        compositeSubscription.add(dataManager.getUserInfo(mFromUserId, userId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("加载失败,请稍后重试");
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();

                        infoBean = gson.fromJson(s, UserInfoBean.class);

                        mVisitingCardView.setUpData(infoBean);

                        mNameTextView.setText(infoBean.getName());
                        mEnterpriseTextView.setText(infoBean.getEnterpriseName());
                        mDepartmentTextView.setText(StringUtils.isNullOrEmpty(infoBean.getDepartmentName()) ? "" : infoBean.getDepartmentName());
                        mPositionTextView.setText(StringUtils.isNullOrEmpty(infoBean.getJobName()) ? "" : infoBean.getJobName());
                        mJobNumTextView.setText(StringUtils.isNullOrEmpty(infoBean.getJobNo()) ? "" : infoBean.getJobNo());
                        mPhoneTextView.setText(infoBean.getPhone());
                        mEmailTextView.setText(StringUtils.isNullOrEmpty(infoBean.getEmail()) ? "" : infoBean.getEmail());
                        mAddressTextView.setText(StringUtils.isNullOrEmpty(infoBean.getCompanyAddress()) ? "" : infoBean.getCompanyAddress());
                        if (0 == infoBean.getIsFriend()){
                            if (StringUtils.isNullOrEmpty(mApplyId)){
                                mRelationTextView.setText(mContext.getString(R.string.user_info_add_friend));
                            }else {
                                mRelationTextView.setText(mContext.getString(R.string.user_info_apple_agree));
                            }

                        }else {
                            mRelationTextView.setText(mContext.getString(R.string.user_info_send_message));
                        }
                        if (infoBean.getGender() == 2) {
                            mGenderIconl.setImageResource(R.mipmap.girl);
                        } else {
                            mGenderIconl.setImageResource(R.mipmap.boy);
                        }
                    }
                })
        );

    }

    /*
    * 发送好友请求
    * */
    private void postFriendApplys(){
        String userId = SpHelper.getInstance(mContext).getUserId();
        compositeSubscription.add(dataManager.postFriendApplies(userId, mFromUserId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(mContext.getString(R.string.user_info_post_friend_apply_error));
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666",s);
                        if (StringUtils.isNullOrEmpty(s)){
                            showToast(mContext.getString(R.string.user_info_post_friend_apply_error));
                        }else {
                            if (s.length() == 9){
                                showToast(mContext.getString(R.string.user_info_post_friend_apply_success));
                                getData();
                            }else {
                                showToast(mContext.getString(R.string.user_info_post_friend_apply_error));
                            }
                        }
                    }
                })
        );
    }

    private void agreeFriendApplys(){
        String userId = SpHelper.getInstance(mContext).getUserId();
        String enterpriseId = SpHelper.getInstance(mContext).getEnterpriseId();
        compositeSubscription.add(dataManager.agreeFriendApplies(mApplyId, 2,userId,enterpriseId,infoBean.getId(),infoBean.getEnterpriseId()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(mContext.getString(R.string.user_info_post_friend_apply_error));
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666",s);
                        if (StringUtils.isNullOrEmpty(s)){
                            showToast(mContext.getString(R.string.user_info_agree_apply_error));
                        }else {
                            if (s.length() == 9){
                                showToast(mContext.getString(R.string.user_info_agree_apply_success));
                                getData();
                                Intent intent = new Intent();
                                setResult(1, intent);
                            }else {
                                showToast(mContext.getString(R.string.user_info_agree_apply_error));
                            }
                        }
                    }
                })
        );
    }


    /**
     * 初始化首页弹出框
     */
    private void initPopupWindow() {

        if (infoBean == null) {
            return;
        }

        View mPopupView = View.inflate(mContext, R.layout.pop_share, null);
//        mPopupView = new PopShortcut(getContext());
//        mPopupView.setTabBottomBarClickListener(this);
        mPopupWindow = new PopupWindow(mPopupView, LinearLayout.LayoutParams.MATCH_PARENT, Utils.getScreenHeight(mContext));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setClippingEnabled(false);

        View background = mPopupView.findViewById(R.id.background);
        LinearLayout shareToSession = mPopupView.findViewById(R.id.ll_sharetosession);
        LinearLayout shareToTimeLine = mPopupView.findViewById(R.id.ll_sharetotimeline);
        LinearLayout copyUrl = mPopupView.findViewById(R.id.ll_copyurl);

        background.setOnClickListener(this);
        shareToSession.setOnClickListener(this);
        shareToTimeLine.setOnClickListener(this);
        copyUrl.setOnClickListener(this);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        mPopupWindow.setBackgroundDrawable(dw);
        mPopupWindow.showAtLocation(body, Gravity.TOP | Gravity.LEFT, 0, 0);
    }

    private void copyUrl(){
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        String webpageUrl = "http://wd.cfpu.com/card/?userId=" + infoBean.getId() + "&fromUserId=" + SpHelper.getInstance(mContext).getUserId();
        ClipData mClipData = ClipData.newPlainText("Label", webpageUrl);
// 将ClipData内容放到系统剪贴板里。
        assert cm != null;
        cm.setPrimaryClip(mClipData);
        showToast(mContext.getString(R.string.share_copyurl_success));
    }


    private void share(final int scene) {
        final IWXAPI wxapi = WXAPIFactory.createWXAPI(mContext, "wx2cfb08abb06edfdf");
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            showToast(mContext.getString(R.string.share_noinstall_wechat));
            return;
        }

        // 初始化一个WXWebpageObject对象
        WXWebpageObject webpageObject = new WXWebpageObject();
        // 填写网页的url
        webpageObject.webpageUrl = "http://wd.cfpu.com/card/?userId=" + infoBean.getId() + "&fromUserId=" + SpHelper.getInstance(mContext).getUserId();

        // 用WXWebpageObject对象初始化一个WXMediaMessage对象

        String email = mContext.getString(R.string.share_position) + Utils.nullOrEmpty(infoBean.getEmail());

        if (email.length() > 19) {
            email = email.substring(0, 16) + "...";
        }

        final WXMediaMessage msg = new WXMediaMessage(webpageObject);
        // 填写网页标题、描述、位图
        msg.title = infoBean.getName();
        msg.description = mContext.getString(R.string.share_position) + Utils.nullOrEmpty(infoBean.getJobName())  + "\n" + email + "\n" + mContext.getString(R.string.share_enterprise) +  Utils.nullOrEmpty(infoBean.getEnterpriseName());
        // 如果没有位图，可以传null，会显示默认的图片


        ImageLoadingListener listener = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.e("net666", String.valueOf(loadedImage));
                msg.setThumbImage(loadedImage);
                // 构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                // transaction用于唯一标识一个请求（可自定义）
                req.transaction = "webpage";
                // 上文的WXMediaMessage对象
                req.message = msg;
                // SendMessageToWX.Req.WXSceneSession是分享到好友会话
                // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
                req.scene = scene;
                // 向微信发送请求
                wxapi.sendReq(req);

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        };
        ImageLoader.getInstance().loadImage(infoBean.getAvatar() + Utils.OSSImageSize(100), listener);
    }


    private void dismissPopupWindow() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }


}
