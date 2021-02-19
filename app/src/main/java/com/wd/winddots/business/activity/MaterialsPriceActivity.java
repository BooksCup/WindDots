package com.wd.winddots.business.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.bean.resp.BusinessBean;
import com.wd.winddots.business.adapter.BusinessTypeBean;
import com.wd.winddots.business.adapter.MaterialsPriceAdapter;
import com.wd.winddots.net.business.BusinessDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.view.selector.SelectBean;
import com.wd.winddots.view.selector.SelectView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: MaterialsPriceActivity
 * Author: 郑
 * Date: 2020/8/19 9:48 AM
 * Description: 原材料价格
 */
public class MaterialsPriceActivity extends BaseActivity implements SelectView.SelectViewOnselectListener, View.OnClickListener, BaseQuickAdapter.OnItemClickListener {


    private AlertDialog alertDialog3;

    private CompositeSubscription compositeSubscription;
    private BusinessDataManager dataManager;


    private List<BusinessBean.MaterialsPrice> mDataSource = new ArrayList<>();
    private List<SelectBean> selectBeanList = new ArrayList<>();
    private String mMaterialsPriceType;
    private int mSelectIndex;

    private BusinessBean.MaterialsPrice mBusinessItem;

    private RecyclerView mRecyclerView;
    private MaterialsPriceAdapter mAdapter;
    private SelectView mSelectView;

    String[] items = null;//{"多选1", "多选2", "多选3", "多选4", "多选5", "多选6", "多选7", "多选8"};
    List<ItemSelect> itemM = new ArrayList<>();
    private String mDate;
    private int mYear;
    private int mMounth;
    private int mDay;
    private List<Map> mHistoryMap = new ArrayList<>();

    private TextView headerTv1;
    private TextView headerTv2;

    private int selectSwitch;
    private String type = "原料";
    private String mName = "";


    @Override
    public int getContentView() {
        return R.layout.activity_materials_price;
    }

    @Override
    public void initView() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new BusinessDataManager();

        Intent intent = getIntent();
        String name = intent.getStringExtra("data");
//        Gson gson = new Gson();
//        if (data != null) {
//            mBusinessItem = gson.fromJson(data, BusinessBean.MaterialsPrice.class);
//        }

        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MaterialsPriceAdapter(R.layout.item_goods_detail_stock_item, mDataSource);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        headerTv1 = findViewById(R.id.tv_header1);
        headerTv2 = findViewById(R.id.tv_header2);
        headerTv1.setOnClickListener(this);
        headerTv2.setOnClickListener(this);
        selectSwitch = R.id.tv_header1;

        ImageView backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView settingIv = findViewById(R.id.iv_setting);
        settingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                if (items == null) {
                    return;
                }
            }
        });

        ImageView calendarIv = findViewById(R.id.iv_calendar);
        calendarIv.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMounth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String mounthS = "";
        if (mMounth < 10) {
            mounthS = "0" + (mMounth + 1);
        } else {
            mounthS = "" + (mMounth + 1);
        }
        String dayS = "";
        if (mDay < 10) {
            dayS = "0" + mDay;
        } else {
            dayS = "" + mDay;
        }
        mDate = mYear + "-" + mounthS + "-" + dayS;
        if (!StringUtils.isNullOrEmpty(name)){
            mName = name;
            TextView textView = findViewById(R.id.tv_title);
            textView.setText(name);
        }

    }

    @Override
    public void initListener() {
        // mSelectView.setSelectViewOnselectListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_calendar:
                onCalendarDidClick();
                break;
            case R.id.tv_header1:
                switchHeaderTv(R.id.tv_header1);
                break;
            case R.id.tv_header2:
                switchHeaderTv(R.id.tv_header2);
                break;
        }
    }

    private void switchHeaderTv(int id){
        if (id == selectSwitch){
            return;
        }
        selectSwitch = id;
        if (id == R.id.tv_header2){

            headerTv2.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            headerTv1.setTextColor(Color.parseColor("#FF888888"));
            type = "化纤";
            getData();
        }else {
            headerTv1.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            headerTv2.setTextColor(Color.parseColor("#FF888888"));
            type = "原料";
            getData();
        }
    }

    private void onCalendarDidClick() {

        DatePickerDialog dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                long maxDate = datePicker.getMaxDate();//日历最大能设置的时间的毫秒值
                int year = datePicker.getYear();//年
                int month = datePicker.getMonth() + 1;//月-1
                int day = datePicker.getDayOfMonth();//日*
                //iyear:年，monthOfYear:月-1，dayOfMonth:日

                String monthS;
                String dayS;
                if (month < 10) {
                    monthS = "0" + month;
                } else {
                    monthS = "" + month;
                }

                if (day < 10) {
                    dayS = "0" + day;
                } else {
                    dayS = "" + day;
                }
                String date = year + "-" + monthS + '-' + dayS;
                mYear = year;
                mMounth = month - 1;
                mDay = day;
                mDate = date;
                showLoading();

            }


        }, mYear, mMounth, mDay);//2013:初始年份，2：初始月份-1 ，1：初始日期

        dp.show();
    }

    @Override
    public void initData() {
        //getPriceType();
        getData();
    }

    @Override
    public void onselect(int position, SelectView view) {
        mMaterialsPriceType = selectBeanList.get(position).getName();
        //getData();
    }


    private void getPriceType() {
        showLoading();
        compositeSubscription.add(dataManager.getPriceType(SpHelper.getInstance(mContext).getUserId(), "0").
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666", s);
                        Gson gson = new Gson();
                        BusinessTypeBean bean = gson.fromJson(s, BusinessTypeBean.class);
                        List<BusinessTypeBean.BusinessTypeItem> typeItems = bean.getData();
                        if (bean.getCode() != 0 || typeItems == null || typeItems.size() == 0) {
                            return;
                        }
                        items = new String[bean.getData().size()];
                        itemM.clear();
                        List<Map> selectTypes = new ArrayList<>();
                        for (int i = 0; i < typeItems.size(); i++) {
                            BusinessTypeBean.BusinessTypeItem businessTypeItem = typeItems.get(i);
                            items[i] = businessTypeItem.getPriceType();
                            if ("1".equals(businessTypeItem.getStatus())) {
                                itemM.add(new ItemSelect(true));
                                Map map = new HashMap();
                                map.put("typeName", businessTypeItem.getPriceType());
                                selectTypes.add(map);
                            } else {
                                itemM.add(new ItemSelect(false));
                            }

                        }
                        if (selectTypes.size() == 0) {
                            Map map = new HashMap();
                            map.put("typeName", typeItems.get(0).getPriceType());
                            selectTypes.add(map);
                        }
                        showLoading();
                        mHistoryMap.clear();
                        mHistoryMap.addAll(selectTypes);
                        getData();
                    }
                })
        );
    }


    private void getData() {
        Log.e("net666", "getData" + mDate);
        Gson gson = new Gson();
        String bodyString = gson.toJson(mHistoryMap);

        String date;
        if (StringUtils.isNullOrEmpty(mName)){
            date = mDate;
        }else {
            date = "";
        }

        compositeSubscription.add(dataManager.getMaterialsPrice(date,"",mName).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        Log.e("net666", String.valueOf(e));
                        mDataSource.clear();
                        mAdapter.notifyDataSetChanged();
                        showToast(mContext.getString(R.string.toast_loading_error));
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666", s);
                       Gson gson = new Gson();
                       List<BusinessBean.MaterialsPrice> list = gson.fromJson(s, new TypeToken<List<BusinessBean.MaterialsPrice>>() {
                                                   }.getType());
                        mDataSource.clear();
                        mDataSource.addAll(list);
                        mAdapter.notifyDataSetChanged();
                        hideLoading();
                    }
                })
        );
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mContext, MaterialsPriceActivity.class);
//                Gson gson = new Gson();
//                String jsonS = gson.toJson(item);
        intent.putExtra("data", mDataSource.get(position).getName());
        startActivity(intent);
    }


    private static class ItemSelect {

        public ItemSelect() {
            super();
        }

        public ItemSelect(boolean select) {
            super();
            this.select = select;
        }

        private boolean select;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }
    }
}



