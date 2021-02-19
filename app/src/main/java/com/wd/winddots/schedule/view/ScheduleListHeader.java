package com.wd.winddots.schedule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * FileName: ScheduleListHeader
 * Author: 郑
 * Date: 2020/6/3 12:12 PM
 * Description:
 */
public class ScheduleListHeader extends RelativeLayout {


    public ScheduleListHeader(Context context) {
        super(context);
        initView();
    }

    public ScheduleListHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ScheduleListHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
            View view= LayoutInflater.from(getContext()).inflate(R.layout.view_schedule_list_header, this, false);
            this.addView(view);
    }

}
