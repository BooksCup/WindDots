package com.wd.winddots.mvp.widget;


import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.client.android.CaptureActivity;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseMvpActivity;
import com.wd.winddots.bean.resp.UpgradeBean;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.desktop.list.invoice.activity.InvoiceDetailActivity;
import com.wd.winddots.desktop.list.invoice.bean.InvoiceDetailBean;
import com.wd.winddots.fast.activity.MeAttendanceActivity;
import com.wd.winddots.fast.activity.MineClaimingActivity;
import com.wd.winddots.fragment.AppFragment;
import com.wd.winddots.mvp.listener.MainActivityDispatchEventListener;
import com.wd.winddots.mvp.presenter.MainPresenter;
import com.wd.winddots.mvp.view.MainView;
import com.wd.winddots.mvp.widget.fragment.BusinessFragment;
import com.wd.winddots.mvp.widget.fragment.MessageFragment;
import com.wd.winddots.mvp.widget.fragment.MineFragment;
import com.wd.winddots.mvp.widget.fragment.ScheduleFragment;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.upgarde.AppUtils;
import com.wd.winddots.upgarde.UpdateDialog;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.view.PopShortcut;
import com.wd.winddots.view.Upgrade;
import com.wd.winddots.view.dialog.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;

import cn.jmessage.support.qiniu.android.utils.StringUtils;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements View.OnClickListener, PopShortcut.PopShortcutClickListener, Upgrade.UpgradeActionListener {

    private static final int REQUEST_CODE_LOCATION = 1;
    private static final int REQUEST_CODE_CAMERA = 2;

    private static final int REQUEST_CODE_SCAN = 10086;

    private MainActivityDispatchEventListener dispatchEventListener;

    /**
     * 快捷
     */
    private RelativeLayout mShortcutRl;
    private PopShortcut mPopupView;
    private RelativeLayout mBtnShortCut;
    private PopupWindow mPopupWindow;


    public CompositeSubscription compositeSubscription;
    public MsgDataManager dataManager;
    public ElseDataManager elseDataManager;

    public UpgradeBean.UpgradeBeanDetail mUpgradeBean;

    private Fragment[] mFragments;
    private ImageView[] mMainButtonIvs;
    private TextView[] mMainButtonTvs;
    private int mIndex;
    // 当前fragment的index
    private int mCurrentTabIndex;

    private BusinessFragment mBusinessAreaFragment;
    //private DesktopFragment mDesktopFragment;
    private AppFragment mAppFragment;
    private MessageFragment mMessageFragment;
    private ScheduleFragment mScheduleFragment;
    private MineFragment mMineFragment;

    private ImageView mShortcutIv;
    private TextView mShortcutTv;


    public void setDispatchEventListener(MainActivityDispatchEventListener dispatchEventListener) {
        this.dispatchEventListener = dispatchEventListener;
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mBtnShortCut = findViewById(R.id.activity_main_tab_bottom_bar_shortcut);
        mShortcutRl = findViewById(R.id.rl_shortcut);
//        mShortcutIv = findViewById(R.id.iv_shortcut);
//        mShortcutTv = findViewById(R.id.tv_shortcut);
        compositeSubscription = new CompositeSubscription();
        dataManager = new MsgDataManager();
        elseDataManager = new ElseDataManager();

        mBusinessAreaFragment = new BusinessFragment();
        mAppFragment = new AppFragment();
        mMessageFragment = new MessageFragment();
        mScheduleFragment = new ScheduleFragment();
        mMineFragment = new MineFragment();

        mFragments = new Fragment[]{mMessageFragment,mScheduleFragment, mAppFragment,mBusinessAreaFragment,mMineFragment};

        mMainButtonIvs = new ImageView[5];
        mMainButtonIvs[0] = findViewById(R.id.iv_message);
        mMainButtonIvs[1] = findViewById(R.id.iv_schedule);
        mMainButtonIvs[2] = findViewById(R.id.iv_desktop);
        mMainButtonIvs[3] = findViewById(R.id.iv_business);
        mMainButtonIvs[4] = findViewById(R.id.iv_shortcut);

        mMainButtonIvs[0].setSelected(true);
        mMainButtonTvs = new TextView[5];
        mMainButtonTvs[0] = findViewById(R.id.tv_message);
        mMainButtonTvs[1] = findViewById(R.id.tv_schedule);
        mMainButtonTvs[2] = findViewById(R.id.tv_desktop);
        mMainButtonTvs[3] = findViewById(R.id.tv_business);
        mMainButtonTvs[4] = findViewById(R.id.tv_shortcut);
        mMainButtonTvs[0].setTextColor(getColor(R.color.colorAccent));
        mIndex = 0;
        mCurrentTabIndex = 0;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rl_fragment_container, mMessageFragment)
                .add(R.id.rl_fragment_container, mAppFragment)
                .add(R.id.rl_fragment_container, mBusinessAreaFragment)
                .add(R.id.rl_fragment_container, mScheduleFragment)
                .add(R.id.rl_fragment_container, mMineFragment)
                .hide(mBusinessAreaFragment).hide(mAppFragment).hide(mScheduleFragment).hide(mMineFragment)
                .show(mMessageFragment).commit();


    }

    @Override
    protected void initListener() {
        //mShortcutRl.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        SpHelper spHelper = SpHelper.getInstance(mContext.getApplicationContext());
        // 极光登录
        JMessageClient.login(spHelper.getString("id"), spHelper.getString("imPassword"), new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    // showToast("连接IM成功");
                } else {
                    //showToast("连接IM失败: " + responseMessage);
                }
            }
        });

        this.upgrade();


        Log.e("tag",spHelper.getUserId() + "------setAlias");

        JPushInterface.setAlias(mContext, 0, spHelper.getUser().getId());
        Log.e("tag",JPushInterface.getRegistrationID(mContext) + "------getRegistrationID");
//        JPushInterface.setAlias(MainActivity.this, spHelper.getUserId(), new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//                Log.e("net666","3333333333333");
//            }
//        });

    }

    private String getLauncherClassName(Context context) {
        ComponentName launchComponent = getLauncherComponentName(context);
        if (launchComponent == null) {
            return "";
        } else {
            return launchComponent.getClassName();
        }
    }

    private ComponentName getLauncherComponentName(Context context) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context
                .getPackageName());
        if (launchIntent != null) {
            return launchIntent.getComponent();
        } else {
            return null;
        }
    }

    private void upgrade() {
        compositeSubscription.add(dataManager.upgrade().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("netssss", String.valueOf(e));
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666upgrade", s);
                        Gson gson = new Gson();
                        UpgradeBean bean = gson.fromJson(s, UpgradeBean.class);
                        mUpgradeBean = bean.getList().get(0);
                        int code = AppUtils.getVersionCode(mContext);
                        if (code < mUpgradeBean.getVersionCode()) {
                            Upgrade popView = new Upgrade(mContext);
                            //popView.setUpgradeActionListener(this);
                            popView.setUpgradeActionListener(MainActivity.this);
                            List<String> list = gson.fromJson(mUpgradeBean.getLog(), new TypeToken<List<String>>() {
                            }.getType());
                            String version = mContext.getString(R.string.upgrade_version) + mUpgradeBean.getVersionName() + mContext.getString(R.string.upgrade_title);
                            popView.setVersion(version);
                            popView.setData(list);
                            mPopupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            // 实例化一个ColorDrawable颜色为半透明
                            ColorDrawable dw = new ColorDrawable(0000000000);
                            // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
                            mPopupWindow.setBackgroundDrawable(dw);
                            int height = dip2px(MainActivity.this, 100);
                            mPopupWindow.showAtLocation(mShortcutRl, Gravity.BOTTOM | Gravity.RIGHT, 0, height);
                        }
                    }
                })
        );
    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_business:
                // 会话列表
                mIndex = 3;
                break;
            case R.id.rl_desktop:
                // 桌面
                mIndex = 2;
                break;
            case R.id.rl_message:
                // 消息
                mIndex = 0;
                break;
            case R.id.rl_schedule:
                // 日程
                mIndex = 1;
                break;
            case R.id.rl_shortcut:
                // 日程
                mIndex = 4;
                break;
        }

        if (mCurrentTabIndex != mIndex) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(mFragments[mCurrentTabIndex]);
            if (!mFragments[mIndex].isAdded()) {
                trx.add(R.id.rl_fragment_container, mFragments[mIndex]);
            }
            trx.show(mFragments[mIndex]).commit();
        }
        mMainButtonIvs[mCurrentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mMainButtonIvs[mIndex].setSelected(true);
        mMainButtonTvs[mCurrentTabIndex].setTextColor(getColor(R.color.tab_text_color));
        mMainButtonTvs[mIndex].setTextColor(getColor(R.color.colorAccent));
        mCurrentTabIndex = mIndex;

//        mShortcutIv.setSelected(false);
//        mShortcutTv.setTextColor(getColor(R.color.tab_text_color));
    }

    /**
     * 初始化首页弹出框
     */
    private void initPopupWindow() {
        //View mPopupView = View.inflate(this, R.layout.popup_window_shortcut, null);
        mPopupView = new PopShortcut(mContext);
        mPopupView.setTabBottomBarClickListener(this);
        mPopupWindow = new PopupWindow(mPopupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        mPopupWindow.setBackgroundDrawable(dw);

        int height = dip2px(MainActivity.this, 100);

        mPopupWindow.showAtLocation(mShortcutRl, Gravity.BOTTOM | Gravity.RIGHT, 0, height);
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
                    case REQUEST_CODE_LOCATION:
                        startActivity(MeAttendanceActivity.class);
                        break;
                    case REQUEST_CODE_CAMERA:
                        Intent intent1 = new Intent(mContext, CaptureActivity.class);
                        startActivityForResult(intent1, REQUEST_CODE_SCAN);
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
                case REQUEST_CODE_LOCATION:
                    // 同意定位权限,进入地图选择器
                    startActivity(MeAttendanceActivity.class);
                    break;
                case REQUEST_CODE_CAMERA:
                    Intent intent1 = new Intent(mContext, CaptureActivity.class);
                    startActivityForResult(intent1, REQUEST_CODE_SCAN);
                    break;
            }
        } else {
            // 拒绝授权做的处理，弹出弹框提示用户授权
            handleRejectPermission(MainActivity.this, permissions[0], requestCode);
        }
    }

    public void handleRejectPermission(final Activity context, String permission, int requestCode) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            String content = "";
            switch (requestCode) {
                case REQUEST_CODE_LOCATION:
                    content = "在设置-应用-瓦丁-权限中开启位置信息权限，以正常使用考勤等功能";
                    break;
                case REQUEST_CODE_CAMERA:
                    content = "在设置-应用-瓦丁-权限中开启打开相机权限，以正常使用扫码等功能";
                    break;
            }
            final ConfirmDialog mConfirmDialog = new ConfirmDialog(MainActivity.this, "权限申请",
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 快捷
            case R.id.rl_shortcut:
                // 清除选中tab
                clearTabSelected();
                // 显示弹窗
                initPopupWindow();
                mShortcutIv.setSelected(true);
                mShortcutTv.setTextColor(getColor(R.color.colorAccent));
                break;
        }
    }

    private void clearTabSelected() {
        for (ImageView mainButtonIv : mMainButtonIvs) {
            mainButtonIv.setSelected(false);
        }
        for (TextView mainButtonTv : mMainButtonTvs) {
            mainButtonTv.setTextColor(getColor(R.color.tab_text_color));
        }
    }

    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void tabBottomBarDidClick(int tab) {
        mIndex = tab - 1;
        if (tab == 5) {
            mPopupWindow.dismiss();
            return;
        }

        if (mCurrentTabIndex != mIndex) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(mFragments[mCurrentTabIndex]);
            if (!mFragments[mIndex].isAdded()) {
                trx.add(R.id.rl_fragment_container, mFragments[mIndex]);
            }
            trx.show(mFragments[mIndex]).commit();
        }
        mMainButtonIvs[mCurrentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mMainButtonIvs[mIndex].setSelected(true);
        mMainButtonTvs[mCurrentTabIndex].setTextColor(getColor(R.color.tab_text_color));
        mMainButtonTvs[mIndex].setTextColor(getColor(R.color.colorAccent));
        mCurrentTabIndex = mIndex;

        mShortcutIv.setSelected(false);
        mShortcutTv.setTextColor(getColor(R.color.tab_text_color));
        mPopupWindow.dismiss();
    }

    @Override
    public void fastButtonDidClick(int button) {
        switch (button) {
            case 1:
                Intent intent = new Intent(mContext, MineClaimingActivity.class);
                startActivity(intent);
                break;
            case 2:
//                Intent intent1 = new Intent(mContext, MeAttendanceActivity.class);
//                startActivity(intent1);
                String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(this, permissions, REQUEST_CODE_LOCATION);
                break;
            case 3:
                String[] cameraPermissions = new String[]{Manifest.permission.CAMERA};
                requestPermissions(this, cameraPermissions, REQUEST_CODE_CAMERA);
                break;
        }
        mPopupWindow.dismiss();

    }

    @Override
    public void onCancel() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    @Override
    public void onUpgrade() {
//
        UpdateDialog.goToDownload(mContext, mUpgradeBean.getApkUrl());
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String resultStr = data.getStringExtra("result");
        if (StringUtils.isNullOrEmpty(resultStr)) {
            return;
        }

        if (resultStr.length() > 4 && "http".equals(resultStr.substring(0, 4))) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(Const.WEB_ACTIVITY_URL_INTENT, resultStr);
            startActivity(intent);
        } else {

            Log.e("net666", resultStr);
            compositeSubscription.add(elseDataManager.getInvoiceDetailByScan(resultStr, SpHelper.getInstance(mContext).getUserId()).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("net666", String.valueOf(e));
                        }

                        @Override
                        public void onNext(String s) {
                            Log.e("net666", s);
                            Gson gson = new Gson();
                            InvoiceDetailBean invoiceDetailBean = gson.fromJson(s, InvoiceDetailBean.class);
                            if (0 == invoiceDetailBean.getCode()) {
                                Intent intent = new Intent(mContext, InvoiceDetailActivity.class);
                                String jsonS = gson.toJson(invoiceDetailBean.getData());
                                intent.putExtra("data", s);
                                intent.putExtra("isScan", true);
                                startActivity(intent);
                            } else {
                                showToast("未能识别的二维码");
                            }
                        }
                    })
            );
        }


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (dispatchEventListener != null){
            dispatchEventListener.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
