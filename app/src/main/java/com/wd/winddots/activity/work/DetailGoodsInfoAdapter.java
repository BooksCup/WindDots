package com.wd.winddots.activity.work;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wd.winddots.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailGoodsInfoAdapter  extends RecyclerView.Adapter<DetailGoodsInfoAdapter.ViewHolder> {

    private Context mContext;
    private List<GoodsInfoBean> list;

    public void setList(List<GoodsInfoBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public DetailGoodsInfoAdapter(Context context, List<GoodsInfoBean> infoBeans){
        mContext = context;
        list = infoBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_info_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsInfoBean infoBean = list.get(position);
        holder.itemColor.setText(infoBean.goodColor);
        holder.itemSize.setText(infoBean.goodSize);
        holder.itemDeliveryNum.setText(infoBean.deliveryNum+"");
        holder.itemCheckNum.setText(infoBean.checkNum+"");
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_color)
        TextView itemColor;

        @BindView(R.id.item_size)
        TextView itemSize;

        @BindView(R.id.item_delivery_num)
        TextView itemDeliveryNum;

        @BindView(R.id.item_check_num)
        TextView itemCheckNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
