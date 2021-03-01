package com.wd.winddots.activity.work;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wd.winddots.GlideApp;
import com.wd.winddots.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailRelatedOrderAdapter extends RecyclerView.Adapter<DetailRelatedOrderAdapter.ViewHolder> {
    Context mContext;
    private List<RelatedOrderBean> list;
    public DetailRelatedOrderAdapter(Context context){
        this.mContext = context;
    }

    public void setList(List<RelatedOrderBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_related_order_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RelatedOrderBean relatedOrderBean = list.get(position);
        GlideApp.with(mContext)
                .load(relatedOrderBean.iconUrl)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .into(holder.itemIcon);

        holder.itemTitle.setText(relatedOrderBean.orderName);
        holder.itemCompany.setText(relatedOrderBean.company);
        holder.itemOrderNo.setText(relatedOrderBean.orderNo);
        holder.itemOrderNum.setText(relatedOrderBean.num);
        holder.itemOrderDate.setText(relatedOrderBean.orderDate);
        holder.itemOrderType.setText(relatedOrderBean.orderType);

        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(mContext);
        LinearLayoutManager layoutManager = new  LinearLayoutManager(mContext);
        holder.infoRecycler.setLayoutManager(layoutManager);
        holder.infoRecycler.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        holder.infoRecycler.setAdapter(orderInfoAdapter);
        if (relatedOrderBean.beanList!=null){
            orderInfoAdapter.setList(relatedOrderBean.beanList);
        }
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.order_info_recycler)
        RecyclerView infoRecycler;

        @BindView(R.id.item_icon)
        ImageView itemIcon;

        @BindView(R.id.item_title)
        TextView itemTitle;

        @BindView(R.id.item_company)
        TextView itemCompany;

        @BindView(R.id.item_order_no)
        TextView itemOrderNo;

        @BindView(R.id.item_order_num)
        TextView itemOrderNum;

        @BindView(R.id.item_order_date)
        TextView itemOrderDate;

        @BindView(R.id.item_order_type)
        TextView itemOrderType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
