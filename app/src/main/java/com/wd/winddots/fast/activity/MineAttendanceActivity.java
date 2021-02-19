package com.wd.winddots.fast.activity;

import android.os.Handler;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.google.android.material.tabs.TabLayout;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.fast.adapter.MineAttendaceTabAdapter;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import androidx.viewpager.widget.ViewPager;

/**
 * FileName: AttendanceActivity
 * Author: 郑
 * Date: 2020/5/21 11:08 AM
 * Description: 考勤
 */
public class MineAttendanceActivity extends CommonActivity {

    private TextView mTimeTextView;
    private TextView mDayTextView;
    private TextView mWeekTextView;
    private TabLayout mTablayout;
    private ViewPager mViewPage;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private Timer timer = new Timer();
    private TimerTask task;
    private Handler mHandler;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();


    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }


    @Override
    public void initView() {
        super.initView();
        setTitleText("考勤");
        addBadyView(R.layout.activity_mine_attendance);

        mMapView = findViewById(R.id.activity_mine_attendance_mapview);
        mDayTextView = findViewById(R.id.activity_mine_attendance_day);
        mWeekTextView = findViewById(R.id.activity_mine_attendance_week);
        mTimeTextView = findViewById(R.id.activity_mine_attendance_time);
        mTablayout = findViewById(R.id.activity_mine_attendance_TabLayout);
        mViewPage = findViewById(R.id.activity_mine_attendance_ViewPager);

        MineAttendaceTabAdapter adapter = new MineAttendaceTabAdapter(getSupportFragmentManager());
        mViewPage.setAdapter(adapter);
        mTablayout.setupWithViewPager(mViewPage);
        mViewPage.setCurrentItem(0);

        initTime();

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setNeedNewVersionRgc(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void initTime() {

        final Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
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


        mDayTextView.setText((month + 1) + "月" + day + "日");
        mWeekTextView.setText(weekS);


        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Calendar calendar1 = Calendar.getInstance();
                        int hour = calendar1.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar1.get(Calendar.MINUTE);
                        int second = calendar1.get(Calendar.SECOND);
                        String hourS = "";
                        if (hour < 10) {
                            hourS = "0" + hour;
                        } else {
                            hourS = hour + "";
                        }
                        String minS = "";
                        if (minute < 10) {
                            minS = "0" + minute;
                        } else {
                            minS = minute + "";
                        }
                        String secS = "";
                        if (second < 10) {
                            secS = "0" + second;
                        } else {
                            secS = second + "";
                        }
                        mTimeTextView.setText(hourS + ':' + minS + ":" + secS);

                    }
                });
            }
        };

        timer.schedule(task, 0, 1000);
    }


    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        timer.cancel();

        mLocationClient.stop();
        mLocationClient = null;
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }


    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            LatLng cenpt = new LatLng(location.getLatitude(),location.getLongitude());

            MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                    .target(cenpt)
                    .zoom(18)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态
        }
    }

}
