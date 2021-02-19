package com.wd.winddots.message.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.bean.resp.DiscussChatBean;
import com.wd.winddots.components.users.MultipleUserSelectActivity;
import com.wd.winddots.components.users.UserBean;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.entity.User;
import com.wd.winddots.message.adapter.DiscussChatDetailAdapter;
import com.wd.winddots.message.presenter.impl.DiscussChatDetailPresenterImpl;
import com.wd.winddots.message.presenter.view.DiscussChatDetailView;
import com.wd.winddots.message.view.DiscussChatDetailFooter;
import com.wd.winddots.utils.SpHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: DiscussChatDetailActivity
 * Author: 郑
 * Date: 2020/6/15 2:49 PM
 * Description: 会务详情
 */
public class DiscussChatDetailActivity extends CommonActivity<DiscussChatDetailView, DiscussChatDetailPresenterImpl>
        implements DiscussChatDetailView, BaseQuickAdapter.OnItemClickListener {


    private RecyclerView mRecyclerView;
    private DiscussChatDetailFooter mFooter;
    private DiscussChatDetailAdapter mAdapter;
    private LinearLayout mEditBtn;

    private List<DiscussChatBean.DataBean.QuestionUsersBean> questionUsers = new ArrayList<>();

    private String mId;
    private String mTitle;
    private DiscussChatBean mData;

    @Override
    public DiscussChatDetailPresenterImpl initPresenter() {
        return new DiscussChatDetailPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.acitiviy_discuss_detail);

        Intent intent = getIntent();
        if (intent != null){
            mId = intent.getStringExtra("id");
            mTitle = intent.getStringExtra("title");
            setTitleText(mTitle);
        }

        mRecyclerView = findViewById(R.id.rlist);
        mEditBtn = findViewById(R.id.tv_edit_btn);
        mEditBtn.setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,5);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mFooter = new DiscussChatDetailFooter(mContext);
        mAdapter = new DiscussChatDetailAdapter(R.layout.item_discuss_detail,questionUsers);
        mAdapter.setFooterView(mFooter);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        presenter.initMessage(mId, SpHelper.getInstance(mContext).getUserId());
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_edit_btn:
                editBtnDidClick();
                break;
        }
    }

    private void editBtnDidClick(){
        Intent intent = new Intent(mContext, DiscussChatAddActivity.class);
        Gson gson = new Gson();
        String jsonS = gson.toJson(mData);
        intent.putExtra("data",jsonS);
        startActivityForResult(intent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (resultCode == Const.MULTIPLE_USER_SELECT_TARGRT) {
            List<User> list = (List<User>) data.getSerializableExtra("list");
            StringBuilder name = new StringBuilder();
            List<String> userIds = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                User item = list.get(i);
                userIds.add(item.getId());
            }
            presenter.addUsers(mId,userIds);
        }else if (requestCode == 1000){
            presenter.initMessage(mId,SpHelper.getInstance(mContext).getUserId());
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DiscussChatBean.DataBean.QuestionUsersBean ben = questionUsers.get(position);
        if (!StringUtils.isNullOrEmpty(ben.getUserId())){
            return;
        }
        List<String> ids = new ArrayList<>();
        List<String> disableIds = new ArrayList<>();
        disableIds.add(SpHelper.getInstance(mContext).getUserId());
        if (questionUsers.size() > 0) {
            for (int i = 0; i < questionUsers.size(); i++) {
                DiscussChatBean.DataBean.QuestionUsersBean item = questionUsers.get(i);
                if (!StringUtils.isNullOrEmpty(item.getUserId())){
                    disableIds.add(item.getUserId());
                }
            }
        }
        Gson gson = new Gson();
        Intent intent = new Intent(mContext, MultipleUserSelectActivity.class);
        intent.putExtra("disableIds", gson.toJson(disableIds));
        startActivityForResult(intent, 0);
    }


    @Override
    public void initMessageDataListSuccess(DiscussChatBean bean) {
        mData = bean;
        questionUsers.clear();
        questionUsers.addAll(bean.getData().getQuestionUsers());
        questionUsers.add(new DiscussChatBean.DataBean.QuestionUsersBean());
        mAdapter.notifyDataSetChanged();
        mFooter.setData(bean);
        if (bean.getData().getCreatorId().equals(SpHelper.getInstance(mContext).getUserId())){
            mEditBtn.setVisibility(View.VISIBLE);
        }else {
            mEditBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void initMessageDataListError() {

    }

    @Override
    public void initMessageDataListCompleted() {

    }

    @Override
    public void addUsersSuccess() {
        showToast(mContext.getString(R.string.discuss_add_user_success));
        presenter.initMessage(mId,SpHelper.getInstance(mContext).getUserId());
    }

    @Override
    public void addUsersError() {

    }

    @Override
    public void addUsersCompleted() {

    }

}
