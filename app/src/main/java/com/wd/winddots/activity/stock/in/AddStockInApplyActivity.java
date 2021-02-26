package com.wd.winddots.activity.stock.in;

import android.os.Bundle;
import android.view.View;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提交入库申请(入库单)
 *
 * @author zhou
 */
public class AddStockInApplyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_in_apply);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
