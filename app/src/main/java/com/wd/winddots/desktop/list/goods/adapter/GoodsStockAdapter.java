package com.wd.winddots.desktop.list.goods.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.desktop.list.goods.bean.GoodsStockBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.RecyclerExpandBaseAdapter;
import com.wd.winddots.utils.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: GoodsStockAdapter
 * Author: 郑
 * Date: 2020/7/29 10:33 AM
 * Description: 库存记录
 */
public class GoodsStockAdapter extends RecyclerExpandBaseAdapter<GoodsStockBean.StockRecordItem, GoodsStockBean.StockItem, RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM_TIME) {
            GoodsStockAdapter.TitleItemHolder holder = new GoodsStockAdapter.TitleItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_detail_stock_header, parent, false));
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
            GoodsStockAdapter.SubItemHolder itemHolder = new GoodsStockAdapter.SubItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_detail_stock_item, parent, false));
            return itemHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            GoodsStockAdapter.TitleItemHolder itemHolder = (GoodsStockAdapter.TitleItemHolder) holder;
            itemHolder.itemView.setTag(mDataList.get(groupIndex));
            GoodsStockBean.StockRecordItem hederItem = mDataList.get(groupIndex).getParent();
            if ("listHeader".equals(hederItem.getViewType())) {
                itemHolder.body.setVisibility(View.GONE);
            } else {
                itemHolder.body.setVisibility(View.VISIBLE);
                itemHolder.headerTv1.setText(Utils.nullOrEmpty(hederItem.getName()));
                itemHolder.headerTv2.setText("保管员: " + Utils.nullOrEmpty(hederItem.getContactName()) + " " + Utils.nullOrEmpty(hederItem.getContactPhone()));
                itemHolder.headerTv3.setText("地址: " + Utils.nullOrEmpty(hederItem.getArea()) + Utils.nullOrEmpty(hederItem.getAddress()));
            }

        } else {
            GoodsStockAdapter.SubItemHolder subHolder = (GoodsStockAdapter.SubItemHolder) holder;
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            int childIndex = mIndexMap.get(position).getChildIndex();
            GoodsStockBean.StockItem subItem = mDataList.get(groupIndex).getChildList().get(childIndex);
            if ("listHeader".equals(subItem.getViewType())){
                if (StringUtils.isNullOrEmpty(subItem.getX())) {
                    subHolder.body.setVisibility(View.GONE);
                    subHolder.header.setVisibility(View.VISIBLE);
                    subHolder.headerTv3.setVisibility(View.GONE);
                    subHolder.headerTv4.setVisibility(View.GONE);
                    subHolder.headerTv1.setText(subItem.getName1());
                    subHolder.headerTv2.setText(subItem.getName2());
                    subHolder.headerTv5.setText("数量");
                    subHolder.line.setVisibility(View.GONE);
                    if (subItem.getType() == 1) {
                        subHolder.headerTv2.setVisibility(View.GONE);
                    } else {
                        subHolder.headerTv2.setVisibility(View.VISIBLE);
                    }
                } else {
                    subHolder.body.setVisibility(View.VISIBLE);
                    subHolder.header.setVisibility(View.GONE);
                    subHolder.contentTv3.setVisibility(View.GONE);
                    subHolder.contentTv4.setVisibility(View.GONE);
                    subHolder.contentTv1.setText(subItem.getName1());
                    subHolder.contentTv2.setText(subItem.getName2());
                    subHolder.contentTv5.setText(subItem.getName5());
                    subHolder.line.setVisibility(View.VISIBLE);
                    if (subItem.getType() == 1) {
                        subHolder.contentTv2.setVisibility(View.GONE);
                    } else {
                        subHolder.contentTv2.setVisibility(View.VISIBLE);
                    }
                }


            }else {
                if (StringUtils.isNullOrEmpty(subItem.getX())) {
                    subHolder.body.setVisibility(View.GONE);
                    subHolder.header.setVisibility(View.VISIBLE);
                    subHolder.headerTv2.setVisibility(View.VISIBLE);
                    subHolder.headerTv4.setVisibility(View.VISIBLE);
                    subHolder.headerTv1.setText(subItem.getName1());
                    subHolder.headerTv2.setText(subItem.getName2());
                    subHolder.headerTv3.setText(subItem.getName3());
                    subHolder.headerTv4.setText(subItem.getName4());
                    subHolder.headerTv5.setText("入库数量");
                    subHolder.line.setVisibility(View.GONE);
                    if (subItem.getType() == 1) {
                        subHolder.headerTv3.setVisibility(View.GONE);
                    } else {
                        subHolder.headerTv3.setVisibility(View.VISIBLE);
                    }
                } else {
                    subHolder.body.setVisibility(View.VISIBLE);
                    subHolder.header.setVisibility(View.GONE);
                    subHolder.contentTv2.setVisibility(View.VISIBLE);
                    subHolder.contentTv4.setVisibility(View.VISIBLE);
                    subHolder.contentTv1.setText(subItem.getName1());
                    subHolder.contentTv2.setText(subItem.getName2());
                    subHolder.contentTv3.setText(subItem.getName3());
                    subHolder.contentTv4.setText(subItem.getName4());
                    subHolder.contentTv5.setText(subItem.getName5());
                    subHolder.line.setVisibility(View.VISIBLE);
                    if (subItem.getType() == 1) {
                        subHolder.contentTv3.setVisibility(View.GONE);
                    } else {
                        subHolder.contentTv3.setVisibility(View.VISIBLE);
                    }
                }

            }



            //subHolder.contentTv.setText(subItem);
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

        LinearLayout body;

        TextView headerTv1;
        TextView headerTv2;
        TextView headerTv3;

        TitleItemHolder(View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.ll_body);
            headerTv1 = itemView.findViewById(R.id.tv_1);
            headerTv2 = itemView.findViewById(R.id.tv_2);
            headerTv3 = itemView.findViewById(R.id.tv_3);
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
