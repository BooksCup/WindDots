package com.wd.winddots.message.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.message.adapter.GroupMenbersAdapter;
import com.wd.winddots.message.bean.GroupChatHistoryBean;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.SpHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * FileName: GroupMembersActivity
 * Author: 郑
 * Date: 2020/10/20 11:30 AM
 * Description: 群成员
 */
public class GroupMembersActivity extends CommonActivity implements BaseQuickAdapter.OnItemClickListener {


    private static final int REQUEST_CODE_ADD_MEMBERS = 10002;



    public CompositeSubscription compositeSubscription;
    public MsgDataManager dataManager;
    
    private String mGroupId;
    private String mTitle;

    private RecyclerView mRecyclerView;
    private GroupMenbersAdapter mAdapter;
    private List<GroupChatHistoryBean.AvatarMapBean> mAvatarMapBeans = new ArrayList<>();




    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_group_members);
        
        compositeSubscription = new CompositeSubscription();
        dataManager = new MsgDataManager();

        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra("title");
//            mId = intent.getStringExtra("id");
//            mTargetId = intent.getStringExtra("targetId");
            mGroupId = intent.getStringExtra("groupId");
            setTitleText(mTitle);
        }

        mRecyclerView = findViewById(R.id.rlist);
        GridLayoutManager manager = new GridLayoutManager(mContext, 5);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GroupMenbersAdapter(R.layout.item_group_menbers,mAvatarMapBeans);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayout deleteBtn = findViewById(R.id.ll_delete);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        getData();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_delete:
                onDelete();
                break;
        }
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
                        mAvatarMapBeans.clear();
                        String userId = SpHelper.getInstance(mContext).getUserId();
                        GroupChatHistoryBean.AvatarMapBean owner = null;
                        for (int i = 0;i < list.size();i++){
                            GroupChatHistoryBean.AvatarMapBean user = list.get(i);
                            if (1 == user.getIsOwner()){
                                owner = user;
                            }
                        }
                        mAvatarMapBeans.addAll(list);
                        if (owner != null && userId.equals(owner.getId())){
                            GroupChatHistoryBean.AvatarMapBean add = new GroupChatHistoryBean.AvatarMapBean();
                            add.setType("add");
                            GroupChatHistoryBean.AvatarMapBean delete = new GroupChatHistoryBean.AvatarMapBean();
                            delete.setType("delete");
                            mAvatarMapBeans.add(delete);
                            mAvatarMapBeans.add(add);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }));     
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        GroupChatHistoryBean.AvatarMapBean bean = mAvatarMapBeans.get(position);
        if ("add".equals(bean.getType())){
            Intent intent = new Intent(mContext, AddMenbersActivity.class);
            List<String> ids = new ArrayList<>();
            for (int i = 0;i < mAvatarMapBeans.size();i++){
                GroupChatHistoryBean.AvatarMapBean bean1 = mAvatarMapBeans.get(i);
                if (!StringUtils.isNullOrEmpty(bean1.getId())){
                    ids.add(bean1.getId());
                }
            }
            Gson gson = new Gson();
            String jsonS = gson.toJson(ids);
            intent.putExtra("data", jsonS);
            intent.putExtra("groupId", mGroupId);
            startActivityForResult(intent,REQUEST_CODE_ADD_MEMBERS);
        }else if ("delete".equals(bean.getType())){

            Intent intent = new Intent(mContext, DeleteMenbersActivity.class);
            intent.putExtra("groupId", mGroupId);
            startActivityForResult(intent,REQUEST_CODE_ADD_MEMBERS);

        }else if (!StringUtils.isNullOrEmpty(bean.getId())){
            Intent intent = new Intent(mContext, UserInfoActivity.class);
            intent.putExtra("id", bean.getId());
            startActivity(intent);
        }
    }

    private void onDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("群定要删除并退出该群聊吗?");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confirmDelete();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private void confirmDelete(){

        showLoading();
        compositeSubscription.add(dataManager.deleteGroupMembers(mGroupId, SpHelper.getInstance(mContext).getUserId()).
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
                        intent.putExtra("data", "delete");
                        setResult(0, intent);
                        finish();
                    }
                })
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_MEMBERS && data != null){
            getData();
            Intent intent = new Intent();
            intent.putExtra("data", "data");
            setResult(0, intent);
        }

    }
}
