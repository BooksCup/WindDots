package com.wd.winddots.fast.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * FileName: MineAttendanceSignHeader
 * Author: 郑
 * Date: 2020/6/2 10:49 AM
 * Description:
 */
public class MineAttendanceSignHeader extends LinearLayout {



    public MineAttendanceSignHeader(Context context) {
        super(context);
        initView();
    }

    public MineAttendanceSignHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MineAttendanceSignHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
            View view= LayoutInflater.from(getContext()).inflate(R.layout.view_attendance_sign_header, this, false);
            this.addView(view);
    }

}
