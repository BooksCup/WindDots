package com.wd.winddots.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.wd.winddots.R;

import androidx.annotation.Nullable;

/**
 * FileName: DatePicker
 * Author: 郑
 * Date: 2020/5/7 3:06 PM
 * Description: 日期选择器
 */
public class DatePicker extends LinearLayout
        implements CalendarView.OnCalendarSelectListener,
        CalendarView.OnMonthChangeListener{

    private PopupWindow mPopupWindow;
    private OnCalendarSelectListener listener;
    private TextView mHeaderText;

    public DatePicker(Context context) {
        super(context);
        initView();
    }

    public DatePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DatePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public void setOnCalendarSelectListener(OnCalendarSelectListener listener1) {
        listener = listener1;
    }


    private void initView(){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.pop_date_picker, this, false);
        mHeaderText = view.findViewById(R.id.view_datepicker_headertext);
        RelativeLayout headerView = view.findViewById(R.id.view_datepicker_header);
        headerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });

        CalendarView calendarView = view.findViewById(R.id.view_datepicker_calendarview);
        calendarView.setOnCalendarSelectListener(this);
        calendarView.setOnMonthChangeListener(this);

        this.addView(view);
    }




    public void showInBody(View body){
        mPopupWindow  = new PopupWindow(this, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.showAtLocation(body, Gravity.TOP| Gravity.LEFT,0,0);
    }

    public void hide() {
        if (mPopupWindow != null){
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        if (listener != null){
            //listener.onCalendarSelect(calendar);
        }
    }

    @Override
    public void onMonthChange(int year, int month) {
        mHeaderText.setText(String.valueOf(month));
    }


    public interface OnCalendarSelectListener{
        void onCalendarSelect(Calendar calendar);
    }




}
