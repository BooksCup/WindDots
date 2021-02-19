package com.wd.winddots.desktop.list.quote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.quote.bean.QuoteDetailBean;
import com.wd.winddots.desktop.list.quote.bean.QuoteListBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.RecyclerExpandBaseAdapter;
import com.wd.winddots.utils.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: QuoteRecordDetailAdapter
 * Author: 郑
 * Date: 2020/7/21 11:44 AM
 * Description:
 */
public class QuoteRecordDetailAdapter extends RecyclerExpandBaseAdapter<QuoteDetailBean.QuoteDetailGroup, QuoteDetailBean.QuoteMaterialItem, RecyclerView.ViewHolder> {

    private Context mContext;

    public void setContext(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM_TIME) {
            QuoteRecordDetailAdapter.TitleItemHolder holder = new QuoteRecordDetailAdapter.TitleItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote_detail_header, parent, false));
            return holder;
        } else {
            QuoteRecordDetailAdapter.SubItemHolder itemHolder = new QuoteRecordDetailAdapter.SubItemHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote_detail_material, parent, false));
            return itemHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            TitleItemHolder itemHolder = (TitleItemHolder) holder;
            itemHolder.itemView.setTag(mDataList.get(groupIndex));
            QuoteDetailBean.QuoteDetailGroup group = mDataList.get(groupIndex).getParent();
            itemHolder.headerTv.setText(group.getTitle());
        } else {
            SubItemHolder subHolder = (SubItemHolder) holder;
            int groupIndex = mIndexMap.get(position).getGroupIndex();
            int childIndex = mIndexMap.get(position).getChildIndex();
            QuoteDetailBean.QuoteMaterialItem subItem = mDataList.get(groupIndex).getChildList().get(childIndex);
            String currency = Utils.getCurrencyByString(mContext, subItem.getCurrency());

            if ("price".equals(subItem.getDataType())) {
                subHolder.materialLayout.setVisibility(View.GONE);
                subHolder.priceLayout.setVisibility(View.VISIBLE);
                subHolder.costLayout.setVisibility(View.GONE);

                String title1 = Utils.nullOrEmpty(subItem.getTitle())+ ": " + Utils.numberNullOrEmpty(subItem.getPrice())  + currency;
                String title2 = "备注: " + Utils.nullOrEmpty(subItem.getRemark());
                subHolder.priceContentTv.setText(title1);
                subHolder.pricRemarkTv.setText(title2);

            }else if("cost".equals(subItem.getDataType())){
                subHolder.materialLayout.setVisibility(View.GONE);
                subHolder.priceLayout.setVisibility(View.GONE);
                subHolder.costLayout.setVisibility(View.VISIBLE);
                String title1 = subItem.getTitle() + ": " + Utils.numberNullOrEmpty(subItem.getPrice()) + currency;
                subHolder.costContentTv.setText(title1);
            } else {
                subHolder.materialLayout.setVisibility(View.VISIBLE);
                subHolder.priceLayout.setVisibility(View.GONE);
                subHolder.costLayout.setVisibility(View.GONE);
                String goodsJson = subItem.getGoodsJson();
                QuoteListBean.GoodsInfo goodsInfo;
                Gson gson = new Gson();
                try {
                    goodsInfo = gson.fromJson(goodsJson, QuoteListBean.GoodsInfo.class);
                } catch (Exception e) {
                    goodsInfo = new QuoteListBean.GoodsInfo();
                }
                String title1 = Utils.nullOrEmpty(goodsInfo.getGoodsName()) + Utils.nullOrEmpty(goodsInfo.getGoodsNo());
                String title2 = "用料位置: " + Utils.nullOrEmpty(subItem.getMaterialLocation());
                String title3 = "描述: " + Utils.nullOrEmpty(subItem.getDescription());
                String title4 = "单耗/损耗: " + Utils.numberNullOrEmpty(subItem.getPieceYardage()) + Utils.nullOrEmpty(goodsInfo.getGoodsUnit()) + " / " + Utils.numberNullOrEmpty(subItem.getWastage()) + "%";

                String title5 = "单价: " + Utils.numberNullOrEmpty(subItem.getMaterialPrice()) + currency;

                subHolder.textView1.setText(title1);
                subHolder.textView2.setText(title2);
                subHolder.textView3.setText(title3);
                subHolder.textView4.setText(title4);
                subHolder.textView5.setText(title5);
            }

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

        /*
         * 材料布局
         * */
        LinearLayout materialLayout;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;

        /*
         * 工艺布局
         * */
        LinearLayout priceLayout;
        TextView priceContentTv;
        TextView pricRemarkTv;

        /*
        * 费用布局
        * */
        LinearLayout costLayout;
        TextView costContentTv;

        SubItemHolder(View itemView) {
            super(itemView);
            materialLayout = itemView.findViewById(R.id.material);
            textView1 = itemView.findViewById(R.id.tv_1);
            textView2 = itemView.findViewById(R.id.tv_2);
            textView3 = itemView.findViewById(R.id.tv_3);
            textView4 = itemView.findViewById(R.id.tv_4);
            textView5 = itemView.findViewById(R.id.tv_5);

            priceLayout = itemView.findViewById(R.id.price);
            priceContentTv = itemView.findViewById(R.id.tv_price_content);
            pricRemarkTv = itemView.findViewById(R.id.tv_peice_remark);

            costLayout = itemView.findViewById(R.id.cost);
            costContentTv = itemView.findViewById(R.id.tv_cost_content);
        }
    }
}
