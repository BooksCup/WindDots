package com.wd.winddots.fast.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.fast.adapter.MineAttendanceSignAdapter;
import com.wd.winddots.fast.bean.MineAttendanceSignBean;
import com.wd.winddots.fast.presenter.impl.MineAttendanceSignPresenterImpl;
import com.wd.winddots.fast.presenter.view.MineAttendanceSignView;
import com.wd.winddots.fast.view.MineAttendanceSignHeader;
import com.wd.winddots.fast.view.MineAttendanceSignPop;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: MineAttendanceSiginFragment
 * Author: 郑
 * Date: 2020/5/26 3:28 PM
 * Description:
 */
public class MineAttendanceSiginFragment extends BaseFragment<MineAttendanceSignView, MineAttendanceSignPresenterImpl>
        implements MineAttendanceSignView, BaseQuickAdapter.OnItemClickListener ,View.OnClickListener ,
        MineAttendanceSignPop.MineAttendanceSignPopActionListener {


    private RecyclerView mRecyclerView;
    private MineAttendanceSignAdapter mAdapter;
    private List<MineAttendanceSignBean.AttendRecordList> mDataSource = new ArrayList<>();
    private MineAttendanceSignBean mData;

    private TextView mSignTextView;
    private TextView mOutWorkTextView;
    private TextView mOutTextView;

    private LinearLayout mBtnShortCut;
    private PopupWindow mPopupWindow;
    private MineAttendanceSignPop mPopView;


    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    private BDLocation mLocation;
    private String mLocationAddress;

    private String mRecordType;


    public static BaseFragment newInstance() {
        MineAttendanceSiginFragment fragment = new MineAttendanceSiginFragment();
        return fragment;
    }

    @Override
    public MineAttendanceSignPresenterImpl initPresenter() {
        return new MineAttendanceSignPresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_mine_attendance_sign;
    }

    @Override
    public void initView() {


        mLocationClient = new LocationClient(mContext);
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



        mBtnShortCut = mView.findViewById(R.id.fragment_mine_attendance_sign_body);
        mRecyclerView = mView.findViewById(R.id.fragment_mine_attendance_sign_rlist);
        mAdapter = new MineAttendanceSignAdapter(R.layout.item_attendance_sign,mDataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        MineAttendanceSignHeader header = new MineAttendanceSignHeader(mContext);
        mAdapter.setHeaderView(header);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mSignTextView = mView.findViewById(R.id.fragment_mine_attendance_sign_signbBtn);
        mOutWorkTextView = mView.findViewById(R.id.fragment_mine_attendance_sign_outWork);
        mOutTextView = mView.findViewById(R.id.fragment_mine_attendance_sign_signOut);
        mSignTextView.setOnClickListener(this);
        mOutWorkTextView.setOnClickListener(this);
        mOutTextView.setOnClickListener(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        presenter.getAttendRecord(SpHelper.getInstance(mContext).getUserId());
    }

    /*
    * 获取当前考勤
    * */
    @Override
    public void getAttendRecordSuccess(MineAttendanceSignBean bean) {

        mData = bean;

        if ("1".equals(bean.getWorkStatus())){
            mSignTextView.setText("下班");
        }else {
            mSignTextView.setText("上班");
        }

        if ("3".equals(bean.getOutWorkStatus())){
            mOutWorkTextView.setText("回勤");
        }else {
            mOutWorkTextView.setText("外勤");
        }

        if ("5".equals(bean.getOutStatus())){
            mOutTextView.setText("回岗");
        }else {
            mOutTextView.setText("离岗");
        }
        mDataSource.clear();
        mDataSource.addAll(bean.getAttendRecordList());
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void getAttendRecordError() {
        showToast("获取考勤失败,请稍后重试");
    }

    @Override
    public void getAttendRecordCompleted() {

    }


    /*
    *
    * 打卡
    * */
    @Override
    public void addAttendRecordSuccess() {
        showToast("打卡成功");
        mPopupWindow.dismiss();
        presenter.getAttendRecord(SpHelper.getInstance(mContext).getUserId());
    }

    @Override
    public void addAttendRecordError() {
        mPopupWindow.dismiss();
        showToast("打卡失败,请稍后重试");
    }

    @Override
    public void addAttendRecordCompleted() {

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onClick(View view) {

        if (mLocation == null){
            showToast("定位失败,请稍后重试");
            return;
        }

        if (mData == null){
            showToast("获取考勤失败,请退出重试");
            return;
        }


        switch (view.getId()){
            case R.id.fragment_mine_attendance_sign_signbBtn:
                onSign();
                break;
            case R.id.fragment_mine_attendance_sign_outWork:
                onOutWork();
                break;
            case R.id.fragment_mine_attendance_sign_signOut:
                onOut();
                break;
        }
    }

    private void onSign(){
        MineAttendanceSignPop popView = new MineAttendanceSignPop(mContext); //LayoutInflater.from(mContext).inflate(R.layout.pop_shortcut, null, false);



        mPopView = popView;
        if ("1".equals(mData.getWorkStatus())){
            popView.setTitleS("下班");
            mRecordType = "2";
        }else {
            popView.setTitleS("上班");
            mRecordType = "1";
        }
        popView.setLocation(mLocationAddress);
        popView.setMineAttendanceSignPopActionListener(this);

        //CalendarRangeView popView = new CalendarRangeView(mContext);
        mPopupWindow  = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(mBtnShortCut, Gravity.TOP| Gravity.LEFT,0,0);
    }


    private void onOutWork(){
        MineAttendanceSignPop popView = new MineAttendanceSignPop(mContext);
        mPopView = popView;
        if ("3".equals(mData.getOutWorkStatus())){
            popView.setTitleS("回勤");
            mRecordType = "4";
        }else {
            popView.setTitleS("外勤");
            mRecordType = "3";
        }
        popView.setLocation(mLocationAddress);

        popView.setMineAttendanceSignPopActionListener(this);
        //CalendarRangeView popView = new CalendarRangeView(mContext);
        mPopupWindow  = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(mBtnShortCut, Gravity.TOP| Gravity.LEFT,0,0);
    }

    private void onOut(){
        MineAttendanceSignPop popView = new MineAttendanceSignPop(mContext);
        mPopView = popView;
        if ("5".equals(mData.getOutStatus())){
            popView.setTitleS("回岗");
            mRecordType = "6";
        }else {
            popView.setTitleS("离岗");
            mRecordType = "5";
        }

        popView.setLocation(mLocationAddress);
        popView.setMineAttendanceSignPopActionListener(this);

        //CalendarRangeView popView = new CalendarRangeView(mContext);
        mPopupWindow  = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(mBtnShortCut, Gravity.TOP| Gravity.LEFT,0,0);
    }

    @Override
    public void onDestroy() {

        mLocationClient.stop();
        mLocationClient = null;

        super.onDestroy();
    }

    @Override
    public void commitBtnDidClick() {

        String recordRemark = mPopView.getText();
        recordRemark = Utils.nullOrEmpty(recordRemark);
        presenter.addAttendRecord(
                SpHelper.getInstance(mContext).getUserId(),
                mRecordType,
                recordRemark,
                "",
                mLocationAddress,
                mLocation.getLatitude(),
                mLocation.getLongitude()
        );



    }


    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {



            String province = Utils.nullOrEmpty(location.getProvince());    //获取省份
            String city = Utils.nullOrEmpty(location.getCity()) ;    //获取城市
            String district = Utils.nullOrEmpty(location.getDistrict());    //获取区县
            String street = Utils.nullOrEmpty(location.getStreet());    //获取街道信息
            String town = Utils.nullOrEmpty(location.getTown());    //获取乡镇信息
            String num = Utils.nullOrEmpty(location.getStreetNumber());

            String addrS = province + city + district + street + town + num;
            mLocationAddress = addrS;
            mLocation = location;



        }
    }


}
