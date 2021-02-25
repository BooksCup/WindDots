package com.wd.winddots.desktop.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.wd.winddots.MyApplication;
import com.wd.winddots.R;
import com.wd.winddots.activity.login.LoginActivity;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.base.SysApplication;
import com.wd.winddots.utils.SpHelper;

import cn.jpush.android.api.JPushInterface;

/**
 * FileName: SettingActivity
 * Author: 郑
 * Date: 2020/5/4 10:11 AM
 * Description: 设置页面-退出登录
 */
public class SettingActivity extends CommonActivity {

    private TextView mVersionTextView;

    @Override
    public void initView() {
        super.initView();
        setTitleText("设置");
        addBadyView(R.layout.activity_setting);
        mVersionTextView = findViewById(R.id.activity_setting_versiontext);
    }


    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter() {
        };
    }


    @Override
    public void initData() {
        super.initData();
        getVersionInfo();
    }


    private void getVersionInfo() {
        PackageManager manager = getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            mVersionTextView.setText(info.versionName);
        } catch (Exception e) {

        }

    }

    /**
     * 退出登录
     *
     * @param v view
     */
    public void logOut(View v) {
        SpHelper.getInstance(mContext).setString("phone", "");
        SpHelper.getInstance(mContext).setString("enterpriseId", "");
        SpHelper.getInstance(mContext).setString("id", "");
        SpHelper.getInstance(mContext).setString("imPassword", "");
        MyApplication.USER_ID = "";
        MyApplication.ENTERPRISE_ID = "";
        SysApplication.getInstance().exit();
        startActivity(LoginActivity.class);
        //JPushInterface.setAlias(mContext,0,"");
        JPushInterface.deleteAlias(mContext,0);

    }

}
