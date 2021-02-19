package com.wd.winddots.desktop.list.card.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.desktop.list.card.adapter.FriendSearchAdapter;
import com.wd.winddots.desktop.list.card.bean.FriendSrarchBean;
import com.wd.winddots.desktop.list.card.bean.SearchUserBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.SpHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: FriendSearchActivity
 * Author: 郑
 * Date: 2020/8/28 11:33 AM
 * Description:
 */
public class FriendSearchActivity extends CommonActivity implements BaseQuickAdapter.OnItemClickListener {


    public CompositeSubscription compositeSubscription;
    public ElseDataManager dataManager;


    private EditText mEditText;
    private RecyclerView mRecyclerView;
    private FriendSearchAdapter mAdapter;
    private List<FriendSrarchBean.FriendSrarchItem> mDataSource = new ArrayList<>();


    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();

        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();


        addBadyView(R.layout.activity_friend_search);
        setTitleText(mContext.getString(R.string.friend_search_title));

        mRecyclerView = findViewById(R.id.rlist);
        mEditText = findViewById(R.id.et_searchkey);
        TextView searchBtn = findViewById(R.id.tv_searchBtn);
//
//        mDataSource.add(new FriendSrarchBean.FriendSrarchItem());
//        mDataSource.add(new FriendSrarchBean.FriendSrarchItem());
//        mDataSource.add(new FriendSrarchBean.FriendSrarchItem());
//        mDataSource.add(new FriendSrarchBean.FriendSrarchItem());

        mAdapter = new FriendSearchAdapter(R.layout.item_friend_search,mDataSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();

        ImageView clearIv = findViewById(R.id.iv_clear);
        clearIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
            }
        });

        TextView cancelTv = findViewById(R.id.tv_cancel);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @Override
    public void initListener() {
        super.initListener();

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void getData(){

        String userId = SpHelper.getInstance(mContext).getUserId();
        String searchKey = mEditText.getText().toString().trim();
        mAdapter.setSearchKey(searchKey);
        compositeSubscription.add(dataManager.friendSearch(userId,searchKey,"01",1,10).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666friendSearch", String.valueOf(e));
                        showToast(mContext.getString(R.string.toast_loading_error));
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666friendSearch",s);
                        Gson gson = new Gson();
                        List<String> list = gson.fromJson(s, new TypeToken<List<String>>() {
                        }.getType());
                        mDataSource.clear();
                        for (int i = 0;i < list.size();i++){
                            FriendSrarchBean.FriendSrarchItem bean = gson.fromJson(list.get(i),FriendSrarchBean.FriendSrarchItem.class);
                            mDataSource.add(bean);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mContext, UserInfoActivity.class);
        intent.putExtra("id",mDataSource.get(position).getUserId());
        startActivity(intent);
    }
}
