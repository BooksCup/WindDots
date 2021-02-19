package com.wd.winddots.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.wd.winddots.utils.statusbar.StatusBarUtil;
import com.wd.winddots.view.LoadingDialog;

public abstract class BaseMvpActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    public T presenter;

    protected Context mContext;
    protected Context mApplicationContext;

    private LoadingDialog mLoading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setStatusBarLightMode(this, Color.WHITE);
        super.onCreate(savedInstanceState);

        setContentView(initLayout());

        mContext = this;

        mApplicationContext = getApplicationContext();

        initBar();

        initView();


        presenter = initPresenter();
        presenter.attach((V)this);
        initListener();

        initData();

        SysApplication.getInstance().addActivity(this);
    }

    private void initBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null){
            presenter.dettach();
            presenter = null;
            System.gc();
        }
    }

    // 实例化presenter
    public abstract T initPresenter();


    /**
     * 初始化布局
     * @return
     */
    protected abstract int initLayout();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化listener
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();


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
     * @param msg
     */
    public void showToast(String msg){
        Toast.makeText(mContext.getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }



    public void showLoading(){

        if (mLoading != null){
            return;
        }

        mLoading = LoadingDialog.getInstance(mContext);
        mLoading.show();
    }

    public void hideLoading(){
        if (mLoading != null){
            mLoading.hide();
            mLoading = null;
        }
    }


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

    protected void initStatusBar() {
        Window win = getWindow();
        // KITKAT也能满足，只是SYSTEM_UI_FLAG_LIGHT_STATUS_BAR（状态栏字体颜色反转）只有在6.0才有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加
            win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

}
