package com.wd.winddots.message.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.adapter.UserSelectAdapter;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.desktop.list.card.bean.FriendListBean;
import com.wd.winddots.desktop.list.card.bean.FriendSrarchBean;
import com.wd.winddots.message.adapter.AddGroupSearchAdapter;
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
 * FileName: AddGroupSearchActivity
 * Author: 郑
 * Date: 2020/9/23 2:22 PM
 * Description: 添加群聊搜索好友
 */
public class AddGroupSearchActivity extends CommonActivity implements BaseQuickAdapter.OnItemClickListener {

    public CompositeSubscription compositeSubscription;
    public ElseDataManager dataManager;


    private EditText mEditText;
    private RecyclerView mRecyclerView;
    private AddGroupSearchAdapter mAdapter;
    private List<FriendSrarchBean.FriendSrarchItem> mDataSource = new ArrayList<>();
    private List<String> disableList = new ArrayList<>();
    private List<String> selectList = new ArrayList<>();

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();

        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();


        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        List<String> list = gson.fromJson(data, new TypeToken<List<String>>() {
                                    }.getType());
        if (list !=null && list.size() > 0){
            disableList.addAll(list);
        }

        addBadyView(R.layout.activity_friend_search);
        setTitleText(mContext.getString(R.string.friend_search_title));

        mRecyclerView = findViewById(R.id.rlist);
        mEditText = findViewById(R.id.et_searchkey);


        mAdapter = new AddGroupSearchAdapter(R.layout.item_user_select,mDataSource);
        mAdapter.activity = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();

        //showSoftInput();

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

        LinearLayout saveBtn = findViewById(R.id.ll_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String jsonS = gson.toJson(selectList);
                Intent intent1 = new Intent();
                intent1.putExtra("data",jsonS);
                setResult(10000,intent1);
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
                if (!StringUtils.isNullOrEmpty(mEditText.getText().toString().trim())){
                    getData();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void getData(){

        String userId = SpHelper.getInstance(mContext).getUserId();
        String searchKey = mEditText.getText().toString().trim();
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
                            for (int m = 0;m < disableList.size();m++){
                                String id = disableList.get(m);
                                if (bean.getUserId().equals(id)){
                                    bean.setDisable(true);
                                }
                            }
                            for (int m = 0;m < selectList.size();m++){
                                String id = selectList.get(m);
                                if (bean.getUserId().equals(id)){
                                    bean.setSelect(true);
                                }
                            }
                            mDataSource.add(bean);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        Intent intent = new Intent(mContext, UserInfoActivity.class);
//        intent.putExtra("id",mDataSource.get(position).getUserId());
//        startActivity(intent);

        FriendSrarchBean.FriendSrarchItem item = mDataSource.get(position);
        if (item.isDisable()){
            return;
        }

        if (item.isSelect()){
            selectList.remove(item.getUserId());
            item.setSelect(false);
        }else {
            item.setSelect(true);
            selectList.add(item.getUserId());
        }

        mAdapter.notifyItemChanged(position);
    }

}
