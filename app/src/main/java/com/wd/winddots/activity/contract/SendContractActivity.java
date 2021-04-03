package com.wd.winddots.activity.contract;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.select.SelectContractActivity;
import com.wd.winddots.activity.web.WebViewActivity;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Contract;

import androidx.annotation.Nullable;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发起合同
 *
 * @author zhou
 */
public class SendContractActivity extends BaseActivity {
    private static final int REQUEST_CODE_CONTRACT = 1;

    static String TAG = "SendContractActivity";

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    // 签署方
    @BindView(R.id.tv_signer)
    TextView mSignerTv;

    // 文件主题
    @BindView(R.id.et_subject)
    EditText mSubjectEt;

    // 截止日期
    @BindView(R.id.tv_sign_validity)
    TextView mSignValidityTv;

    // 合同有效期
    @BindView(R.id.tv_contract_validity)
    TextView mContractValidityTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_contract);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.ll_select_contract, R.id.ll_signer,
            R.id.ll_sign_validity, R.id.ll_contract_validity, R.id.tv_send_contract})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_select_contract:
                // 选择签署合同
                intent = new Intent(SendContractActivity.this, SelectContractActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CONTRACT);
                break;
            case R.id.ll_signer:
                // 签署方
                break;
            case R.id.ll_sign_validity:
                // 签署截止日期
                showDatePickDlg();
                break;
            case R.id.ll_contract_validity:
                // 合同有效期
                break;
            case R.id.tv_send_contract:
                // 发起合同
                intent = new Intent(SendContractActivity.this, WebViewActivity.class);
                intent.putExtra(Constant.WEB_ACTIVITY_URL_INTENT, "www.baidu.com");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        mSubjectEt.setText(mSubjectEt.getText().toString());
        mSubjectEt.setSelection(mSubjectEt.getText().toString().length());
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //签署合同
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CONTRACT:
                    // 物品
                    if (null != data) {
                        Contract contract = (Contract) data.getSerializableExtra("contract");
                        mTitleTv.setText(contract.getContractNo());
                    }
                    break;
            }
        }
    }

    /**
     * 显示选择日期
     */
    public void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) ->
                mSignValidityTv.setText(year + "-" + monthOfYear + "-" + dayOfMonth), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
