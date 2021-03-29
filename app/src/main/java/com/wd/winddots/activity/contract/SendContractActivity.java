package com.wd.winddots.activity.contract;

import android.os.Bundle;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;

import androidx.annotation.Nullable;

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
    }
}
