package com.wd.winddots.view.employee;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * 人员
 *
 * @author zhou
 */
public class EmployeeHeaderView extends LinearLayout {

    public EmployeeHeaderView(Context context) {
        super(context);
        initView();
    }

    public EmployeeHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EmployeeHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).
                inflate(R.layout.view_employee_header, this, false);
        this.addView(view);
    }

}
