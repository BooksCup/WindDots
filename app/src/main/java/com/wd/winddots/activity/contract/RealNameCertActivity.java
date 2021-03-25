package com.wd.winddots.activity.contract;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.EContractApiResult;
import com.wd.winddots.entity.User;
import com.wd.winddots.enums.EContractApiResultEnum;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 实名认证
 *
 * @author zhou
 */
public class RealNameCertActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText mNameEt;

    @BindView(R.id.et_phone)
    EditText mPhoneEt;

    @BindView(R.id.et_id_number)
    EditText mIdNumberEt;

    @BindView(R.id.et_verify_code)
    EditText mVerifyCodeEt;

    User mUser;
    VolleyUtil mVolleyUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_cert);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mUser = SpHelper.getInstance(this).getUser();
        mVolleyUtil = VolleyUtil.getInstance(this);
        mNameEt.setText(mUser.getName());
        mPhoneEt.setText(mUser.getPhone());
    }

    @OnClick({R.id.iv_back, R.id.tv_verify_code, R.id.tv_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_verify_code:
                realNameCert();
                break;
            case R.id.tv_submit:
                checkVerifyCode();
                break;
        }
    }

    /**
     * 实名认证
     */
    private void realNameCert() {

        String name = mNameEt.getText().toString().trim();
        String idNumber = mIdNumberEt.getText().toString().trim();
        String phone = mPhoneEt.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            showToast("请输入真实姓名");
            return;
        }

        if (TextUtils.isEmpty(idNumber)) {
            showToast("请输入你的身份证号");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            showToast("请输入你的手机号");
            return;
        }

        showLoadingDialog();

        String url = Constant.APP_BASE_URL + "realNameCert";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", mUser.getId());
        paramMap.put("userName", name);
        paramMap.put("mobile", phone);
        paramMap.put("identityNum", idNumber);

        mVolleyUtil.httpPostRequest(url, paramMap, response -> {
            hideLoadingDialog();
            EContractApiResult eContractApiResult = JSON.parseObject(response, EContractApiResult.class);
            if (EContractApiResultEnum.SUCCESS.getCode() == eContractApiResult.getCode()) {
                showToast("验证码已发送");
            } else {
                showToast(eContractApiResult.getMessage());
            }
        }, volleyError -> {
            hideLoadingDialog();
            mVolleyUtil.handleCommonErrorResponse(RealNameCertActivity.this, volleyError);
        });
    }

    /**
     * 检查验证码
     */
    private void checkVerifyCode() {

        String name = mNameEt.getText().toString().trim();
        String idNumber = mIdNumberEt.getText().toString().trim();
        String phone = mPhoneEt.getText().toString().trim();
        String verifyCode = mVerifyCodeEt.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            showToast("请输入真实姓名");
            return;
        }

        if (TextUtils.isEmpty(idNumber)) {
            showToast("请输入你的身份证号");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            showToast("请输入你的手机号");
            return;
        }

        if (TextUtils.isEmpty(verifyCode)) {
            showToast("请输入验证码");
            return;
        }

        showLoadingDialog();

        String url = Constant.APP_BASE_URL + "realNameCert/check?userId=" + mUser.getId() + "&code=" + verifyCode;

        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            EContractApiResult eContractApiResult = JSON.parseObject(response, EContractApiResult.class);
            if (EContractApiResultEnum.SUCCESS.getCode() == eContractApiResult.getCode()) {
                // 实名认证成功
                mUser.setIdNumber(idNumber);
                mUser.setRealNameCertType(Constant.REAL_NAME_CERT_SUCCESS);
                SpHelper.getInstance(this).setUser(mUser);
                startActivity(new Intent(RealNameCertActivity.this, RealNameCertFinishActivity.class));
                finish();
            } else {
                // 实名认证失败
                showToast(eContractApiResult.getMessage());
            }
        }, volleyError -> {
            hideLoadingDialog();
            mVolleyUtil.handleCommonErrorResponse(RealNameCertActivity.this, volleyError);
        });
    }

}
