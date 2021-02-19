package com.wd.winddots.desktop.list.goods.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.RecyclerExpandBaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: GoodsDetailAttrAdapter
 * Author: 郑
 * Date: 2020/7/28 11:55 AM
 * Description: 物品属性规格适配器
 */
public class GoodsDetailAttrAdapter extends RecyclerExpandBaseAdapter <String,String,RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM_TIME) {
            GoodsDetailAttrAdapter.TitleItemHolder holder = new GoodsDetailAttrAdapter.TitleItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote_detail_header, parent, false));
            return holder;
        } else {
            GoodsDetailAttrAdapter.SubItemHolder itemHolder = new GoodsDetailAttrAdapter.SubItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_detail_attr, parent, false));
            return itemHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            GoodsDetailAttrAdapter.TitleItemHolder itemHolder = (GoodsDetailAttrAdapter.TitleItemHolder) holder;
            itemHolder.itemView.setTag(mDataList.get(groupIndex));
            String group = mDataList.get(groupIndex).getParent();
            itemHolder.headerTv.setText(group);
        } else {
            GoodsDetailAttrAdapter.SubItemHolder subHolder = (GoodsDetailAttrAdapter.SubItemHolder) holder;
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            int childIndex = mIndexMap.get(position).getChildIndex();
            String subItem = mDataList.get(groupIndex).getChildList().get(childIndex);
            subHolder.contentTv.setText(subItem);
        }
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





    static class TitleItemHolder extends RecyclerView.ViewHolder {
        TextView headerTv;
        TitleItemHolder(View itemView) {
            super(itemView);
            headerTv = itemView.findViewById(R.id.tv_header);
        }
    }

    static class SubItemHolder extends RecyclerView.ViewHolder {


        TextView contentTv;

        SubItemHolder(View itemView) {
            super(itemView);
            contentTv = itemView.findViewById(R.id.tv_content);
        }
    }
}
