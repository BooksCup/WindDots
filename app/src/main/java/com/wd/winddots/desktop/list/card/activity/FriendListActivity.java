package com.wd.winddots.desktop.list.card.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.desktop.list.card.adapter.FriendListAdapter;
import com.wd.winddots.desktop.list.card.bean.FriendListBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderItemDecoration;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderRecyclerView;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.SpHelper;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: FriendListActivity
 * Author: 郑
 * Date: 2020/6/18 3:01 PM
 * Description: 好友列表
 */
public class FriendListActivity extends CommonActivity
        implements FriendListAdapter.OnSubItemClickListener,
        FriendListAdapter.OnSubItemLongClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        FriendListAdapter.OnSubItemAgreeApplyClickListener {


    public static final int REQUEST_CODE_ADD_FRIEND = 1;
    public static final int REQUEST_CODE_USER_INFO = 2;

    public CompositeSubscription compositeSubscription;
    public MsgDataManager dataManager;
    private LinearLayoutManager mLayoutManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PinnedHeaderRecyclerView mRecyclerView;


    private FriendListAdapter mAdapter;

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();

        compositeSubscription = new CompositeSubscription();
        dataManager = new MsgDataManager();

        addBadyView(R.layout.activity_friend_list);
        setTitleText(mContext.getString(R.string.friend_list_title));

        mSwipeRefreshLayout = findViewById(R.id.srrefresh);
        mRecyclerView = findViewById(R.id.rlist);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new PinnedHeaderItemDecoration());
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new FriendListAdapter();
        mAdapter.setContext(mContext);
        mRecyclerView.setAdapter(mAdapter);

        ImageView addBtn = findViewById(R.id.iv_addIcon);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        getData();
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnSubItemClickListener(this);
        mAdapter.setOnSubItemAgreeApplyClickListener(this);
        mAdapter.setOnSubItemLongClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setOnPinnedHeaderClickListener(new PinnedHeaderRecyclerView.OnPinnedHeaderClickListener() {
            @Override
            public void onPinnedHeaderClick(int adapterPosition) {
                mAdapter.switchExpand(adapterPosition);
                //标题栏被点击之后，滑动到指定位置
                mLayoutManager.scrollToPositionWithOffset(adapterPosition, 0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_addIcon:
                onAddIconDidClick();
                break;
        }
    }

    private void onAddIconDidClick() {
        Intent intent = new Intent(mContext, SearchUserActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_FRIEND);
    }


    @Override
    public void onRefresh() {
        getData();
    }

    private void getData() {
        SpHelper helper = SpHelper.getInstance(mContext);
        mSwipeRefreshLayout.setRefreshing(true);
        compositeSubscription.add(dataManager.getFriendList(helper.getUserId(), helper.getEnterpriseId()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("net", String.valueOf(e));
                        showToast(mContext.getString(R.string.toast_loading_error));
                    }

                    @Override
                    public void onNext(String s) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Gson gson = new Gson();
                        FriendListBean infoBean = gson.fromJson(s, FriendListBean.class);
                        List<ExpandGroupItemEntity<FriendListBean.Contact, FriendListBean.User>> dataList = new ArrayList<>();
                        List<FriendListBean.FriendApply> friendApplyList = infoBean.getFriendApplyList();
                        if (friendApplyList == null) {
                            friendApplyList = new ArrayList<>();
                        }
                        List<FriendListBean.User> userList = new ArrayList<>();
                        for (int i = 0; i < friendApplyList.size(); i++) {
                            FriendListBean.FriendApply friendApply = friendApplyList.get(i);
                            FriendListBean.User user = new FriendListBean.User();
                            user.setAvatar(friendApply.getUserAvatar());
                            user.setPhone(friendApply.getEnterpriseName());
                            user.setName(friendApply.getUserName());
                            user.setStatus(friendApply.getStatus());
                            user.setApplyId(friendApply.getApplyId());
                            user.setId(friendApply.getFromUserId());
                            user.setEnterpriseId(friendApply.getEnterpriseId());
                            userList.add(user);
                        }
                        ExpandGroupItemEntity<FriendListBean.Contact, FriendListBean.User> appleItem = new ExpandGroupItemEntity<>();
                        FriendListBean.Contact apply = new FriendListBean.Contact();
                        apply.setFriendList(userList);
                        apply.setName(mContext.getString(R.string.friend_list_new_friend));
                        appleItem.setExpand(false);
                        appleItem.setParent(apply);
                        appleItem.setChildList(userList);
                        dataList.add(appleItem);
                        for (int group = 0; group < infoBean.getContacts().size(); group++) {
                            ExpandGroupItemEntity<FriendListBean.Contact, FriendListBean.User> groupItem = new ExpandGroupItemEntity<>();
                            FriendListBean.Contact contact = infoBean.getContacts().get(group);
                            groupItem.setExpand(false);
                            groupItem.setParent(contact);
                            List<FriendListBean.User> childList = contact.getFriendList();
                            groupItem.setChildList(childList);
                            dataList.add(groupItem);
                        }
                        mAdapter.setData(dataList);
                    }
                })
        );
    }

    @Override
    public void onItemClick(FriendListBean.User user) {
        Intent intent = new Intent(mContext, UserInfoActivity.class);
        intent.putExtra("id", user.getId());
        if (user.getApplyId() != null) {
            intent.putExtra("applyId", user.getApplyId());
        }
        startActivityForResult(intent, REQUEST_CODE_USER_INFO);
    }

    @Override
    public void onAgreeApplyClick(FriendListBean.User user) {
        String userId = SpHelper.getInstance(mContext).getUserId();
        String enterpriseId = SpHelper.getInstance(mContext).getEnterpriseId();
        compositeSubscription.add(dataManager.agreeFriendApplies(user.getApplyId(), 2,userId,enterpriseId,user.getId(),user.getEnterpriseId()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(mContext.getString(R.string.user_info_post_friend_apply_error));
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666",s);
                        if (StringUtils.isNullOrEmpty(s)){
                            showToast(mContext.getString(R.string.user_info_agree_apply_error));
                        }else {
                            if (s.length() == 9){
                                showToast(mContext.getString(R.string.user_info_agree_apply_success));
                                onRefresh();
                            }else {
                                showToast(mContext.getString(R.string.user_info_agree_apply_error));
                            }
                        }
                    }
                })
        );
    }


    @Override
    public void onItemLongClick(final FriendListBean.User user) {
        final String userId = SpHelper.getInstance(mContext).getUserId();


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("确定要删除该好友吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                compositeSubscription.add(dataManager.deleteFriend(userId,user.getId()).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                showToast(mContext.getString(R.string.user_info_post_friend_apply_error));
                            }

                            @Override
                            public void onNext(String s) {
                                Log.e("net666",s);
                                if (StringUtils.isNullOrEmpty(s)){
                                    showToast(mContext.getString(R.string.user_info_agree_apply_error));
                                }else {
                                    if (s.length() == 6){
                                        showToast(mContext.getString(R.string.user_info_delete_success));

                                        deleteWithUser(user);

                                        // onRefresh();
                                    }else {
                                        showToast(mContext.getString(R.string.user_info_delete_success));
                                    }
                                }
                            }
                        })
                );
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        onRefresh();
    }


    private void deleteWithUser(FriendListBean.User user){

        List<ExpandGroupItemEntity<FriendListBean.Contact, FriendListBean.User>> dataList =  mAdapter.getData();

        int group = -1;
        int index = -1;
        for (int i = 0;i < dataList.size();i++){
            ExpandGroupItemEntity<FriendListBean.Contact, FriendListBean.User> enterprise = dataList.get(i);
            List<FriendListBean.User> users = enterprise.getChildList();
            for (int m = 0;m < users.size();m++){
                FriendListBean.User user1 = users.get(m);
                if (user.getId().equals(user1.getId())){
                    group = i;
                    index = m;
                    break;
                }
            }
        }

        if (group != -1 && index != -1){
            dataList.get(group).getChildList().remove(user);
        }
        mAdapter.notifyDataSetChanged();
    }


}
