package com.wd.winddots.business.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseActivity;
import com.wd.winddots.bean.resp.BusinessBean;
import com.wd.winddots.business.adapter.BusinessTypeBean;
import com.wd.winddots.business.adapter.RateCurrencyAdapter;
import com.wd.winddots.business.bean.RateCurrencyBean;
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
 * Description: 汇率
 */
public class RateCurrencyActivity extends BaseActivity implements  View.OnClickListener{

    private CompositeSubscription compositeSubscription;
    private BusinessDataManager dataManager;

    private List<RateCurrencyBean.RateCurrencyItem> mDataSource = new ArrayList<>();
    private List<SelectBean> selectBeanList = new ArrayList<>();
    private String mCurrency;

    private BusinessBean.MaterialsPrice mBusinessItem;

    private RecyclerView mRecyclerView;
    private RateCurrencyAdapter mAdapter;
    private SelectView mSelectView;

    private AlertDialog alertDialog3;

    String[] items = null;//{"多选1", "多选2", "多选3", "多选4", "多选5", "多选6", "多选7", "多选8"};
    List<ItemSelect> itemM = new ArrayList<>();
    private String mDate;
    private int mYear;
    private int mMounth;
    private int mDay;
    private List<Map> mHistoryMap = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_rate_currency;
    }

    @Override
    public void initView() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new BusinessDataManager();

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        if (data != null) {
            mBusinessItem = gson.fromJson(data, BusinessBean.MaterialsPrice.class);
        }

        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RateCurrencyAdapter(R.layout.item_currency_rate, mDataSource);
        mRecyclerView.setAdapter(mAdapter);
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
                showMutilAlertDialog();
            }
        });

        ImageView calendarIv = findViewById(R.id.iv_calendar);
        calendarIv.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMounth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        String mounthS = "";
        if (mMounth < 10){
            mounthS = "0" + (mMounth + 1);
        }else {
            mounthS = "" + (mMounth + 1);
        }
        String dayS = "";
        if (mDay < 10){
            dayS = "0" + mDay;
        }else {
            dayS = "" + mDay;
        }
        mDate = mYear + "-" + mounthS + "-" + dayS;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_calendar:
                onCalendarDidClick();
                break;
        }
    }

    private void onCalendarDidClick(){
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
                String date = year + "-" + monthS + "-" + dayS;
                mYear = year;
                mMounth = month-1;
                mDay = day;
                mDate = date;
                showLoading();
                getHistoryData();
            }


        }, mYear, mMounth, mDay);//2013:初始年份，2：初始月份-1 ，1：初始日期
        dp.show();
    }

    @Override
    public void initData() {
        getPriceType();
    }


    private void getPriceType() {
        showLoading();
        compositeSubscription.add(dataManager.getPriceType(SpHelper.getInstance(mContext).getUserId(), "1").
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                hideLoading();
//                                Log.e("net666", String.valueOf(e));
//                                mDataSource.clear();
//                                mAdapter.notifyDataSetChanged();
//                                showToast(mContext.getString(R.string.toast_loading_error));
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
                                if (selectTypes.size() == 0){
                                    Map map = new HashMap();
                                    map.put("typeName", typeItems.get(0).getPriceType());
                                    selectTypes.add(map);
                                }
                                mHistoryMap.clear();
                                mHistoryMap.addAll(selectTypes);
                                getData();
//                        mDataSource.clear();
//                        mDataSource.addAll(bean.getData());
//                        mAdapter.notifyDataSetChanged();
                            }
                        })
        );
    }

    private void getData() {

        Gson gson = new Gson();
        String bodyString = gson.toJson(mHistoryMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
        compositeSubscription.add(dataManager.getRateCurrency(requestBody).
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
                        hideLoading();
                        Log.e("net666", s);
                        Gson gson = new Gson();
                        RateCurrencyBean bean = gson.fromJson(s, RateCurrencyBean.class);
                        mDataSource.clear();
                        mDataSource.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                })
        );
    }


    private void showMutilAlertDialog() {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择币种");

        boolean[] itemSS = new boolean[itemM.size()];
        for (int m = 0; m < itemM.size(); m++) {
            itemSS[m] = itemM.get(m).isSelect();
        }


        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, itemSS, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (isChecked) {
                    // Toast.makeText(MainActivity.this, "选择" + items[i], Toast.LENGTH_SHORT).show();
                    //showToast("选择" + items[i]);
                    itemM.get(i).setSelect(true);
                } else {
                    // Toast.makeText(MainActivity.this, "取消选择" + items[i], Toast.LENGTH_SHORT).show();
                    //showToast("取消选择" + items[i]);
                    itemM.get(i).setSelect(false);
                }
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog3.dismiss();
//                for (int m = 0; m < itemM.size(); m++) {
//                    Log.e("net666", String.valueOf(itemM.get(m).isSelect()) + "-----" + m);
//                }
                addType();

            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog3.dismiss();
            }
        });


        alertDialog3 = alertBuilder.create();
        alertDialog3.show();

    }

    private void addType() {
        final List<Map> selectType = new ArrayList<>();
        for (int i = 0; i < itemM.size(); i++) {
            if (itemM.get(i).isSelect()) {
                Map map = new HashMap();
                map.put("typeName", items[i]);
                selectType.add(map);
            }
        }
        if (selectType.size() == 0) {
            showToast(mContext.getString(R.string.toast_currency_empty));
            return;
        }
        mHistoryMap.clear();
        mHistoryMap.addAll(selectType);
        Gson gson = new Gson();
        String bodyString = gson.toJson(selectType);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
        compositeSubscription.add(dataManager.addBusinessTypeList(SpHelper.getInstance(mContext).getUserId(), "1", requestBody).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("net666", String.valueOf(e));
//                                mDataSource.clear();
//                                mAdapter.notifyDataSetChanged();
//                                showToast(mContext.getString(R.string.toast_loading_error));
                            }

                            @Override
                            public void onNext(String s) {
                                Log.e("net666", s);
                                showLoading();
                                getHistoryData();
                            }
                        })
        );
    }


    private void getHistoryData() {
        Gson gson = new Gson();
        String bodyString = gson.toJson(mHistoryMap);
        Log.e("net666",bodyString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
        compositeSubscription.add(dataManager.getRateHistoryPrice(mDate,requestBody).
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
                        hideLoading();
                        Log.e("net666", s);
                        Gson gson = new Gson();
                        RateCurrencyBean bean = gson.fromJson(s, RateCurrencyBean.class);
                        if (bean.getCode() != 0){
                            showToast(mContext.getString(R.string.toast_loading_error));
                            return;
                        }
                        if (bean.getData() == null || bean.getData().size() == 0){
                            mDataSource.clear();
                            mDataSource.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();
                            showToast(mContext.getString(R.string.toast_rate_history_empty));
                            return;
                        }
                        mDataSource.clear();
                        mDataSource.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                })
        );
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



