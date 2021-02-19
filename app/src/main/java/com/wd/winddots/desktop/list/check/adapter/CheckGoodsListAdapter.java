package com.wd.winddots.desktop.list.check.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.check.bean.CheckGoodsBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.RecyclerExpandBaseAdapter;
import com.wd.winddots.utils.Utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: CheckGoodsListAdapter
 * Author: 郑
 * Date: 2021/1/29 9:20 AM
 * Description: 物品列表
 */
public class CheckGoodsListAdapter extends RecyclerExpandBaseAdapter<CheckGoodsBean.CheckGoodsItem, CheckGoodsBean.CheckGang, RecyclerView.ViewHolder> {

    private OnSubItemClickListener onSubItemClickListener;

    public void setOnSubItemClickListener(OnSubItemClickListener onSubItemClickListener) {
        this.onSubItemClickListener = onSubItemClickListener;
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
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_list, parent, false));
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
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_gang, parent, false));

            return itemHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            TitleItemHolder itemHolder = (TitleItemHolder) holder;
            itemHolder.itemView.setTag(mDataList.get(groupIndex));
            CheckGoodsBean.CheckGoodsItem goodsItem = mDataList.get(groupIndex).getParent();
            if (goodsItem.getGoods() == null) {
                itemHolder.goodsNameTv.setText("");
                itemHolder.icon.setImageResource(R.mipmap.default_img);
                itemHolder.attr1Tv.setText("");
                itemHolder.attr2Tv.setText("");
            } else {
                CheckGoodsBean.GoodsInfo goodsInfo = goodsItem.getGoods();
                itemHolder.goodsNameTv.setText(Utils.nullOrEmpty(goodsItem.getProductName()) + "(" + Utils.nullOrEmpty(goodsInfo.getGoodsNo()) + ")");
                Map<String, JSONArray> attrList = Utils.getMapForJson(goodsInfo.getAttrList());
                List<String> attrSList = new ArrayList<>();
                if (attrList != null) {
                    for (Map.Entry<String, JSONArray> entry : attrList.entrySet()) {
                        //获取key
                        String key = entry.getKey();
                        //获取value
                        JSONArray value = entry.getValue();
                        JSONObject valueMap = null;
                        try {
                            valueMap = value.getJSONObject(0);
                        } catch (Exception e) {
                        }
                        if (valueMap != null) {
                            try {
                                attrSList.add(key + ": " + valueMap.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (attrSList.size() >= 2) {
                    itemHolder.attr1Tv.setText(attrSList.get(0));
                    itemHolder.attr2Tv.setText(attrSList.get(1));
                } else if (attrSList.size() == 1) {
                    itemHolder.attr1Tv.setText(attrSList.get(0));
                }

                String goodsPhotosJson = goodsInfo.getGoodsNhotos();
                List<String> goodsPhotos = null;
                try {
                    goodsPhotos = JSON.parseArray(goodsPhotosJson, String.class);
                } catch (Exception e) {
                }
                if (goodsPhotos != null && goodsPhotos.size() > 0) {
                    String imageurl = "http:" + goodsPhotos.get(0);
                    itemHolder.icon.setImageURI(Uri.parse(imageurl));
                } else {
                    itemHolder.icon.setImageResource(R.mipmap.default_img);
                }

            }

        } else {
            SubItemHolder subHolder = (SubItemHolder) holder;
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            int childIndex = mIndexMap.get(position).getChildIndex();
            final CheckGoodsBean.CheckGang subItem = mDataList.get(groupIndex).getChildList().get(childIndex);
            CheckGoodsBean.CheckGoodsItem goods = mDataList.get(groupIndex).getParent();
            subItem.setGoodsName(goods.getProductName());
            if (goods.getGoods() != null) {
                subItem.setGoodsNo(goods.getGoods().getGoodsNo());
            }
            subHolder.itemView.setTag(subItem);
            //subHolder.goodsNameTv.setText(groupIndex + "");
            if (childIndex == 0) {
                subHolder.itemHeader.setVisibility(View.VISIBLE);
                subHolder.itemHeader.setText(subItem.getEnterprise().getSupplierName());
            } else {
                subHolder.itemHeader.setVisibility(View.GONE);
            }
            subHolder.gangTv.setText(Utils.nullOrEmpty(subItem.getCylinderNumber()));
            subHolder.lenghtTv.setText(Utils.nullOrEmpty(subItem.getTotalLength()) + Utils.nullOrEmpty(subItem.getLengthUnit()));
            subHolder.weightTv.setText(Utils.nullOrEmpty(subItem.getTotalWeight()) + Utils.nullOrEmpty(subItem.getWeightUnit()));
            subHolder.body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSubItemClickListener.onItemClick(subItem);
                }
            });
        }

    }


    static class TitleItemHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView icon;
        TextView goodsNameTv;
        TextView attr1Tv;
        TextView attr2Tv;

        TitleItemHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            goodsNameTv = itemView.findViewById(R.id.tv_goods_name);
            attr1Tv = itemView.findViewById(R.id.tv_attr1);
            attr2Tv = itemView.findViewById(R.id.tv_attr2);
        }
    }

    static class SubItemHolder extends RecyclerView.ViewHolder {

        TextView itemHeader;
        LinearLayout body;
        TextView gangTv;
        TextView weightTv;
        TextView lenghtTv;


        SubItemHolder(View itemView) {
            super(itemView);
            itemHeader = itemView.findViewById(R.id.tv_header);
            body = itemView.findViewById(R.id.ll_body);
            gangTv = itemView.findViewById(R.id.tv_gang);
            weightTv = itemView.findViewById(R.id.tv_weight);
            lenghtTv = itemView.findViewById(R.id.tv_length);
        }
    }

    public interface OnSubItemClickListener {
        void onItemClick(CheckGoodsBean.CheckGang gang);
    }


}
