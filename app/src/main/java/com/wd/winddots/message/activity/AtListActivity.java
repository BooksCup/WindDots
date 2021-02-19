package com.wd.winddots.message.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: AtListActivity
 * Author: 郑
 * Date: 2020/10/7 10:42 AM
 * Description: @某人
 */
public class AtListActivity extends CommonActivity implements BaseQuickAdapter.OnItemClickListener {


    private RecyclerView mRecyclerView;
    private AtUserAdapter mAdapter;
    private List<AtUser> mDataSource = new ArrayList<>();


    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }



    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_atlist);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if (!StringUtils.isNullOrEmpty(data)){
            Gson gson = new Gson();
            List<AtUser> list = gson.fromJson(data, new TypeToken<List<AtUser>>() {
                                        }.getType());
            mDataSource.addAll(list);
        }

        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new AtUserAdapter(R.layout.item_user_select, mDataSource);
        mRecyclerView.setAdapter(mAdapter);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(this);
    }
//1911.99
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent();
        intent.putExtra("name",mDataSource.get(position).getName());
        intent.putExtra("id",mDataSource.get(position).getId());
        setResult(100,intent);
        finish();
    }









    private static class AtUserAdapter extends BaseQuickAdapter<AtUser, BaseViewHolder> {

        public AtUserAdapter(int layoutResId, @Nullable List<AtUser> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AtUser item) {
            ImageView icon = helper.getView(R.id.iv_avatar);
            ImageView visitingCardIcon = helper.getView(R.id.visiting_card_icon);
            visitingCardIcon.setVisibility(View.GONE);
            GlideApp.with(mContext).load(item.getAvatar() + Utils.OSSImageSize(100)).into(icon);
            helper.setText(R.id.tv_name,item.getName());
        }
    }


    private static class AtUser{

        private String name;
        private String id;
        private String avatar;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }





}
