package com.wd.winddots.activity.select;

import android.os.Bundle;

import com.wd.winddots.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;

/**
 * 选择-往来单位
 *
 * @author zhou
 */
public class SelectRelatedCompany extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_related_company);
        ButterKnife.bind(this);
    }

}
