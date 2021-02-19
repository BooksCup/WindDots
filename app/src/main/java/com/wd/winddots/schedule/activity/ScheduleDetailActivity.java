package com.wd.winddots.schedule.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haibin.calendarview.Calendar;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.bean.resp.ScheduleBena;
import com.wd.winddots.components.calendar.range.CalendarRangeView;
import com.wd.winddots.components.users.MultipleUserSelectActivity;
import com.wd.winddots.components.users.UserBean;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.entity.User;
import com.wd.winddots.schedule.adapter.ScheduleUserAdapter;
import com.wd.winddots.schedule.presenter.impl.ScheduleDetailPresenterImpl;
import com.wd.winddots.schedule.presenter.view.ScheduleDetailView;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.RequestBody;

/**
 * FileName: ScheduleDetailActivity
 * Author: 郑
 * Date: 2020/6/4 9:32 AM
 * Description:
 */
public class ScheduleDetailActivity extends CommonActivity<ScheduleDetailView, ScheduleDetailPresenterImpl>
        implements ScheduleDetailView, View.OnClickListener, CalendarRangeView.CalendarRangeViewActionListener {


    private RecyclerView mRecyclerView;
    private ScheduleUserAdapter mAdapter;
    private TextView mDateTextView;
    private EditText mTitleEditText;
    private EditText mContentEditText;
    private LinearLayout mBottomBar;

    private PopupWindow mPopupWindow;
    private LinearLayout mBody;
    private CalendarRangeView mCalendarRangeView;

    private String mStartTime;
    private String mEndTime;
    private List<User> userBeanList = new ArrayList<>();

    private String mScheduleId;
    private String mUserId;
    private boolean isCreate = true;


    @Override
    public ScheduleDetailPresenterImpl initPresenter() {
        return new ScheduleDetailPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();

        addBadyView(R.layout.activity_schedule_detail);

        Intent intent = getIntent();
        mScheduleId = intent.getStringExtra("scheduleId");
        mUserId = SpHelper.getInstance(mContext).getUserId();
        if (StringUtils.isNullOrEmpty(mScheduleId)){
            setTitleText("新建日程");
        }else {
            setTitleText("日程详情");
        }

        LinearLayout datePicker = findViewById(R.id.activity_schedule_datepick);
        final LinearLayout userPicker = findViewById(R.id.activity_schedule_user);
        TextView deleteBtn = findViewById(R.id.activity_schedule_delete);
        TextView commitBtn = findViewById(R.id.activity_schedule_commit);

        mBottomBar = findViewById(R.id.activity_schedule_bottomBar);
        mTitleEditText = findViewById(R.id.activity_schedule_title);
        mDateTextView = findViewById(R.id.activity_schedule__datepicktext);
        mRecyclerView = findViewById(R.id.activity_schedule_userList);
        mContentEditText = findViewById(R.id.activity_schedule_content);
        mBody = findViewById(R.id.activity_schedule_body);

        mAdapter = new ScheduleUserAdapter(R.layout.item_schedule_user, userBeanList);
        GridLayoutManager manager = new GridLayoutManager(mContext, 5);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    userPicker.performClick();
                }
                return false;
            }
        });

        String currentDate = Utils.getCurrentDate();
        mStartTime = currentDate;
        mEndTime = currentDate;
        mDateTextView.setText(currentDate + "~" + currentDate);

        datePicker.setOnClickListener(this);
        userPicker.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);

    }


    @Override
    public void initData() {
        super.initData();
        if (!StringUtils.isNullOrEmpty(mScheduleId)) {
            presenter.getScheduleDetail(mUserId, mScheduleId);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (!isCreate){
            return;
        }

        switch (v.getId()) {
            case R.id.activity_schedule_datepick:
                hideKeyboard();
                timePicker();
                break;
            case R.id.activity_schedule_user:
                userPicker();
                break;
            case R.id.activity_schedule_commit:
                onCommit();
                break;
            case R.id.activity_schedule_delete:
                onDelete();
                break;
        }
    }

    /*
     * 选择日期
     * */
    private void timePicker() {

        mCalendarRangeView = new CalendarRangeView(mContext);
        if (!StringUtils.isNullOrEmpty(mStartTime) && !StringUtils.isNullOrEmpty(mEndTime)) {
            mCalendarRangeView.setSelectRange(mStartTime, mEndTime);
        }
        mCalendarRangeView.setCalendarRangeViewActionListener(this);
        //mCalendarRangeView.setSelectRange(mStartTime, mEndTime);
        mPopupWindow = new PopupWindow(mCalendarRangeView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(mBody, Gravity.TOP | Gravity.LEFT, 0, 0);
    }


    /*
     * 选择用户
     * */
    private void userPicker() {
        List<String> ids = new ArrayList<>();
        List<String> disableIds = new ArrayList<>();
        if (userBeanList.size() > 0) {
            for (int i = 0; i < userBeanList.size(); i++) {
                if (!mUserId.equals(userBeanList.get(i).getId())) {
                    ids.add(userBeanList.get(i).getId());
                }
            }
        }
        disableIds.add(mUserId);
        Gson gson = new Gson();
        Intent intent = new Intent(mContext, MultipleUserSelectActivity.class);
        intent.putExtra("disableIds", gson.toJson(disableIds));
        intent.putExtra("selectIds", gson.toJson(ids));
        startActivityForResult(intent, 0);
    }

    @Override
    public void onCancel() {
        mPopupWindow.dismiss();
        mPopupWindow = null;
    }

    @Override
    public void onConfirm() {

        Calendar startCalendar = mCalendarRangeView.getStartCalendar();
        Calendar endCalendar = mCalendarRangeView.getEndCalendar();
        String startHour = mCalendarRangeView.getStartHour();
        String endHour = mCalendarRangeView.getEndHour();
        if (startCalendar == null) {
            showToast("请先选择开始时间");
            return;
        }


        String startMonthS = Utils.int2String(startCalendar.getMonth());
        String startDayS = Utils.int2String(startCalendar.getDay());


        String startTime = startCalendar.getYear() + "-" + startMonthS + "-" + startDayS + " ";

        String endDate = "";
        if (endCalendar == null) {
            endDate = startCalendar.getYear() + "-" + startMonthS + "-" + startDayS + " ";
        } else {
            String endMonthS = Utils.int2String(endCalendar.getMonth());
            String endDayS = Utils.int2String(endCalendar.getDay());
            endDate = endCalendar.getYear() + "-" + endMonthS + "-" + endDayS + " ";
        }
        String endTime = endDate;
        if (!StringUtils.isNullOrEmpty(startHour)) {


            if (StringUtils.isNullOrEmpty(endHour)) {
                showToast("请先填写结束时间");
                return;
            }
            if (startHour.length() == 1) {
                startHour = "0" + startHour;
            }

            if (endHour.length() == 1) {
                endHour = "0" + endHour;
            }

            String startMin = StringUtils.isNullOrEmpty(mCalendarRangeView.getStartMin()) ? "00" : mCalendarRangeView.getStartMin();
            if (startMin.length() == 1) {
                startMin = "0" + startMin;
            }

            String endMin = StringUtils.isNullOrEmpty(mCalendarRangeView.getEndMin()) ? "00" : mCalendarRangeView.getEndMin();
            if (endMin.length() == 1) {
                endMin = "0" + endMin;
            }
            startTime = startTime + startHour + ":" + startMin;
            endTime = endTime + endHour + ":" + endMin;
        }


        mStartTime = startTime;
        mEndTime = endTime;
        mDateTextView.setText(startTime + "~" + endTime);
        mPopupWindow.dismiss();
        mPopupWindow = null;


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }

        if (resultCode == Const.MULTIPLE_USER_SELECT_TARGRT) {
            List<User> list = (List<User>) data.getSerializableExtra("list");
            StringBuilder name = new StringBuilder();
            userBeanList.clear();
            userBeanList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    /*
     * 点击删除
     * */
    private void onDelete() {
        if (StringUtils.isNullOrEmpty(mScheduleId)){
            finish();
        }else {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("提示");
            builder.setMessage("确定要删除该日程?");

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    presenter.deleteScheduleDetail(mScheduleId,mUserId);
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.show();


        }
    }

    /*
     * 点击提交
     * */
    private void onCommit() {
        String title = mTitleEditText.getText().toString().trim();
        if (StringUtils.isNullOrEmpty(title)) {
            showToast("请先输入标题");
            return;
        }

        Map data = new HashMap();
        data.put("scheduleTitle", title);
        data.put("scheduleRemindTime", -1);
        data.put("scheduleLat", 31.93857086820907);
        data.put("scheduleLng", 118.83408678221055);
        data.put("scheduleCreateUserId", mUserId);
        data.put("scheduleTag", "#33B3EA");
        data.put("scheduleDate", mStartTime.substring(0, 10));
        data.put("scheduleStartTime", mStartTime);
        data.put("scheduleEndTime", mEndTime);
        data.put("scheduleLocation", "南京市江宁区庄排路109号");
        List<String> contactUserIds = new ArrayList<>();
        for (int i = 0; i < userBeanList.size(); i++) {
            if (!mUserId.equals(userBeanList.get(i).getId())) {
                contactUserIds.add(userBeanList.get(i).getId());
            }
        }
        data.put("contactUserIds", contactUserIds);
        if (mStartTime.length() > 10) {
            data.put("scheduleIsAllDay", "0");
        } else {
            data.put("scheduleIsAllDay", "1");
        }

        String content = mContentEditText.getText().toString().trim();
        if (!StringUtils.isNullOrEmpty(content)) {
            data.put("scheduleContent", content);
        }


        if (StringUtils.isNullOrEmpty(mScheduleId)) {
            RequestBody requestBody = Utils.map2requestBody(data);
            presenter.addSchedule(requestBody);
        } else {
            data.put("scheduleId", mScheduleId);
            RequestBody requestBody = Utils.map2requestBody(data);
            presenter.updateSchedule(requestBody);
        }


    }


    @Override
    public void deleteScheduleSuccess() {
        showToast("删除日程成功");
        Intent intent = new Intent();
        intent.putExtra("data", "data");
        setResult(250, intent);
        finish();
    }

    @Override
    public void deleteScheduleError() {

    }

    @Override
    public void deleteScheduleCompleted() {

    }

    /*
     * 获取日程详情
     * */
    @Override
    public void getScheduleDetailSuccess(ScheduleBena bena) {

        mTitleEditText.setText(bena.getScheduleTitle());

        if (mUserId.equals(bena.getScheduleCreateUserId())) {
            isCreate = true;
            mTitleEditText.setFocusable(false);
            mTitleEditText.setFocusableInTouchMode(false);

            mContentEditText.setFocusable(false);
            mContentEditText.setFocusableInTouchMode(false);

            mBottomBar.setVisibility(View.VISIBLE);
        }else {
            isCreate = false;

            mTitleEditText.setFocusable(true);
            mTitleEditText.setFocusableInTouchMode(true);

            mContentEditText.setFocusable(true);
            mContentEditText.setFocusableInTouchMode(true);

            mBottomBar.setVisibility(View.GONE);
        }

        String startTime = "";
        String endTime = "";
        if (bena.getScheduleStartTime().length() >= 16) {
            if (bena.getScheduleStartTime().substring(11, 16).equals("00:00")) {
                startTime = bena.getScheduleStartTime().substring(0, 10);
            } else {
                startTime = bena.getScheduleStartTime().substring(0, 16);
            }
        }
        if (bena.getScheduleEndTime().length() >= 16) {
            if (bena.getScheduleEndTime().substring(11, 16).equals("00:00")) {
                endTime = bena.getScheduleEndTime().substring(0, 10);
            } else {
                endTime = bena.getScheduleEndTime().substring(0, 16);
            }
        }
        if (!StringUtils.isNullOrEmpty(startTime)){
            mDateTextView.setText(startTime + "~" + endTime);
        }
        mStartTime = startTime;
        mEndTime = endTime;
        mContentEditText.setText(bena.getScheduleContent());
        userBeanList.clear();
        List<ScheduleBena.User> userBeanList1 = bena.getUserList();
        if (userBeanList1 == null) {
            userBeanList1 = new ArrayList<>();
        }

        Gson gson = new Gson();
        String jsonS = gson.toJson(userBeanList1);
        List<User> list = gson.fromJson(jsonS, new TypeToken<List<User>>() {
        }.getType());
        userBeanList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getScheduleDetailError() {

    }

    @Override
    public void getScheduleDetailCompleted() {

    }


    /*
     * 新建日程
     * */
    @Override
    public void addScheduleSuccess() {
        showToast("新建日程成功");
        Intent intent = new Intent();
        intent.putExtra("data", "data");
        setResult(250, intent);
        finish();
    }

    @Override
    public void addScheduleError() {
        showToast("新建日程失败,请稍后重试");
    }

    @Override
    public void addScheduleCompleted() {

    }


    /*
     * 修改日程
     * */
    @Override
    public void updateScheduleSuccess() {
        showToast("编辑日程成功");
        Intent intent = new Intent();
        intent.putExtra("data", "data");
        setResult(250, intent);
        finish();
    }

    @Override
    public void updateScheduleError() {
        showToast("编辑日程失败,请稍后重试");
    }

    @Override
    public void updateScheduleCompleted() {

    }
}
