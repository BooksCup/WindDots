package com.wd.winddots.components.calendar.range;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.wd.winddots.R;
import com.wd.winddots.utils.Utils;

import java.util.Timer;

import androidx.annotation.Nullable;

/**
 * FileName: CalendarRangeView
 * Author: 郑
 * Date: 2020/5/22 2:50 PM
 * Description: 日历范围选择
 */
public class CalendarRangeView extends LinearLayout implements
        CalendarView.OnCalendarRangeSelectListener, CalendarView.OnMonthChangeListener, View.OnClickListener {


    CalendarView mCalendarRangeView;
    private TextView mYearMonthTextView;
    private TextView mStartHourEditText;
    private TextView mStartMinEditText;
    private TextView mEndHourEditText;
    private TextView mEndMinEditText;
    private TextView mStartTextView;
    private TextView mEndTextView;

    private TextView mStartDateTextView;
    private TextView mEndDateTextView;

    private Switch mSwitch;
    private TextView mSwitchTv;


    private CalendarRangeViewActionListener listener;
    private Calendar startCalendar;
    private Calendar endCalendar;

    public Calendar getStartCalendar() {
        return startCalendar;
    }

    public Calendar getEndCalendar() {
        return endCalendar;
    }

    public String getStartHour() {
        return mStartHourEditText.getText().toString().trim();
    }

    public String getStartMin() {
        return mStartMinEditText.getText().toString().trim();
    }

    public String getEndHour() {
        return mEndHourEditText.getText().toString().trim();
    }

    public String getEndMin() {
        return mEndMinEditText.getText().toString().trim();
    }

    public String getStartDate() {

        if (startCalendar == null) {
            return null;
        }

        String startMonthS = Utils.int2String(startCalendar.getMonth());
        String startDayS = Utils.int2String(startCalendar.getDay());
        String startTime = startCalendar.getYear() + "-" + startMonthS + "-" + startDayS;
        return startTime;
    }

    public String getEndDate() {
        if (startCalendar == null) {
            return null;
        }
        String endMonthS = Utils.int2String(endCalendar.getMonth());
        String endDayS = Utils.int2String(endCalendar.getDay());
        String endTime = endCalendar.getYear() + "-" + endMonthS + "-" + endDayS;
        return endTime;
    }

    public void setCalendarRangeViewActionListener(CalendarRangeViewActionListener listener1) {
        listener = listener1;
    }

    public void setSelectRange(String start, String end) {
        int startYear;
        int startMonth;
        int startDay;

        String startHour;
        String startMin;

        int endYear;
        int endMonth;
        int endDay;
        String endHour;
        String endMin;

        startYear = Integer.parseInt(start.substring(0, 4));
        startMonth = Integer.parseInt(start.substring(5, 7));
        startDay = Integer.parseInt(start.substring(8, 10));

        endYear = Integer.parseInt(end.substring(0, 4));
        endMonth = Integer.parseInt(end.substring(5, 7));
        endDay = Integer.parseInt(end.substring(8, 10));

        Calendar startCalendar = new Calendar();
        startCalendar.setYear(startYear);
        startCalendar.setMonth(startMonth);
        startCalendar.setDay(startDay);

        Calendar endCalendar = new Calendar();
        endCalendar.setYear(endYear);
        endCalendar.setMonth(endMonth);
        endCalendar.setDay(endDay);

        mCalendarRangeView.setSelectCalendarRange(startCalendar, endCalendar);
        if (start.length() > 11) {
            startHour = start.substring(11, 13);
            startMin = start.substring(14, 16);
            endHour = end.substring(11, 13);
            endMin = end.substring(14, 16);
            mStartHourEditText.setText(startHour);
            mStartMinEditText.setText(startMin);
            mEndHourEditText.setText(endHour);
            mEndMinEditText.setText(endMin);
            mStartTextView.setVisibility(View.VISIBLE);
            mEndTextView.setVisibility(View.VISIBLE);
            mSwitchTv.setText("日期");
            mSwitch.setChecked(false);
        }else {
            mSwitchTv.setText("全天");
            mSwitch.setChecked(true);
        }

    }


    public CalendarRangeView(Context context) {
        super(context);
        initView();
    }

    public CalendarRangeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CalendarRangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_calendar_range, this, false);
        mCalendarRangeView = view.findViewById(R.id.view_calendae_range_CalendarView);
        mYearMonthTextView = view.findViewById(R.id.view_calendae_range_yearText);
        mStartHourEditText = view.findViewById(R.id.start_hour);
        mStartMinEditText = view.findViewById(R.id.start_min);
        mEndHourEditText = view.findViewById(R.id.end_hour);
        mEndMinEditText = view.findViewById(R.id.end_min);
        mStartTextView = view.findViewById(R.id.tv_start);
        mEndTextView  = view.findViewById(R.id.tv_end);

        mStartDateTextView = view.findViewById(R.id.start_date);
        mEndDateTextView = view.findViewById(R.id.end_date);

        mCalendarRangeView.setOnCalendarRangeSelectListener(this);
        mCalendarRangeView.setOnMonthChangeListener(this);
        LinearLayout modal = view.findViewById(R.id.view_calendae_range_modal);
        TextView cancelBtn = view.findViewById(R.id.view_calendae_range_cancel);
        TextView confirmBtn = view.findViewById(R.id.view_calendae_range_confirm);
        final LinearLayout start = view.findViewById(R.id.ll_start);
        final LinearLayout end = view.findViewById(R.id.ll_end);


        start.setOnClickListener(this);
        end.setOnClickListener(this);
        mStartHourEditText.setOnClickListener(this);
        mStartMinEditText.setOnClickListener(this);
        mEndHourEditText.setOnClickListener(this);
        mEndMinEditText.setOnClickListener(this);

        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               startTimeClick();
            }
        });
        mStartHourEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeClick();
            }
        });
        mStartMinEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeClick();
            }
        });
        end.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimeClick();
            }
        });


        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });

        modal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });

        confirmBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onConfirm();
                }
            }
        });

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH) + 1;
        String monthS;
        String dayS;
        if (month < 10) {
            monthS = "0" + month;
        } else {
            monthS = "" + month;
        }

        mYearMonthTextView.setText(year + "年" + monthS + "月");


         mSwitch = view.findViewById(R.id.view_switch);
        mSwitchTv = view.findViewById(R.id.tv_status);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mSwitchTv.setText("全天");
                    start.setVisibility(GONE);
                    end.setVisibility(GONE);
                }else {
                    mSwitchTv.setText("日期");
                    start.setVisibility(VISIBLE);
                    end.setVisibility(VISIBLE);
                }
            }
        });


        this.addView(view);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_start:
            case R.id.start_hour:
            case R.id.start_min:
                startTimeClick();
                break;
            case R.id.ll_end:
            case R.id.end_hour:
            case R.id.end_min:
                endTimeClick();
                break;
        }
    }


    private void startTimeClick(){
        java.util.Calendar calendar1 = java.util.Calendar.getInstance();
        int hourOfDay = calendar1.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = calendar1.get(java.util.Calendar.MINUTE);
        int second = calendar1.get(java.util.Calendar.SECOND);
        TimePickerDialog pickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour = "";
                if (hourOfDay < 10){
                    hour = "0" + hourOfDay;
                }else {
                    hour = hourOfDay + "";
                }

                String min = "";
                if (minute < 10){
                    min = "0" + minute;
                }else {
                    min = minute + "";
                }
                mStartHourEditText.setText(hour);
                mStartMinEditText.setText(min);
                mStartTextView.setVisibility(View.VISIBLE);
            }
        }, hourOfDay, minute, true);
        pickerDialog.setTitle("开始时间");

        pickerDialog.show();
    }

    private void endTimeClick(){
        java.util.Calendar calendar1 = java.util.Calendar.getInstance();
        int hourOfDay = calendar1.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = calendar1.get(java.util.Calendar.MINUTE);
        int second = calendar1.get(java.util.Calendar.SECOND);
        TimePickerDialog pickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour = "";
                if (hourOfDay < 10){
                    hour = "0" + hourOfDay;
                }else {
                    hour = hourOfDay + "";
                }

                String min = "";
                if (minute < 10){
                    min = "0" + minute;
                }else {
                    min = minute + "";
                }
                mEndHourEditText.setText(hour);
                mEndMinEditText.setText(min);
                mEndTextView.setVisibility(View.VISIBLE);
            }
        }, hourOfDay, minute, true);
        pickerDialog.setTitle("结束时间");

        pickerDialog.show();
    }


    @Override
    public void onCalendarSelectOutOfRange(Calendar calendar) {

    }

    @Override
    public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {

    }

    @Override
    public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {
        if (!isEnd) {
            startCalendar = calendar;
//            mStartDateTextView.setText(getStartDate());
//            mEndDateTextView.setText(getStartDate());
//
        } else {
            endCalendar = calendar;
//            mEndDateTextView.setText(getEndDate());

        }
    }

    @Override
    public void onMonthChange(int year, int month) {

        String monthS = "";
        if (month < 10) {
            monthS = "0" + month;
        } else {
            monthS = "" + month;
        }
        mYearMonthTextView.setText(year + "年" + monthS + "月");
    }




    public interface CalendarRangeViewActionListener {
        void onCancel();

        void onConfirm();
    }

}
