package com.wd.winddots.desktop.list.employee.activity;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.employee.adapter.EmployeeApplyAdapter;
import com.wd.winddots.desktop.list.employee.bean.EmployeeApplyBean;
import com.wd.winddots.desktop.list.employee.bean.EmployeeListBean;
import com.wd.winddots.entity.Enterprise;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.register.activity.SearchEnterpriseActivity;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: EmployeeApplyActivity
 * Author: 郑
 * Date: 2020/11/10 11:18 AM
 * Description: 成员申请
 */
public class EmployeeApplyActivity extends CommonActivity
        implements SwipeRefreshLayout.OnRefreshListener, EmployeeApplyAdapter.EmployeeApplyAdapterListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private EmployeeApplyAdapter mAdapter;

    private List<EmployeeApplyBean.EmployeeApplyItem> dataSource = new ArrayList<>();

    private VolleyUtil mVolleyUtil;

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_employee_list);
        setTitleText(mContext.getString(R.string.employee_apply_title));

        mVolleyUtil = VolleyUtil.getInstance(this);

        mSwipeRefreshLayout = findViewById(R.id.srl);
        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new EmployeeApplyAdapter(R.layout.item_employee_apply,dataSource);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setEmployeeApplyAdapterListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        getData();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        getData();
    }
    
    private void getData(){
        final String url = Constant.APP_BASE_URL + "userApply?enterpriseId=" + SpHelper.getInstance(mContext).getEnterpriseId();
        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoading();
            List<EmployeeApplyBean.EmployeeApplyItem> enterpriseList = JSON.parseArray(response, EmployeeApplyBean.EmployeeApplyItem.class);
            dataSource.addAll(enterpriseList);
            mAdapter.notifyDataSetChanged();
        }, volleyError -> {
            hideLoading();
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }


    /*
    * 点击同意
    * */
    @Override
    public void agreeDidClick(EmployeeApplyBean.EmployeeApplyItem item) {


    }

    /*
    * 点击拒绝
    * */
    @Override
    public void refuseDidClick(EmployeeApplyBean.EmployeeApplyItem item) {

    }

    private class HandleBean{
        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
