package com.wd.winddots.fast.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.fast.adapter.RelationEnterpriseAdapter;
import com.wd.winddots.fast.bean.RelationEnterpriseBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.SpHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: RelationEnterprise
 * Author: 郑
 * Date: 2020/5/13 1:37 PM
 * Description: 往来单位账户
 */
public class RelationEnterprise extends CommonActivity implements
        RelationEnterpriseAdapter.OnAccountSelectListener,
        BaseQuickAdapter.OnItemClickListener {


    private List<RelationEnterpriseBean.Enterprise> mDataSource = new ArrayList<>();

    public CompositeSubscription compositeSubscription;
    public ElseDataManager dataManager;
    private RecyclerView mRecyclerView;
    private RelationEnterpriseAdapter mAdapter;

    private boolean mAccountSelect = false;


    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }


    @Override
    public void initView() {
        super.initView();

        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
        addBadyView(R.layout.activity_mine_claiming_enterprise);

        setTitleText("往来单位");

        Intent intent = getIntent();
        mAccountSelect = intent.getBooleanExtra("accountSelect",false);

        mRecyclerView = findViewById(R.id.activity_mine_claiming_enterprise_rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new RelationEnterpriseAdapter(R.layout.item_mine_claiming_relationenterpirse,mDataSource,mContext,mAccountSelect);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnAccountSelectListener(this);
        if (!mAccountSelect){
            mAdapter.setOnItemClickListener(this);
        }


        final EditText editText = findViewById(R.id.activity_mine_claiming_enterprise_edittext);
        TextView searchBtn = findViewById(R.id.activity_mine_claiming_enterprise_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchKey = editText.getText().toString().trim();
                if (StringUtils.isNullOrEmpty(searchKey)){
                    searchKey = "";
                }
                hideKeyboard();
                showLoading();
                getData(searchKey);
            }
        });

    }


    @Override
    public void initData() {
        super.initData();
        getData("");
    }

    private void getData(String searchKey){
        compositeSubscription.add(dataManager.getRelationEnterprise(SpHelper.getInstance(mContext).getEnterpriseId(),searchKey).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            Thread.sleep(1000);
                        }catch (Exception error){
                        }
                        showToast("加载失败,请稍后重试");
                        hideLoading();
                    }

                    @Override
                    public void onNext(String s) {


                        hideLoading();
                        Log.e("ssssss",s);
                        Gson gson = new Gson();
                        RelationEnterpriseBean bean = gson.fromJson(s,RelationEnterpriseBean.class);
                        mDataSource.clear();
                        mDataSource.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                })
                );
    }

    @Override
    public void onAccountSelect(RelationEnterpriseBean.Enterprise enterprise) {
        Gson gson = new Gson();
        String data = gson.toJson(enterprise);
        Intent intent = new Intent();
        intent.putExtra("data", data);
        setResult(300, intent);
        finish();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Gson gson = new Gson();
        String jsonS = gson.toJson(mDataSource.get(position));
        Intent intent = new Intent();
        intent.putExtra("data", jsonS);
        setResult(300, intent);
        finish();
    }
}
