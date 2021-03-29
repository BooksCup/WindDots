package com.wd.winddots.activity.contract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.web.WebViewActivity;
import com.wd.winddots.cons.Constant;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发起合同
 *
 * @author zhou
 */
public class SendContractActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_contract);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_send_contract})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_send_contract:
                Intent intent = new Intent(SendContractActivity.this, WebViewActivity.class);
                intent.putExtra(Constant.WEB_ACTIVITY_URL_INTENT, "www.baidu.com");
                startActivity(intent);
                break;
        }
    }

}
