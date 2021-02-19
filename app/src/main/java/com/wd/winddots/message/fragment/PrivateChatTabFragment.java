package com.wd.winddots.message.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.desktop.list.card.activity.FriendSearchActivity;
import com.wd.winddots.desktop.list.enterprise.activity.EnterpriseListActivity;
import com.wd.winddots.message.activity.AddGroupChatActivity;
import com.wd.winddots.message.activity.GroupChatActivity;
import com.wd.winddots.message.adapter.AddGroupSuccessBean;
import com.wd.winddots.message.bean.PrivateChatHistoryBean;
import com.wd.winddots.message.bean.PrivateChatListBean;
import com.wd.winddots.message.presenter.impl.PrivateChatTabPresenterImpl;
import com.wd.winddots.message.presenter.view.PrivateChatTabView;
import com.wd.winddots.message.activity.PrivateChatActivity;
import com.wd.winddots.message.adapter.PrivateChatTabAdapter;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.BottomSearchBarView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class PrivateChatTabFragment extends BaseFragment<PrivateChatTabView, PrivateChatTabPresenterImpl>
        implements PrivateChatTabView, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener,
BottomSearchBarView.BottomSearchBarViewClickListener{


    private final  static int REQUEST_CODE_ADD_GROUP_CHAT = 1001;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private BottomSearchBarView mBottomSearchBarView;
    private PopupWindow mPopupWindow;

    private List<PrivateChatListBean> mPrivateChatListBeanList = new ArrayList<>();
    private PrivateChatTabAdapter mAdapter;

    private PrivateChatTabUnread privateChatTabUnread;

    public void setPrivateChatTabUnread(PrivateChatTabUnread privateChatTabUnread) {
        this.privateChatTabUnread = privateChatTabUnread;
    }

    public static PrivateChatTabFragment newInstance() {
        PrivateChatTabFragment fragment = new PrivateChatTabFragment();
        return fragment;
    }

    @Override
    public PrivateChatTabPresenterImpl initPresenter() {
        return new PrivateChatTabPresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_tab_privatechat;
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout = ((SwipeRefreshLayout) mView.findViewById(R.id.fragment_tab_privatechat_srl));
        mRecyclerView = ((RecyclerView) mView.findViewById(R.id.fragment_tab_privatechat_rv));
        mBottomSearchBarView = mView.findViewById(R.id.fragment_tap_privatechat_searchbar);

        mBottomSearchBarView.setSearchIconVisibility(View.VISIBLE);


        mAdapter = new PrivateChatTabAdapter(R.layout.item_privatechat,mPrivateChatListBeanList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mBottomSearchBarView.setOnIconClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        presenter.loadListData(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"));
    }


    /**
     * 接收到新消息
     *
     * @param newMessageListBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetNewMessage(PrivateChatHistoryBean.MessageListBean newMessageListBean) {
        onRefresh();
    }

    @Override
    public void onRefresh() {

        presenter.loadListData(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"));
    }

    @Override
    public void onPrivateChatHeaderSuccess() {

    }

    @Override
    public void onPrivateChatHeaderError(String errorMsg) {

    }

    @Override
    public void onPrivateChatListSuccess(List<PrivateChatListBean> privateChatListBeanList) {
        if (privateChatListBeanList.size() != 0) {
            mPrivateChatListBeanList.clear();
            mPrivateChatListBeanList.addAll(privateChatListBeanList);

            int unread = 0;
            for (int i = 0;i < privateChatListBeanList.size();i++){
                PrivateChatListBean listBean = privateChatListBeanList.get(i);
                unread += listBean.getUnread();
            }

            if (privateChatTabUnread != null){
                privateChatTabUnread.setPrivateUnread(unread);
            }

        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPrivateChatListError(String errorMsg) {

    }

    @Override
    public void onPrivateChatListComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 条目点击
     * @param adapter  the adpater
     * @param view     The itemView within the RecyclerView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PrivateChatListBean privateChatListBean = mPrivateChatListBeanList.get(position);
        String id = privateChatListBean.getId();
        privateChatListBean.setUnread(0);
        mAdapter.notifyItemChanged(position);

        int unread = 0;
        for (int i = 0;i < mPrivateChatListBeanList.size();i++){
            PrivateChatListBean listBean = mPrivateChatListBeanList.get(i);
            unread += listBean.getUnread();
        }

        if (privateChatTabUnread != null){
            privateChatTabUnread.setPrivateUnread(unread);
        }

        if ("single".equals(privateChatListBean.getTargetType())){

            String singleName = privateChatListBean.getSingleName();
            String targetId = privateChatListBean.getUserId();
            Intent intent = new Intent(mContext,PrivateChatActivity.class);
            intent.putExtra("title",singleName);
            intent.putExtra("id",id);
            intent.putExtra("targetId",targetId);
            intent.putExtra("avatar",privateChatListBean.getUserAvatar());

            String title = Utils.nullOrEmpty(privateChatListBean.getShortName()) + " " +
                    Utils.nullOrEmpty(privateChatListBean.getDepartmentName()) + " " +
                    Utils.nullOrEmpty(privateChatListBean.getSingleJobName());
            intent.putExtra("info",title);

            startActivity(intent);
        }else if ("group".equals(privateChatListBean.getTargetType())){
//            Intent intent = new Intent(mContext, DiscussChatActivity.class);
//            String id = privateChatListBean.getId();
//            intent.putExtra("id",id);
//            startActivity(intent);

            String singleName = privateChatListBean.getSingleName();
            String targetId = privateChatListBean.getUserId();
            String groupId = privateChatListBean.getToId();
            Intent intent = new Intent(mContext, GroupChatActivity.class);
            intent.putExtra("title",singleName);
            intent.putExtra("id",id);
            intent.putExtra("targetId",targetId);
            intent.putExtra("groupId",groupId);
            startActivity(intent);
        }
    }

    @Override
    public void onAddIconDidClick() {
        Intent intent = new Intent(mContext, AddGroupChatActivity.class);
        startActivityForResult(intent,REQUEST_CODE_ADD_GROUP_CHAT);
    }

    @Override
    public void onSearchIconDidClick() {

//        Intent intent = new Intent(mContext, FriendSearchActivity.class);
//        startActivity(intent);

        Intent intent = new Intent(mContext, EnterpriseListActivity.class);
        startActivity(intent);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }

        if (requestCode == REQUEST_CODE_ADD_GROUP_CHAT){
            String s = data.getStringExtra("data");
            Gson gson = new Gson();

//            String singleName = dataBean.getTitle();
//            String targetId = dataBean.getUserId();
//            String groupId = dataBean.getGroupId();
//            Intent intent = new Intent(mContext, GroupChatActivity.class);
//            intent.putExtra("title",singleName);
//            intent.putExtra("id",id);
//            intent.putExtra("targetId",targetId);
//            intent.putExtra("groupId",groupId);

            AddGroupSuccessBean bean = gson.fromJson(s,AddGroupSuccessBean.class);
            String singleName = bean.getName();
            String targetId = String.valueOf(bean.getjId());
            Intent intent = new Intent(mContext,GroupChatActivity.class);
            intent.putExtra("title",singleName);
            intent.putExtra("id",bean.getConvrId());
            intent.putExtra("targetId",targetId);
            intent.putExtra("groupId",bean.getId());
            startActivity(intent);
        }
    }



    public interface PrivateChatTabUnread{
        void setPrivateUnread(int unread);
    }
}
