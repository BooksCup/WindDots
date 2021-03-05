package com.wd.winddots.desktop.list.employee.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * FileName: EmployeeListHeader
 * Author: 郑
 * Date: 2020/11/10 10:51 AM
 * Description: 成员列表 header
 */
public class EmployeeListHeader extends LinearLayout {

    public EmployeeListHeader(Context context) {
        super(context);
        initView();
    }

    public EmployeeListHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EmployeeListHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
            View view= LayoutInflater.from(getContext()).inflate(R.layout.view_employee_header, this, false);
            this.addView(view);
    }

}
