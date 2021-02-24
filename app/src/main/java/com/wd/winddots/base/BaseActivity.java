package com.wd.winddots.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.wd.winddots.message.bean.GroupChatHistoryBean;
import com.wd.winddots.message.bean.PrivateChatHistoryBean;
import com.wd.winddots.utils.statusbar.StatusBarUtil;
import com.wd.winddots.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;
    public Context mAppContext;
    private LoadingDialog mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setStatusBarLightMode(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
//        ButterKnife.bind(this);
        //避免切换横竖屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        mAppContext = getApplicationContext();
        EventBus.getDefault().register(this);
        initBar();
        initView();
        initListener();
        initData();
        SysApplication.getInstance().addActivity(this);
    }

    private void initBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayShowTitleEnabled(false);
    }


    /**
     * 返回一个用于显示界面的布局id
     *
     * @return 视图id
     */
    public abstract int getContentView();

    /**
     * 初始化View的代码写在这个方法中
     */
    public abstract void initView();

    /**
     * 初始化监听器的代码写在这个方法中
     */
    public abstract void initListener();

    /**
     * 初始数据的代码写在这个方法中，用于从服务器获取数据
     */
    public abstract void initData();

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public String getSting(int id) {
        return mContext.getString(id);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }


    /**
     * 显示toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(mContext.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetNewMessage(PrivateChatHistoryBean.MessageListBean newMessageListBean) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetNewMessage(GroupChatHistoryBean.MessageListBean newMessageListBean) {

    }

    public void finishAll() {
        finish();
    }

    public int getStatusBarHeight() {
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

//    class MyReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if(intent.getAction().equals("user_logout")){
//                //LogUtils.e("zs","退出登陆");
//                finish();
//            }
//        }
//    }

    public void showLoading() {
        if (mLoading != null) {
            return;
        }
        mLoading = LoadingDialog.getInstance(mContext);
        mLoading.show();
    }

    public void hideLoading() {
        if (mLoading != null) {
            mLoading.hide();
            mLoading = null;
        }
    }

}
