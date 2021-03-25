package com.wd.winddots.activity.contract;

import android.os.Bundle;
import android.view.View;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 实名认证结束
 *
 * @author zhou
 */
public class RealNameCertFinishActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_cert_finish);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_finish})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_finish:
                finish();
                break;
        }
    }

}
