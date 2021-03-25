package com.wd.winddots.activity.contract;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.entity.User;
import com.wd.winddots.utils.SpHelper;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 实名认证成功
 *
 * @author zhou
 */
public class RealNameCertSuccessActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView mNameTv;

    @BindView(R.id.tv_id_number)
    TextView mIdNumberTv;

    @BindView(R.id.tv_phone)
    TextView mPhoneTv;

    User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_cert_success);
        ButterKnife.bind(this);
        mUser = SpHelper.getInstance(this).getUser();
        initView();
    }

    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initView() {
        mNameTv.setText(mUser.getName());
        mIdNumberTv.setText(mUser.getIdNumber());
        mPhoneTv.setText(mUser.getPhone());
    }

}
