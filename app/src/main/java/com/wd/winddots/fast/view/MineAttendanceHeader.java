package com.wd.winddots.fast.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * FileName: MineAttendanceHeader
 * Author: 郑
 * Date: 2020/5/27 2:39 PM
 * Description:
 */
public class MineAttendanceHeader extends LinearLayout {

    private MineAttendanceHeaderAddIconListener listener;

    public void setMineAttendanceHeaderAddIconListener(MineAttendanceHeaderAddIconListener listener1){
            listener = listener1;
    }

    public MineAttendanceHeader(Context context) {
        super(context);
        initView();
    }

    public MineAttendanceHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MineAttendanceHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
            View view= LayoutInflater.from(getContext()).inflate(R.layout.view_mine_attendance_header, this, false);
            ImageView addIcon = view.findViewById(R.id.fragemnt_mine_attendance_addicon);
            addIcon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.addIconDidClick();
                    }
                }
            });
            this.addView(view);
    }


    public interface MineAttendanceHeaderAddIconListener{
        void addIconDidClick();
    }

}
