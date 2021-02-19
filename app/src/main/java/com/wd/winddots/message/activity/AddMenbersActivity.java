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
import com.wd.winddots.desktop.list.card.bean.FriendListBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderItemDecoration;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.PinnedHeaderRecyclerView;
import com.wd.winddots.message.adapter.AddGroupChatAdapter;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.SpHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
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
 * FileName: AddMenbersActivity
 * Author: 郑
 * Date: 2020/10/19 1:46 PM
 * Description:
 */
public class AddMenbersActivity extends CommonActivity implements AddGroupChatAdapter.OnSubItemClickListener {

    private static final int REQUEST_CODE_USER_SEARCH = 10086;


    public CompositeSubscription compositeSubscription;
    public MsgDataManager dataManager;
    private LinearLayoutManager mLayoutManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PinnedHeaderRecyclerView mRecyclerView;
    private AddGroupChatAdapter mAdapter;

    private RecyclerView mHaderRecyclerView;
    private HeaderAdapter mHeaderAdapter;
    private List<FriendListBean.User> mHeaderList = new ArrayList<>();

    private FriendListBean mInfoBean;

    private List<String> disableIds = new ArrayList<>();
    private String mGroupId;


    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();

        Intent intent = getIntent();
        String disableIdsJson = intent.getStringExtra("data");
        mGroupId = intent.getStringExtra("groupId");
        if (!StringUtils.isNullOrEmpty(disableIdsJson)){
            Log.e("111111",disableIdsJson);
            Gson gson = new Gson();
            List<String> ids = gson.fromJson(disableIdsJson,new TypeToken<List<String>>() {
            }.getType());
            disableIds.addAll(ids);
        }

        compositeSubscription = new CompositeSubscription();
        dataManager = new MsgDataManager();

        addBadyView(R.layout.activity_add_group_members);
        setTitleText(mContext.getString(R.string.add_group_chat_title));

        mRecyclerView = findViewById(R.id.rlist);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new PinnedHeaderItemDecoration());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new AddGroupChatAdapter();
        mAdapter.setContext(mContext);
        mAdapter.activity = this;
        mRecyclerView.setAdapter(mAdapter);


        mHaderRecyclerView = findViewById(R.id.header_list);
        mHaderRecyclerView.setVisibility(View.GONE);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHaderRecyclerView.setLayoutManager(layoutManager1);
        mHeaderAdapter = new HeaderAdapter(R.layout.item_user_select_header, mHeaderList);
        mHaderRecyclerView.setAdapter(mHeaderAdapter);

        LinearLayout searchBar = findViewById(R.id.searchbar);
        searchBar.setOnClickListener(this);

        LinearLayout saveBtn = findViewById(R.id.ll_add_menbers_save);
        saveBtn.setOnClickListener(this);

    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnSubItemClickListener(this);
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
            case R.id.ll_add_menbers_save:
                saveBtnDidClick();
                break;
            case R.id.searchbar:
                searchBtnDidClick();
                break;
        }
    }

    @Override
    public void initData() {
        super.initData();
        getData();
    }

    private void getData() {
        SpHelper helper = SpHelper.getInstance(mContext);
        compositeSubscription.add(dataManager.getFriendList(helper.getUserId(), helper.getEnterpriseId()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("net666", String.valueOf(e));
                        showToast(mContext.getString(R.string.toast_loading_error));
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("net666", s);
                        Gson gson = new Gson();
                        FriendListBean infoBean = gson.fromJson(s, FriendListBean.class);
                        mInfoBean = infoBean;
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
                        //dataList.add(appleItem);
                        for (int group = 0; group < infoBean.getContacts().size(); group++) {
                            ExpandGroupItemEntity<FriendListBean.Contact, FriendListBean.User> groupItem = new ExpandGroupItemEntity<>();
                            FriendListBean.Contact contact = infoBean.getContacts().get(group);
                            groupItem.setExpand(false);
                            groupItem.setParent(contact);
                            List<FriendListBean.User> childList = contact.getFriendList();
                            for (int m = 0;m < childList.size();m++){
                                FriendListBean.User user = childList.get(m);
                                for (int n = 0;n < disableIds.size();n++){
                                    if (user.getId().equals(disableIds.get(n))){
                                        user.setDisable(true);
                                        break;
                                    }
                                }
                            }
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

        if (user.isDisable()){
            return;
        }


        if (user.isSelect()) {
            //icon.setImageResource(R.mipmap.unselect);
            user.setSelect(false);
            mHeaderList.remove(user);
        } else {
            //icon.setImageResource(R.mipmap.select);
            user.setSelect(true);
            mHeaderList.add(user);
        }

        if (mHeaderList.size() == 0) {
            mHaderRecyclerView.setVisibility(View.GONE);
        } else {
            mHaderRecyclerView.setVisibility(View.VISIBLE);
        }

        mHeaderAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    private void searchBtnDidClick() {
        Intent intent = new Intent(mContext, AddGroupSearchActivity.class);
        Gson gson = new Gson();
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < mHeaderList.size(); i++) {
            ids.add(mHeaderList.get(i).getId());
        }

        ids.addAll(disableIds);

        intent.putExtra("data", gson.toJson(ids));
        startActivityForResult(intent, REQUEST_CODE_USER_SEARCH);
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
            FriendListBean.User user = mHeaderList.get(i);
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
        compositeSubscription.add(dataManager.addGroupMembers(mGroupId, userIds).
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
                        showToast(mContext.getString(R.string.add_group_menber_error));
                    }

                    @Override
                    public void onNext(String s) {
                        hideLoading();
                        Log.e("net666", s);


                        if (s == null || s.length() != 11) {
                            showToast(mContext.getString(R.string.add_group_menber_error));
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (requestCode == REQUEST_CODE_USER_SEARCH) {
            String jsonS = data.getStringExtra("data");
            Gson gson = new Gson();
            List<String> list = gson.fromJson(jsonS, new TypeToken<List<String>>() {
            }.getType());

            if (list == null || list.size() == 0){
                return;
            }
            for (int i = 0; i< mInfoBean.getContacts().size();i++){
                FriendListBean.Contact contact = mInfoBean.getContacts().get(i);
                for (int m = 0;m < contact.getFriendList().size();m++){
                    FriendListBean.User user = contact.getFriendList().get(m);
                    for (int n = 0;n < list.size();n++){
                        if (user.getId().equals(list.get(n))){
                            mHeaderList.add(user);
                            user.setSelect(true);
                        }
                    }
                }
            }

            if (mHeaderList.size() > 0){
                mHaderRecyclerView.setVisibility(View.VISIBLE);
            }
            mAdapter.notifyDataSetChanged();
            mHeaderAdapter.notifyDataSetChanged();
        }


    }

    private static class HeaderAdapter extends BaseQuickAdapter<FriendListBean.User, BaseViewHolder> {

        public HeaderAdapter(int layoutResId, @Nullable List<FriendListBean.User> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, FriendListBean.User item) {
//            GlideApp.with(mContext).load(item.getAvatar()).into((ImageView) helper.getView(R.id.item_usersel_headericon));
            SimpleDraweeView mAvatarSdv = helper.getView(R.id.sdv_avatar);
            if (!TextUtils.isEmpty(item.getAvatar())){
                mAvatarSdv.setImageURI(Uri.parse(item.getAvatar()));
            }
        }
    }

}
