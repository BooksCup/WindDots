package com.wd.winddots.mvp.widget.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.bean.resp.ScheduleBena;
import com.wd.winddots.mvp.presenter.impl.SchedulePresenterImpl;
import com.wd.winddots.mvp.view.ScheduleView;
import com.wd.winddots.mvp.widget.adapter.ScheduleAdapter;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.schedule.activity.ScheduleDetailActivity;
import com.wd.winddots.schedule.bean.ContentNotNullListBean;
import com.wd.winddots.schedule.view.ScheduleListHeader;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.BottomSearchBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ScheduleFragment extends BaseFragment<ScheduleView, SchedulePresenterImpl>
        implements CalendarView.OnMonthChangeListener, CalendarView.OnCalendarSelectListener ,
        BottomSearchBarView.BottomSearchBarViewClickListener, BaseQuickAdapter.OnItemClickListener {


    private TextView mYearMonthTextView;
    private CalendarView mCalendarView;
    private RecyclerView mRecyclerView;
    private CalendarLayout mCalendarLayout;

    private ScheduleAdapter mAdapter;

    public CompositeSubscription compositeSubscription;
    public ElseDataManager dataManager;

    public String mDate;
    private List<ScheduleBena> mDataSource = new ArrayList<>();


    @Override
    public SchedulePresenterImpl initPresenter() {
        return new SchedulePresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_schedule;
    }

    @Override
    public void initView() {

        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();


        mCalendarLayout = mView.findViewById(R.id.fragment_schedule_CalendarLayout);
        mYearMonthTextView = mView.findViewById(R.id.fragment_schedule_yearMonth);
        mCalendarView = mView.findViewById(R.id.fragment_schedule_CalendarView);
        mRecyclerView = mView.findViewById(R.id.fragment_schedule_rlist);
        BottomSearchBarView searchBarView = mView.findViewById(R.id.fragment_schedule_rlist_searchbar);
        searchBarView.setOnIconClickListener(this);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH) + 1;
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        String monthS;
        String dayS;
        if (month < 10) {
            monthS = "0" + month;
        } else {
            monthS = "" + month;
        }

        mYearMonthTextView.setText(year + "年" + monthS + "月");


        Calendar startCalendar = new Calendar();
        startCalendar.setYear(year);
        startCalendar.setMonth(month);
        startCalendar.setDay(day);

        Calendar endCalendar = new Calendar();
        endCalendar.setYear(year);
        endCalendar.setMonth(month);
        endCalendar.setDay(day);

        mCalendarView.setSelectCalendarRange(startCalendar, endCalendar);

        mDate = year + "-" + monthS + "-" + Utils.int2String(day);


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);


        mAdapter = new ScheduleAdapter(R.layout.item_schedule, mDataSource);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setHeaderView(new ScheduleListHeader(mContext));
        mAdapter.setOnItemClickListener(this);


//        Map<String, Calendar> map = new HashMap<>();
//        map.put(getSchemeCalendar(year, month, 8, 0xFF40db25, "假").toString(),
//                getSchemeCalendar(year, month, 8, 0xFF40db25, "假"));
//        mCalendarView.setSchemeDate(map);


        initData();

    }


    @Override
    public void initListener() {
        mCalendarView.setOnMonthChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);


    }

    @Override
    public void initData() {
        getContentNotNullList();
        getData();



    }

   private void setSchemeDate(List<String> dateList){

        Map data = new HashMap();
        for (int i = 0;i < dateList.size();i++){
            String date = dateList.get(i);
            data.put(getSchemeCalendar(date).toString(),
                    getSchemeCalendar(date));
        }

        mCalendarView.setSchemeDate(data);

    }


    private Calendar getSchemeCalendar(String date) {

        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5,7));
        int day = Integer.parseInt(date.substring(8,10));
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
       // calendar.setSchemeColor(0xFF008800);//如果单独标记颜色、则会使用这个颜色
        calendar.addScheme(new Calendar.Scheme());
//        calendar.addScheme(0xFF008800, "假");
//        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }




    private void getData() {
        compositeSubscription.add(dataManager.getUserSchedule(SpHelper.getInstance(mContext).getUserId(), mDate).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666", s);
                        Gson gson = new Gson();
                        List<ScheduleBena> list = gson.fromJson(s, new TypeToken<List<ScheduleBena>>() {
                        }.getType());
                        mDataSource.clear();
                        mDataSource.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                }));
    }


    private void getContentNotNullList() {
        compositeSubscription.add(dataManager.getUserScheduleContentNotNullList(SpHelper.getInstance(mContext).getUserId()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666", s);
                        Gson gson = new Gson();
                        //Map map = Utils.getMapForJson(s);
                        ContentNotNullListBean bean = gson.fromJson(s,ContentNotNullListBean.class);
                        //List<String> list = (List<String>) map.get("data");//gson.fromJson(s, new TypeToken<List<String>>() { }.getType());
                        setSchemeDate(bean.getData());
                    }
                }));
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

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        String date = calendar.getYear() + "-" + Utils.int2String(calendar.getMonth()) + "-" + Utils.int2String(calendar.getDay());
        mDate = date;
        getData();
    }

    @Override
    public void onSearchIconDidClick() {

    }


    @Override
    public void onAddIconDidClick() {
        Intent intent = new Intent(mContext, ScheduleDetailActivity.class);
        startActivityForResult(intent,100);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ScheduleBena bena = mDataSource.get(position);
        Intent intent = new Intent(mContext, ScheduleDetailActivity.class);
        intent.putExtra("scheduleId",bena.getScheduleId());
        startActivityForResult(intent,100);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            getData();
            getContentNotNullList();
        }
    }
}
