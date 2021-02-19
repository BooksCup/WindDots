package com.wd.winddots.desktop.list.card.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.card.bean.FriendListBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.RecyclerExpandBaseAdapter;
import com.wd.winddots.utils.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FriendListAdapter
 * Author: 郑
 * Date: 2020/6/18 4:01 PM
 * Description:
 */
public class FriendListAdapter extends RecyclerExpandBaseAdapter<FriendListBean.Contact,FriendListBean.User, RecyclerView.ViewHolder> {


    Context mContext;
    private OnSubItemClickListener onitemClickListener;
    private OnSubItemLongClickListener onitemLongClickListener;
    private OnSubItemAgreeApplyClickListener agreeApplyClickListener;

    public void setContext(Context context){
        mContext = context;
    }
    public void setOnSubItemClickListener(OnSubItemClickListener listener){
        onitemClickListener = listener;
    }
    public void setOnSubItemLongClickListener(OnSubItemLongClickListener listener){
        onitemLongClickListener = listener;
    }
    public void setOnSubItemAgreeApplyClickListener(OnSubItemAgreeApplyClickListener listener){
        agreeApplyClickListener = listener;
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
        if (viewType == VIEW_TYPE_ITEM_TIME) {
            TitleItemHolder holder = new TitleItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_list_header, parent, false));
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
            SubItemHolder itemHolder = new SubItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_list, parent, false));
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onitemClickListener != null){
                        FriendListBean.User user = (FriendListBean.User)view.getTag();
                        onitemClickListener.onItemClick(user);
                    }
                }
            });

            itemHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onitemLongClickListener != null){
                        FriendListBean.User user = (FriendListBean.User)view.getTag();
                        onitemLongClickListener.onItemLongClick(user);
                    }
                    return true;
                }
            });

            return itemHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            TitleItemHolder itemHolder = (TitleItemHolder) holder;
            itemHolder.itemView.setTag(mDataList.get(groupIndex));
            FriendListBean.Contact enterprise = mDataList.get(groupIndex).getParent();
            itemHolder.enterpriseName.setText(enterprise.getName());
            itemHolder.length.setText(enterprise.getFriendList().size()+"");
            if (StringUtils.isNullOrEmpty(enterprise.getId())){
                itemHolder.icon.setImageResource(R.mipmap.new_friends);
            }else {
                itemHolder.icon.setImageResource(R.mipmap.companys);
            }

        } else {
            SubItemHolder subHolder = (SubItemHolder) holder;
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            int childIndex = mIndexMap.get(position).getChildIndex();
            final FriendListBean.User subItem = mDataList.get(groupIndex).getChildList().get(childIndex);
            subHolder.itemView.setTag(subItem);
            GlideApp.with(mContext).load(subItem.getAvatar() + Utils.OSSImageSize(100)).into(subHolder.icon);
            subHolder.name.setText(subItem.getName());
            subHolder.position.setText(subItem.getJobName());
            subHolder.phone.setText(subItem.getPhone());
            if (StringUtils.isNullOrEmpty(subItem.getApplyId())){
                subHolder.agreed.setVisibility(View.GONE);
                subHolder.agree.setVisibility(View.GONE);
            }else {
                if (subItem.getStatus() != 1){
                    subHolder.agreed.setVisibility(View.VISIBLE);
                    subHolder.agree.setVisibility(View.GONE);
                }else {
                    subHolder.agree.setVisibility(View.VISIBLE);
                    subHolder.agreed.setVisibility(View.GONE);
                    subHolder.agree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (agreeApplyClickListener != null){
                                agreeApplyClickListener.onAgreeApplyClick(subItem);
                            }
                        }
                    });
                }
            }
        }

    }



    static class TitleItemHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView enterpriseName;
        TextView length;

        TitleItemHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_avatar);
            enterpriseName = itemView.findViewById(R.id.tv_enterprisename);
            length = itemView.findViewById(R.id.tv_length);
        }
    }

    static class SubItemHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView name;
        TextView position;
        TextView phone;
        TextView agree;
        TextView agreed;

        SubItemHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_icon);
            name = itemView.findViewById(R.id.tv_name);
            position = itemView.findViewById(R.id.tv_position);
            phone = itemView.findViewById(R.id.tv_phone);
            agree = itemView.findViewById(R.id.tv_status_agree);
            agreed = itemView.findViewById(R.id.tv_status_agreed);
        }
    }

    public interface OnSubItemClickListener{
        void onItemClick(FriendListBean.User user);
    }

    public interface OnSubItemLongClickListener{
        void onItemLongClick(FriendListBean.User user);
    }

    public interface OnSubItemAgreeApplyClickListener{
        void onAgreeApplyClick(FriendListBean.User user);
    }
}
