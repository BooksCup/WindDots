package com.wd.winddots.message.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.message.activity.DiscussChatAddActivity;
import com.wd.winddots.message.bean.GroupChatHistoryBean;
import com.wd.winddots.message.bean.GroupChatListBean;
import com.wd.winddots.message.bean.PrivateChatHistoryBean;
import com.wd.winddots.message.presenter.impl.GroupChatTabPresenterImpl;
import com.wd.winddots.message.presenter.view.GroupChatTabView;
import com.wd.winddots.message.activity.DiscussChatActivity;
import com.wd.winddots.message.activity.GroupChatActivity;
import com.wd.winddots.message.adapter.GroupChatTabAdapter;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.view.BottomSearchBarView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class GroupChatTabFragment extends BaseFragment<GroupChatTabView, GroupChatTabPresenterImpl>
        implements GroupChatTabView, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener ,
        BottomSearchBarView.BottomSearchBarViewClickListener {

    private List<GroupChatListBean.DataBean> mGroupChatListBeanList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private GroupChatTabAdapter mAdapter;
    private View mFooterView;
    private int page = 1;

    private GroupChatTabUnread groupChatTabUnread;

    public void setGroupChatTabUnread(GroupChatTabUnread groupChatTabUnread) {
        this.groupChatTabUnread = groupChatTabUnread;
    }

    public static GroupChatTabFragment newInstance() {
        GroupChatTabFragment fragment = new GroupChatTabFragment();
        return fragment;
    }

    @Override
    public GroupChatTabPresenterImpl initPresenter() {
        return new GroupChatTabPresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_tab_groupchat;
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout = ((SwipeRefreshLayout) mView.findViewById(R.id.fragment_tab_groupchat_srl));
        mRecyclerView = ((RecyclerView) mView.findViewById(R.id.fragment_tab_groupchat_rv));

        mAdapter = new GroupChatTabAdapter(R.layout.item_groupchat,mGroupChatListBeanList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        BottomSearchBarView barView = mView.findViewById(R.id.fragment_tab_groupchat_searchbar);
        barView.setOnIconClickListener(this);
    }

    @Override
    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        EventBus.getDefault().register(this);


    }

    @Override
    public void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        presenter.refreshGroupChatListData(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(String message) {
        onRefresh();
    }

    /**
     * 接收到新消息
     *
     * @param newMessageListBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetNewMessage(GroupChatHistoryBean.MessageListBean newMessageListBean) {
        onRefresh();
    }


    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        page = 1;
        presenter.refreshGroupChatListData(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"));
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        presenter.loadMoreGroupChatListData(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"),page);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        onRefresh();
    }

    @Override
    public void onGroupChatListRefreshSuccess(GroupChatListBean bean) {
        List<GroupChatListBean.DataBean> dataBeans = bean.getData();
        if (dataBeans.size() > 0) {
            mGroupChatListBeanList.clear();
            mGroupChatListBeanList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
        } else {
            showToast("暂无任何数据");
        }

        if (dataBeans.size() < 10){
            mAdapter.setEnableLoadMore(false);
            if (mFooterView == null){
                mFooterView = LayoutInflater.from(mContext).inflate(R.layout.footer_no_more_data,null,false);
                mAdapter.addFooterView(mFooterView);
            }
        }

        int unread = 0;
        for (int i = 0;i < dataBeans.size();i++){
            GroupChatListBean.DataBean bean1 = dataBeans.get(i);
            unread += bean1.getUnReadCount();
        }
        if (groupChatTabUnread != null){
            groupChatTabUnread.setGroupUnread(unread);
        }


    }

    @Override
    public void onGroupChatListRefreshError(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void onGroupChatListRefreshComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onGroupChatListLoadMoreSuccess(GroupChatListBean bean) {
        List<GroupChatListBean.DataBean> dataList = bean.getData();
        if (dataList.size() != 0){
            mGroupChatListBeanList.addAll(dataList);
            mAdapter.loadMoreComplete();
            mAdapter.notifyDataSetChanged();
        }

        if (dataList.size() < 10){
            mAdapter.loadMoreEnd();
        }

        int unread = 0;
        for (int i = 0;i < mGroupChatListBeanList.size();i++){
            GroupChatListBean.DataBean bean1 = mGroupChatListBeanList.get(i);
            unread += bean1.getUnReadCount();
        }
        if (groupChatTabUnread != null){
            groupChatTabUnread.setGroupUnread(unread);
        }
    }

    @Override
    public void onGroupChatListLoadMoreError(String errorMsg) {
        mAdapter.loadMoreFail();
        page--;
    }

    @Override
    public void onGroupChatListLoadMoreComplete() {
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
        GroupChatListBean.DataBean dataBean = ((GroupChatTabAdapter) adapter).getData().get(position);

        String type = dataBean.getType();//类型
        String id = dataBean.getId();
        dataBean.setUnReadCount(0);






        mAdapter.notifyItemChanged(position);

        int unread = 0;
        for (int i = 0;i < mGroupChatListBeanList.size();i++){
            GroupChatListBean.DataBean listBean = mGroupChatListBeanList.get(i);
            unread += listBean.getUnReadCount();
        }

        if (groupChatTabUnread != null){
            groupChatTabUnread.setGroupUnread(unread);
        }

        if ("1".equals(type)){ //群聊模式
            String singleName = dataBean.getTitle();
            String targetId = dataBean.getUserId();
            String groupId = dataBean.getGroupId();
            Intent intent = new Intent(mContext, GroupChatActivity.class);
            if (!"0".equals(dataBean.getUserGroupStatus())){
                presenter.outGroupPerson(SpHelper.getInstance(mContext).getUserId(),groupId);
            }
            dataBean.setUserGroupStatus("0");
            intent.putExtra("title",singleName);
            intent.putExtra("id",id);
            intent.putExtra("targetId",targetId);
            intent.putExtra("groupId",groupId);
            startActivity(intent);
        }else { //社区讨论模式
            Intent intent = new Intent(mContext, DiscussChatActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("title",dataBean.getTitle());
            startActivityForResult(intent,1);
        }


    }

    @Override
    public void onAddIconDidClick() {
        Intent intent = new Intent(mContext, DiscussChatAddActivity.class);
        startActivityForResult(intent,1000);
    }

    @Override
    public void onSearchIconDidClick() {

    }

    public interface GroupChatTabUnread{
        void setGroupUnread(int unread);
    }
}
