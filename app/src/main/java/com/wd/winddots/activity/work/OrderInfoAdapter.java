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

public class OrderInfoAdapter  extends RecyclerView.Adapter<OrderInfoAdapter.ViewHolder>{

    private Context mContext;

    private List<OrderInfoBean> list;

    public OrderInfoAdapter(Context context){
        mContext = context;
    }

    public void setList(List<OrderInfoBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_info, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderInfoBean bean = list.get(position);
        holder.itemColor.setText(bean.orderColor);
        holder.itemSize.setText(bean.orderSize);
        holder.itemOrderNum.setText(bean.orderNum+"");
        holder.itemDeliveryNum.setText(bean.deliveryNum+"");
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

        @BindView(R.id.item_order_num)
        TextView itemOrderNum;

        @BindView(R.id.item_delivery_num)
        TextView itemDeliveryNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
