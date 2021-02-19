package com.wd.winddots.desktop.list.card.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.desktop.activity.MeActivity;
import com.wd.winddots.desktop.list.card.adapter.SearchUserAdapter;
import com.wd.winddots.desktop.list.card.bean.SearchUserBean;
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
 * FileName: SearchUserActivity
 * Author: 郑
 * Date: 2020/6/22 9:53 AM
 * Description: 搜索平台用户
 */
public class SearchUserActivity extends CommonActivity implements BaseQuickAdapter.OnItemClickListener {


    public CompositeSubscription compositeSubscription;
    public ElseDataManager dataManager;

    private EditText mEditText;
    private RecyclerView mRecyclerView;
    private SearchUserAdapter mAdapter;

    private List<SearchUserBean> mDataSource = new ArrayList<>();

    @Override
    public BasePresenter initPresenter() {
        return new  BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();

        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();

        addBadyView(R.layout.activity_search_user);
        setTitleText(mContext.getString(R.string.search_user_title));

        mRecyclerView = findViewById(R.id.rlist);
        mEditText = findViewById(R.id.et_searchkey);
        TextView searchBtn = findViewById(R.id.tv_searchBtn);

        mAdapter = new SearchUserAdapter(R.layout.item_search_user,mDataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();




        searchBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_searchBtn:
                onSearchBtnDidClick();
        }
    }

    private void onSearchBtnDidClick(){
        getData();
    }

    private void getData(){



        String searchKey = mEditText.getText().toString().trim();

        if (StringUtils.isNullOrEmpty(searchKey)){
            showToast(mContext.getString(R.string.toast_search_empty));
            return;
        }

        mEditText.clearFocus();

        compositeSubscription.add(dataManager.searchNewUserList("01", searchKey,1,5).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(mContext.getString(R.string.toast_loading_error));
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666",s);
                        Gson gson = new Gson();
                        List<String> list = gson.fromJson(s, new TypeToken<List<String>>() {
                                                    }.getType());
                        mDataSource.clear();
                        for (int i = 0;i < list.size();i++){
                            SearchUserBean bean = gson.fromJson(list.get(i),SearchUserBean.class);
                            mDataSource.add(bean);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }));
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SearchUserBean userBean = mDataSource.get(position);

        if (SpHelper.getInstance(mContext).getUserId().equals(userBean.getId())){
            Intent intent = new Intent(mContext, MeActivity.class);
            //intent.putExtra("id",userBean.getId());
            startActivity(intent);
        }else {
            Intent intent = new Intent(mContext, UserInfoActivity.class);
            intent.putExtra("id",userBean.getId());
            startActivity(intent);
        }

    }
}
