package com.wd.winddots.register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Enterprise;
import com.wd.winddots.utils.MD5Util;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.LoadingDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册
 *
 * @author zhou
 */
public class RegisterActivity extends FragmentActivity {

    private static final String TAG = "RegisterActivity";
    private static final int COUNTDOWN = 60; //倒数秒数

    private static final int REQUEST_CODE_SEARCH_ENTERPRISE = 10086;

    private VolleyUtil mVolleyUtil;

    @BindView(R.id.et_name)
    EditText mNameEt;

    @BindView(R.id.et_phone)
    EditText mPhoneEt;

    @BindView(R.id.et_password)
    EditText mPasswordEt;

    @BindView(R.id.tv_verify_code)
    TextView mVerifyCodeTv;

    @BindView(R.id.pb_loading)
    ProgressBar mLoadingPb;

    @BindView(R.id.tv_enterprise)
    TextView mEnterpriseTv;

    @BindView(R.id.et_verify_code)
    EditText mVerifyCodeEt;

    private LoadingDialog mDialog;
    private Timer mTimer = new Timer();
    private TimerTask mTask;
    private int mCountdown = COUNTDOWN;

    private Enterprise mEnterprise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mVolleyUtil = VolleyUtil.getInstance(this);
        mDialog = LoadingDialog.getInstance(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_verify_code, R.id.ll_enterprise, R.id.ll_user_apply})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_verify_code:
                getVerifyCode();
                break;
            case R.id.ll_enterprise:
                onSearchEnterprise();
                break;
            case R.id.ll_user_apply:
                onUserApply();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {

        String phone = mPhoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getString(R.string.register_phone_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        if (mCountdown != COUNTDOWN) {
            return;
        }
        mVerifyCodeTv.setVisibility(View.GONE);
        mLoadingPb.setVisibility(View.VISIBLE);

        final String url = Constant.APP_BASE_URL + "verifyCode";
        Map<String, String> params = new HashMap<>();
        params.put("type", "0");
        params.put("phone", phone);

        mVolleyUtil.httpPostRequest(url, params, response -> {
            mVerifyCodeTv.setText(mCountdown + "秒后重发");
            mVerifyCodeTv.setVisibility(View.VISIBLE);
            mLoadingPb.setVisibility(View.GONE);
            initTime();
        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
            mVerifyCodeTv.setVisibility(View.VISIBLE);
            mLoadingPb.setVisibility(View.GONE);
        });
    }

    /**
     * 倒计时
     */
    private void initTime() {
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    mCountdown -= 1;
                    mVerifyCodeTv.setText(mCountdown + "秒后重发");
                    if (mCountdown == 0) {
                        mVerifyCodeTv.setText(getString(R.string.register_get_verify_code));
                        mCountdown = COUNTDOWN;
                        mTimer.cancel();
                        mTimer = null;
                    }
                });
            }
        };
        mTimer.schedule(mTask, 0, 1000);
    }

    /**
     * 搜索企业
     */
    private void onSearchEnterprise() {
        Intent intent = new Intent(this, SearchEnterpriseActivity.class);
        if (null != mEnterprise) {
            intent.putExtra("keyword", mEnterprise.getKeyword());
        }
        startActivityForResult(intent, REQUEST_CODE_SEARCH_ENTERPRISE);
    }

    /**
     * 申请加入公司
     */
    private void onUserApply() {

        String name = mNameEt.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, getString(R.string.register_name_empty), Toast.LENGTH_SHORT).show();
        }

        String phone = mPhoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getString(R.string.register_phone_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        String password = mPasswordEt.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, getString(R.string.register_password_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        String verifyCode = mVerifyCodeEt.getText().toString().trim();
        if (TextUtils.isEmpty(verifyCode)) {
            Toast.makeText(this, getString(R.string.register_verify_code_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        if (null == mEnterprise) {
            Toast.makeText(this, getString(R.string.register_enterprise_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        String md5Pwd = MD5Util.md5Decode32(password);
        mDialog.show();
        final String url = Constant.APP_BASE_URL + "userApply";
        Map<String, String> params = new HashMap();
        params.put("code", verifyCode);
        params.put("enterpriseId", mEnterprise.getId());
        params.put("name", name);
        params.put("password", md5Pwd);
        params.put("phone", phone);
        mVolleyUtil.httpPostRequest(url, params, response -> {
            mDialog.hide();
            Toast.makeText(this, getString(R.string.user_apply_success), Toast.LENGTH_SHORT).show();
            finish();
        }, volleyError -> {
            mDialog.hide();
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_SEARCH_ENTERPRISE) {
            String json = data.getStringExtra("data");
            Enterprise enterprise = JSON.parseObject(json, Enterprise.class);
            mEnterpriseTv.setText(enterprise.getName());
            mEnterprise = enterprise;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}
