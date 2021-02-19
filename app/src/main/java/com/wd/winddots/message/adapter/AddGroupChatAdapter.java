package com.wd.winddots.message.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.desktop.list.card.bean.FriendListBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.RecyclerExpandBaseAdapter;
import com.wd.winddots.utils.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: AddGroupChatAdapter
 * Author: 郑
 * Date: 2020/8/26 10:36 AM
 * Description:
 */
public class AddGroupChatAdapter extends RecyclerExpandBaseAdapter<FriendListBean.Contact,FriendListBean.User, RecyclerView.ViewHolder> {
    Context mContext;
    private OnSubItemClickListener onitemClickListener;

    public void setContext(Context context){
        mContext = context;
    }

    public Activity activity;

    public void setOnSubItemClickListener(OnSubItemClickListener listener){
        onitemClickListener = listener;
    }


    /**
     * 悬浮标题栏被点击的时候，展开收起切换功能
     */
    public void switchExpand(int adapterPosition) {
        int groupIndex = mIndexMap.get(adapterPosition).getGroupIndex();
        ExpandGroupItemEntity entity = mDataList.get(groupIndex);
        entity.setExpand(!entity.isExpand());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("tttttt","tttttttttt");
        if (viewType == VIEW_TYPE_ITEM_TIME) {
            AddGroupChatAdapter.TitleItemHolder holder = new AddGroupChatAdapter.TitleItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_group_chat_header, parent, false));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpandGroupItemEntity entity = (ExpandGroupItemEntity) v.getTag();
                    entity.setExpand(!entity.isExpand());
                    notifyDataSetChanged();
                }
            });
            return holder;
        } else {
            AddGroupChatAdapter.SubItemHolder itemHolder = new AddGroupChatAdapter.SubItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_select, parent, false));
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onitemClickListener != null){
                        FriendListBean.User user = (FriendListBean.User)view.getTag();
                        onitemClickListener.onItemClick(user);
                    }
                }
            });
            return itemHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.e("tttttt","ttttttttttmmmmmm");
        if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            AddGroupChatAdapter.TitleItemHolder itemHolder = (AddGroupChatAdapter.TitleItemHolder) holder;
            itemHolder.itemView.setTag(mDataList.get(groupIndex));
            FriendListBean.Contact enterprise = mDataList.get(groupIndex).getParent();
            itemHolder.enterpriseName.setText(enterprise.getName());

        } else {
            AddGroupChatAdapter.SubItemHolder subHolder = (AddGroupChatAdapter.SubItemHolder) holder;
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            int childIndex = mIndexMap.get(position).getChildIndex();
            final FriendListBean.User subItem = mDataList.get(groupIndex).getChildList().get(childIndex);
            subHolder.itemView.setTag(subItem);


            GlideApp.with(mContext).load(subItem.getAvatar()).into(subHolder.icon);
            subHolder.name.setText(subItem.getName());
            subHolder.position.setText(Utils.nullOrEmpty(subItem.getDepartmentName()));

            if (subItem.isDisable()){
                subHolder.selectIcon.setImageResource(R.mipmap.selectgray);
            }else {
                if (subItem.isSelect()){
                    subHolder.selectIcon.setImageResource(R.mipmap.select);
                }else {
                    subHolder.selectIcon.setImageResource(R.mipmap.unselect);
                }
            }




            subHolder.visitingIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UserInfoActivity.class);
                    intent.putExtra("id", subItem.getId());
                    activity.startActivity(intent);
                }
            });

        }

    }



    static class TitleItemHolder extends RecyclerView.ViewHolder {

        TextView enterpriseName;


        TitleItemHolder(View itemView) {
            super(itemView);

            enterpriseName = itemView.findViewById(R.id.tv_name);

        }
    }

    static class SubItemHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        ImageView selectIcon;
        TextView name;
        TextView position;
        ImageView visitingIcon;


        SubItemHolder(View itemView) {
            super(itemView);
            selectIcon = itemView.findViewById(R.id.item_usersel_icon);
            icon = itemView.findViewById(R.id.iv_avatar);
            name = itemView.findViewById(R.id.tv_name);
            position = itemView.findViewById(R.id.tv_job_name);
            visitingIcon = itemView.findViewById(R.id.visiting_card_icon);

        }
    }

    public interface OnSubItemClickListener{
        void onItemClick(FriendListBean.User user);
    }


}
