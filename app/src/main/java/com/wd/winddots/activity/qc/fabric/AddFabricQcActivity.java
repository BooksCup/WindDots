package com.wd.winddots.activity.qc.fabric;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.wd.winddots.R;
import com.wd.winddots.activity.select.SelectRelatedCompany;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增盘点(面料验货)
 *
 * @author zhou
 */
public class AddFabricQcActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fabric_qc);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.rl_related_company})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_related_company:
                Intent intent = new Intent(AddFabricQcActivity.this, SelectRelatedCompany.class);
                startActivity(intent);
                break;
        }
    }


}
