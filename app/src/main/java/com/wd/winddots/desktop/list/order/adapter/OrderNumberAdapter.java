package com.wd.winddots.desktop.list.order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.desktop.list.order.bean.OrderNumberBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.RecyclerExpandBaseAdapter;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: OrderNumberAdapter
 * Author: 郑
 * Date: 2020/8/11 10:37 AM
 * Description:
 */
public class OrderNumberAdapter extends RecyclerExpandBaseAdapter<OrderNumberBean.DeliveryTime, OrderNumberBean.DeliveryTimeDetailItem, RecyclerView.ViewHolder> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM_TIME) {
            OrderNumberAdapter.TitleItemHolder holder = new OrderNumberAdapter.TitleItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_number_header, parent, false));
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
            OrderNumberAdapter.SubItemHolder itemHolder = new OrderNumberAdapter.SubItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_detail_stock_item, parent, false));
            return itemHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            OrderNumberAdapter.TitleItemHolder itemHolder = (OrderNumberAdapter.TitleItemHolder) holder;
            itemHolder.itemView.setTag(mDataList.get(groupIndex));
            OrderNumberBean.DeliveryTime hederItem = mDataList.get(groupIndex).getParent();
            if ("header".equals(hederItem.getViewType())) {
                itemHolder.body.setVisibility(View.GONE);
                itemHolder.header.setVisibility(View.VISIBLE);
            } else {
                itemHolder.body.setVisibility(View.VISIBLE);
                itemHolder.header.setVisibility(View.GONE);
                itemHolder.headerTv1.setText(hederItem.getName1());
                itemHolder.headerTv2.setText(hederItem.getName2());
                itemHolder.headerTv3.setText(hederItem.getName3());
            }

        } else {
            OrderNumberAdapter.SubItemHolder subHolder = (OrderNumberAdapter.SubItemHolder) holder;
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            int childIndex = mIndexMap.get(position).getChildIndex();
            OrderNumberBean.DeliveryTimeDetailItem subItem = mDataList.get(groupIndex).getChildList().get(childIndex);

            subHolder.body.setVisibility(View.VISIBLE);
            subHolder.header.setVisibility(View.GONE);

            subHolder.contentTv1.setText(subItem.getName1());
            subHolder.contentTv2.setText(subItem.getName2());
            if (subItem.getType() == 2) {
                subHolder.contentTv3.setText(subItem.getName3());
                subHolder.contentTv3.setVisibility(View.VISIBLE);
            }else {
                subHolder.contentTv3.setVisibility(View.GONE);
            }
            subHolder.contentTv4.setVisibility(View.GONE);
            subHolder.contentTv5.setVisibility(View.GONE);

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

        LinearLayout header;
        LinearLayout body;


        TextView headerTv1;
        TextView headerTv2;
        TextView headerTv3;

        TitleItemHolder(View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.ll_list_header);
            header = itemView.findViewById(R.id.ll_header);
            headerTv1 = itemView.findViewById(R.id.tv_title1);
            headerTv2 = itemView.findViewById(R.id.tv_title2);
            headerTv3 = itemView.findViewById(R.id.tv_title3);
        }
    }

    static class SubItemHolder extends RecyclerView.ViewHolder {

        LinearLayout header;
        LinearLayout body;

        TextView headerTv1;
        TextView headerTv2;
        TextView headerTv3;
        TextView headerTv4;
        TextView headerTv5;

        TextView contentTv1;
        TextView contentTv2;
        TextView contentTv3;
        TextView contentTv4;
        TextView contentTv5;

        View line;

        SubItemHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.ll_header);
            body = itemView.findViewById(R.id.ll_body);

            headerTv1 = itemView.findViewById(R.id.tv_name1);
            headerTv2 = itemView.findViewById(R.id.tv_name2);
            headerTv3 = itemView.findViewById(R.id.tv_name3);
            headerTv4 = itemView.findViewById(R.id.tv_name4);
            headerTv5 = itemView.findViewById(R.id.tv_name5);

            contentTv1 = itemView.findViewById(R.id.tv_content1);
            contentTv2 = itemView.findViewById(R.id.tv_content2);
            contentTv3 = itemView.findViewById(R.id.tv_content3);
            contentTv4 = itemView.findViewById(R.id.tv_content4);
            contentTv5 = itemView.findViewById(R.id.tv_content5);

            line = itemView.findViewById(R.id.line);
        }
    }


}
