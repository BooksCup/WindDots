package com.wd.winddots.activity.contract;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新建联系人
 */
public class AddLinkmanActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText mNameEt;

    @BindView(R.id.et_phone)
    EditText mPhoneEt;

    @BindView(R.id.et_email)
    EditText mEmailEt;

    @BindView(R.id.et_company_name)
    EditText mCompanyNameEt;

    @BindView(R.id.et_remark)
    EditText mRemarkEt;

    VolleyUtil mVolleyUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_linkman);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                getData();
                finish();
                break;
        }
    }

    private void getData() {
        String name = mNameEt.getText().toString().trim();
        String phone = mPhoneEt.getText().toString().trim();
        String email = mEmailEt.getText().toString().trim();
        String company = mCompanyNameEt.getText().toString().trim();
        String remark = mRemarkEt.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入联系人的姓名");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入联系人的电话");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            showToast("请输入联系人的邮箱");
            return;
        }
        if (TextUtils.isEmpty(company)) {
            showToast("请输入联系人的所属企业");
            return;
        }
        if (TextUtils.isEmpty(remark)) {
            showToast("请输入备注");
            return;
        }
        saveLinkmanData(name, phone, email, company, remark);
    }

    /**
     * 临时跳转其他页面时保存写好的数据
     */
    private void saveData() {
        mNameEt.getText().toString();
        mPhoneEt.getText().toString();
        mEmailEt.getText().toString();
        mCompanyNameEt.getText().toString();
        mRemarkEt.getText().toString();
    }

    private void saveLinkmanData(String name, String phone, String email, String company, String remark) {
        String url = Constant.APP_BASE_URL + "linkman";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", SpHelper.getInstance(this).getUserId());
        paramMap.put("enterpriseId", SpHelper.getInstance(this).getEnterpriseId());
        paramMap.put("name", name);
        paramMap.put("phone", phone);
        paramMap.put("email", email);
        paramMap.put("companyName", company);
        paramMap.put("remark", remark);

        mVolleyUtil.httpPostRequest(url, paramMap, response -> {
            showToast(getString(R.string.add_linkman_success));
            hideLoadingDialog();
            finish();
        }, volleyError -> {
            hideLoadingDialog();
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }

    @Override
    protected void onResume() {
        saveData();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}