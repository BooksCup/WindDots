package com.wd.winddots.fast.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.utils.Utils;

import java.util.Calendar;

import androidx.annotation.Nullable;

/**
 * FileName: MineAttendanceSignPop
 * Author: 郑
 * Date: 2020/6/2 12:07 PM
 * Description:
 */
public class MineAttendanceSignPop extends RelativeLayout {


    private TextView mTypeTextView;
    private TextView mTimeTextView;
    private TextView mLocationTextView;
    private EditText mEditText;
    private TextView mCommitBtn;
    private MineAttendanceSignPopActionListener listener;

    public void setMineAttendanceSignPopActionListener(MineAttendanceSignPopActionListener listener1) {
        listener = listener1;
    }


    public MineAttendanceSignPop(Context context) {
        super(context);
        initView();
    }

    public MineAttendanceSignPop(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MineAttendanceSignPop(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_sign, this, false);
        mTypeTextView = view.findViewById(R.id.pop_sign_type);
        mTimeTextView = view.findViewById(R.id.pop_sign_time);
        mLocationTextView = view.findViewById(R.id.pop_sign_location);
        mEditText = view.findViewById(R.id.pop_sign_edit);
        mCommitBtn = view.findViewById(R.id.pop_sign_save);
        mCommitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.commitBtnDidClick();
                }
            }
        });

        setTime();
        this.addView(view);
    }

    private void setTime() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String weekS = "";
        switch (week) {
            case 1:
                weekS = "星期天";
                break;
            case 2:
                weekS = "星期一";
                break;
            case 3:
                weekS = "星期二";
                break;
            case 4:
                weekS = "星期三";
                break;
            case 5:
                weekS = "星期四";
                break;
            case 6:
                weekS = "星期五";
                break;
            case 7:
                weekS = "星期六";
                break;

        }

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        String monthS = Utils.int2String(month);
        String dayS = Utils.int2String(day);
        String hourS = Utils.int2String(hour);
        String minS = Utils.int2String(minute);


        mTimeTextView.setText(monthS + "月" + dayS + "日 " + weekS + " " + hourS + ":" + minS);

    }

    public void setTitleS(String s) {
        mTypeTextView.setText(s);
    }

    public void setLocation(String s) {
        mLocationTextView.setText(s);
    }

    public String getText() {
        return mEditText.getText().toString().trim();
    }

    public interface MineAttendanceSignPopActionListener {
        void commitBtnDidClick();
    }


}
