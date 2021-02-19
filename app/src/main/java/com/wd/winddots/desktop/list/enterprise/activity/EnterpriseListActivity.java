package com.wd.winddots.desktop.list.enterprise.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.desktop.list.enterprise.adapter.EnterpriseListAdapter;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseListBean;
import com.wd.winddots.desktop.list.order.bean.OrderListBean;
import com.wd.winddots.desktop.list.quote.bean.QuoteListBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.enterprise.EnterpeiseDataManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: EnterpriseListActivity
 * Author: 郑
 * Date: 2020/12/22 10:17 AM
 * Description: 企业列表
 */
public class EnterpriseListActivity extends CommonActivity implements BaseQuickAdapter.OnItemClickListener {


    private CompositeSubscription compositeSubscription;
    private EnterpeiseDataManager dataManager;


    private EditText mEditText;
    private RecyclerView mRecyclerView;

    private EnterpriseListAdapter mAdapter;
    private List<EnterpriseListBean.EnterpriseItem> mDataSource = new ArrayList<>();

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }



    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_enterprise_list);


        compositeSubscription = new CompositeSubscription();
        dataManager = new EnterpeiseDataManager();

        setTitleText(mContext.getString(R.string.enterprise_title));

        mRecyclerView = findViewById(R.id.rlist);
        mEditText = findViewById(R.id.et_searchkey);
        TextView searchBtn = findViewById(R.id.tv_searchBtn);

        mEditText.setOnKeyListener(onKeyListener);


        mAdapter = new EnterpriseListAdapter(R.layout.item_enterprise_list, mDataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        searchBtn.setOnClickListener(this);
        //getData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_searchBtn:
                onSearchBtnDidClick();
        }
    }

    private void onSearchBtnDidClick() {
        hideKeyboard();
        getData();
    }


    private void getData() {

        String searckKey = mEditText.getText().toString().trim();

        if (StringUtils.isNullOrEmpty(searckKey)){
            showToast("请先输入关键字");
            return;
        }


        compositeSubscription.add(dataManager.enterpriseSearchList(searckKey).
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
                        //mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("net666", s);

                        Gson gson = new Gson();
                        List<EnterpriseListBean.EnterpriseItem> list = gson.fromJson(s, new TypeToken<List<EnterpriseListBean.EnterpriseItem>>() {
                        }.getType());
                        mAdapter.searchKey = mEditText.getText().toString().trim();
                        mDataSource.clear();
                        mDataSource.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                })
        );


//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//            AssetManager assetManager = mContext.getAssets();
//            BufferedReader bf = new BufferedReader(new InputStreamReader(
//                    assetManager.open("enterprise.json")));
//            String line;
//            while ((line = bf.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            ;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String json = stringBuilder.toString();
//        Gson gson = new Gson();
//        List<EnterpriseListBean.EnterpriseItem> list = gson.fromJson(json, new TypeToken<List<EnterpriseListBean.EnterpriseItem>>() {
//        }.getType());
//        mAdapter.searchKey = mEditText.getText().toString().trim();
//        mDataSource.clear();
//        mDataSource.addAll(list);
//        mAdapter.notifyDataSetChanged();


    }


    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                hideKeyboard();
                getData();
                return true;
            }
            return false;
        }
    };

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mContext, EnterpriseDetailActivity.class);
        Gson gson = new Gson();
        EnterpriseListBean.EnterpriseItem item = mDataSource.get(position);
        String jsonS = gson.toJson(item);
        intent.putExtra("data", jsonS);
        startActivity(intent);
    }
}
