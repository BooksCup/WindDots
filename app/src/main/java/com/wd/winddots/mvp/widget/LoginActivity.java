package com.wd.winddots.mvp.widget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wd.winddots.MyApplication;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseMvpActivity;
import com.wd.winddots.bean.param.LoginParam;
import com.wd.winddots.bean.resp.LoginBean;
import com.wd.winddots.mvp.presenter.impl.LoginPresenterImpl;
import com.wd.winddots.mvp.view.LoginView;
import com.wd.winddots.register.activity.RegisterActivity;
import com.wd.winddots.utils.MD5Util;
import com.wd.winddots.utils.SpHelper;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * 登录
 *
 * @author zhou
 */
public class LoginActivity extends BaseMvpActivity<LoginView, LoginPresenterImpl>
        implements LoginView, View.OnClickListener {

    private EditText mPhoneEt;
    private EditText mPasswordEt;
    private TextView mLoginTv;
    private TextView mRegisterTv;

    @Override
    public LoginPresenterImpl initPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    protected int initLayout() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mPhoneEt = findViewById(R.id.et_phone);
        mPasswordEt = findViewById(R.id.et_password);
        mLoginTv = findViewById(R.id.tv_login);
        mRegisterTv = findViewById(R.id.tv_register);

//        mPhoneEt.setText("13813815768");
//        mPasswordEt.setText("123456");
//        mPhoneEt.setText("1850575223.");
//        mPasswordEt.setText("000000");
    }


    @Override
    protected void initListener() {
        mLoginTv.setOnClickListener(this);
        mRegisterTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_register:
                register();
                break;
        }
    }

    private void register(){
        Intent intent = new Intent(mContext, RegisterActivity.class);
        startActivity(intent);
    }


    private void login() {
        String phone = mPhoneEt.getText().toString().trim();
        String password = mPasswordEt.getText().toString().trim();
        password = MD5Util.md5Decode32(password);
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            showToast("手机号或密码不能为空!");
        } else {
            showLoading();
            LoginParam param = new LoginParam(phone, password);
            Gson gson = new Gson();
            String paramS = gson.toJson(param);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramS);
            presenter.login(body);
        }
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        final LoginBean.DataBean data = loginBean.getData();
        // 极光登录
        JMessageClient.login(data.getId(), data.getImPassword(), new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    hideLoading();
                    showToast("登录成功");
                    SpHelper.getInstance(mApplicationContext).setString("phone", data.getPhone());
                    SpHelper.getInstance(mApplicationContext).setString("enterpriseId", data.getEnterpriseId());
                    SpHelper.getInstance(mApplicationContext).setString("id", data.getId());
                    SpHelper.getInstance(mApplicationContext).setString("imPassword", data.getImPassword());
                    SpHelper.getInstance(mApplicationContext).setString("name", data.getName());
                    SpHelper.getInstance(mApplicationContext).setString("avatar", data.getAvatar());
                    SpHelper.getInstance(mApplicationContext).setInt("userIsSuperAdmin", data.getIsSuperAdmin());
                    MyApplication.USER_ID = data.getId();
                    MyApplication.ENTERPRISE_ID = data.getEnterpriseId();
                    startActivity(MainActivity.class);
                    finish();
                } else {
                    hideLoading();
                    showToast("登录失败: " + responseMessage);
                }
            }
        });
    }


    @Override
    public void onLoginFailure(String msg) {
        Log.e("net666","onLoginFailure");
        hideLoading();
        showToast("登录失败: " + msg);
    }

    @Override
    public void onLoginError(String errorMsg) {
        Log.e("net666","onLoginError");
        hideLoading();
        showToast(errorMsg);
    }

    @Override
    public void onLoginComplete() {
        Log.e("net666","onLoginComplete");
    }
}
