package com.wd.winddots.activity.check.fabric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.select.SelectRelatedCompanyActivity;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增简单订单
 *
 * @author zhou
 */
public class AddSimpleOrderActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_simple_order);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.iv_back, R.id.ll_related_company})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_related_company:
                // 往来单位
                intent = new Intent(AddSimpleOrderActivity.this, SelectRelatedCompanyActivity.class);
                startActivity(intent);
                break;
        }
    }


}
