package com.wd.winddots.activity.work;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {

    private Context mContext;

    private List<DeliveryBean> list;

    public DeliveryAdapter(Context context,List<DeliveryBean> deliveryBeans){
        mContext = context;
        list = deliveryBeans;
    }

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerItemClickListener.onItemClick(position);
            }
        });

        DeliveryBean deliveryBean = list.get(position);
        holder.title.setText(deliveryBean.deliveryName);

        GlideApp.with(mContext)
                .load(deliveryBean.iconUrl)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .into(holder.itemIcon);
        if (deliveryBean.isCheck){
            holder.tvModify.setVisibility(View.GONE);
            holder.tvCheck.setText("已确认");
            holder.tvCheck.setTextColor(ContextCompat.getColor(mContext,R.color.colorLoginBtnPressedBg));
            holder.tvCheck.setBackgroundResource(R.drawable.bg_item_deliveryed);
        }else {
            holder.tvModify.setVisibility(View.VISIBLE);
            holder.tvCheck.setText("未确认");
            holder.tvCheck.setTextColor(ContextCompat.getColor(mContext,R.color.operate_agree));
            holder.tvCheck.setBackgroundResource(R.drawable.bg_item_delivery_do);
        }
        holder.type.setText("业务类型："+deliveryBean.deliveryType);
        holder.company.setText("往来单位："+deliveryBean.company);
        holder.applicant.setText("申请人："+deliveryBean.applicant);
        holder.tvDate.setText("申请时间："+deliveryBean.applyDate);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size():0;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.rl_item_delivery)
        RelativeLayout rlItem;

        @BindView(R.id.item_icon)
        ImageView itemIcon;

        @BindView(R.id.tv_modify)
        TextView tvModify;

        @BindView(R.id.item_title)
        TextView title;

        @BindView(R.id.item_type)
        TextView type;

        @BindView(R.id.item_company)
        TextView company;

        @BindView(R.id.item_person)
        TextView applicant;

        @BindView(R.id.item_time)
        TextView tvDate;

        @BindView(R.id.tv_check)
        TextView tvCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
