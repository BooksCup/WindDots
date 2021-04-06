package com.wd.winddots.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.wd.winddots.MyApplication;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.User;
import com.wd.winddots.enums.ResponseMsgEnum;
import com.wd.winddots.mvp.widget.MainActivity;
import com.wd.winddots.utils.MD5Util;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 登录
 *
 * @author zhou
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText mPhoneEt;

    @BindView(R.id.et_password)
    EditText mPasswordEt;

    VolleyUtil mVolleyUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_register})
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

    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void login() {
        String phone = mPhoneEt.getText().toString().trim();
        String password = mPasswordEt.getText().toString().trim();
        password = MD5Util.md5Decode32(password);
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            showToast("手机号或密码不能为空!");
        } else {
            showLoadingDialog();
            String url = Constant.APP_BASE_URL + "user/login";
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("phone", phone);
            paramMap.put("password", password);
            mVolleyUtil.httpPostRequest(url, paramMap, response -> {
                final User user = JSON.parseObject(response, User.class);
                JMessageClient.login(user.getId(), user.getImPassword(), new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        if (responseCode == 0) {
                            hideLoadingDialog();
                            // 登录成功后设置user和isLogin至sharedPreferences中
                            SpHelper.getInstance(LoginActivity.this).setString("phone", user.getPhone());
                            SpHelper.getInstance(LoginActivity.this).setString("enterpriseId", user.getEnterpriseId());
                            SpHelper.getInstance(LoginActivity.this).setString("id", user.getId());
                            SpHelper.getInstance(LoginActivity.this).setString("imPassword", user.getImPassword());
                            SpHelper.getInstance(LoginActivity.this).setString("name", user.getName());
                            SpHelper.getInstance(LoginActivity.this).setString("avatar", user.getAvatar());
                            SpHelper.getInstance(LoginActivity.this).setInt("userIsSuperAdmin", user.getIsSuperAdmin());
                            SpHelper.getInstance(LoginActivity.this).setString("isFabricCheckAdmin", user.getIsFabricCheckAdmin());
                            SpHelper.getInstance(LoginActivity.this).setUser(user);

                            MyApplication.USER_ID = user.getId();
                            MyApplication.ENTERPRISE_ID = user.getEnterpriseId();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            hideLoadingDialog();
                        }
                    }
                });
            }, volleyError -> {
                hideLoadingDialog();
                int errorCode = volleyError.networkResponse.statusCode;
                switch (errorCode) {
                    case 400:
                        Map<String, String> headers = volleyError.networkResponse.headers;
                        String responseCode = headers.get("responseCode");
                        if (ResponseMsgEnum.USER_NOT_EXIST.getResponseCode().equals(responseCode)) {
                            showToast("用户名或者密码错误");
                        } else {
                            showToast("网络繁忙");
                        }
                        break;
                }

            });
        }
    }

}
