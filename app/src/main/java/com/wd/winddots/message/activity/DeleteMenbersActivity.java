package com.wd.winddots.message.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.message.bean.GroupChatHistoryBean;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.SpHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: DeleteMenbersActivity
 * Author: 郑
 * Date: 2020/10/19 3:35 PM
 * Description: 删除群成员
 */
public class DeleteMenbersActivity extends CommonActivity implements BaseQuickAdapter.OnItemClickListener {

    public CompositeSubscription compositeSubscription;
    public MsgDataManager dataManager;

    private RecyclerView mRecyclerView;
    private DeleteMenbersAdapter mAdapter;

    private RecyclerView mHaderRecyclerView;
    private HeaderAdapter mHeaderAdapter;

    private List<GroupChatHistoryBean.AvatarMapBean> mAvatarMapBeans = new ArrayList<>();
    private List<GroupChatHistoryBean.AvatarMapBean> mHeaderList = new ArrayList<>();
    private String mGroupId;

    @Override
    public BasePresenter initPresenter() {
        return new  BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_delete_members);

        setTitleText("删除成员");
        Intent intent = getIntent();
        mGroupId = intent.getStringExtra("groupId");

        compositeSubscription = new CompositeSubscription();
        dataManager = new MsgDataManager();

        mRecyclerView = findViewById(R.id.rlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new DeleteMenbersAdapter(R.layout.item_user_select, mAvatarMapBeans);
        mRecyclerView.setAdapter(mAdapter);

        mHaderRecyclerView = findViewById(R.id.header_list);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHaderRecyclerView.setLayoutManager(layoutManager1);
        mHeaderAdapter = new HeaderAdapter(R.layout.item_user_select_header,mHeaderList);
        mHaderRecyclerView.setAdapter(mHeaderAdapter);

        LinearLayout saveBtn = findViewById(R.id.ll_save);
        saveBtn.setOnClickListener(this);

    }

    @Override
    public void initListener() {
        super.initListener();
        mHeaderAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_save:
                saveBtnDidClick();
                break;
        }
    }

    @Override
    public void initData() {
        super.initData();
        getData();
    }




    private void getData(){
        compositeSubscription.add(dataManager.getGroupMembers(mGroupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String data) {
                        Log.e("net666",data);
                        Gson gson = new Gson();
                        List<GroupChatHistoryBean.AvatarMapBean> list = gson.fromJson(data, new TypeToken<List<GroupChatHistoryBean.AvatarMapBean>>() {
                        }.getType());

                        String userId = SpHelper.getInstance(mContext).getUserId();
                        for (int i = 0;i < list.size();i++){
                            GroupChatHistoryBean.AvatarMapBean user = list.get(i);
                            if (!userId.equals(user.getId())){
                                mAvatarMapBeans.add(user);
                            }
                        }
                        mAdapter.notifyDataSetChanged();

                    }
                }));
    }



    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


        if (adapter == mAdapter){

            GroupChatHistoryBean.AvatarMapBean item = mAvatarMapBeans.get(position);
            ImageView icon = view.findViewById(R.id.item_usersel_icon);

            if (item.isSelect()){
                icon.setImageResource(R.mipmap.unselect);
                item.setSelect(false);
                mHeaderList.remove(item);
            }else {
                icon.setImageResource(R.mipmap.select);
                item.setSelect(true);
                mHeaderList.add(item);
            }
            mHeaderAdapter.notifyDataSetChanged();
        }

    }


    private void saveBtnDidClick() {
        if (mHeaderList.size() == 0) {
            showToast(mContext.getString(R.string.add_group_chat_user_empty));
            return;
        }
//        if (mHeaderList.size() == 1) {
//            FriendListBean.User user = mHeaderList.get(0);
//            String singleName = user.getName();
//            String toId = user.getId();
//            Intent intent = new Intent(mContext, PrivateChatActivity.class);
//            intent.putExtra("title", singleName);
//            intent.putExtra("targetId", toId);
//            intent.putExtra("avatar", user.getAvatar());
//            startActivity(intent);
//            finish();
//            return;
//        }
        String owner = SpHelper.getInstance(mContext).getUserId();
        String groupName = SpHelper.getInstance(mContext).getName() + ",";
        String userIds = "";
        for (int i = 0; i < mHeaderList.size(); i++) {
            GroupChatHistoryBean.AvatarMapBean user = mHeaderList.get(i);
            if (i == mHeaderList.size() - 1) {
                groupName = groupName + user.getName();
                userIds = userIds + user.getId();
            } else {
                groupName = groupName + user.getName() + ",";
                userIds = userIds + user.getId() + ",";
            }
        }
        groupName = "群聊";
        showLoading();
        compositeSubscription.add(dataManager.deleteGroupMembers(mGroupId, userIds).
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
                        showToast(mContext.getString(R.string.delete_group_menber_error));
                    }

                    @Override
                    public void onNext(String s) {
                        hideLoading();
                        Log.e("net666", s);


                        if (s == null || s.length() != 11) {
                            showToast(mContext.getString(R.string.delete_group_menber_error));
                            return;
                        }
                        Intent intent = new Intent();
                        intent.putExtra("data", s);
                        setResult(0, intent);
                        finish();
                    }
                })
        );
    }





    private static class DeleteMenbersAdapter extends BaseQuickAdapter<GroupChatHistoryBean.AvatarMapBean, BaseViewHolder>{

        public DeleteMenbersAdapter(int layoutResId, @Nullable List<GroupChatHistoryBean.AvatarMapBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GroupChatHistoryBean.AvatarMapBean item) {
            ImageView icon = helper.getView(R.id.iv_avatar);
            GlideApp.with(mContext).load(item.getAvatar()).into(icon);
            helper.setText(R.id.tv_name,item.getName());
        }
    }


    private static class HeaderAdapter extends BaseQuickAdapter<GroupChatHistoryBean.AvatarMapBean, BaseViewHolder>{

        public HeaderAdapter(int layoutResId, @Nullable List<GroupChatHistoryBean.AvatarMapBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GroupChatHistoryBean.AvatarMapBean item) {
//            GlideApp.with(mContext).load(item.getAvatar()).into((ImageView) helper.getView(R.id.item_usersel_headericon));
            SimpleDraweeView mAvatarSdv = helper.getView(R.id.sdv_avatar);
            if (!TextUtils.isEmpty(item.getAvatar())){
                mAvatarSdv.setImageURI(Uri.parse(item.getAvatar()));
            }
        }
    }



}
