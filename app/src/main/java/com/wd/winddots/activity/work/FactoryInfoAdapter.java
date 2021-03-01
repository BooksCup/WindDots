package com.wd.winddots.activity.work;


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

public class FactoryInfoAdapter  extends RecyclerView.Adapter<FactoryInfoAdapter.ViewHolder>{

    private List<FactoryInfoBean> list;

    public void setList(List<FactoryInfoBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_factory_info, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FactoryInfoBean factoryInfoBean = list.get(position);
        holder.itemColor.setText(factoryInfoBean.color);
        holder.itemSize.setText(factoryInfoBean.size);
        holder.itemFactoryNum.setText(factoryInfoBean.factoryNum+"");
        holder.itemDeliveryNum.setText(factoryInfoBean.deliveryNum+"");
        holder.itemAllocationNum.setText(factoryInfoBean.allocationNum+"");
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

        @BindView(R.id.item_factory_num)
        TextView itemFactoryNum;

        @BindView(R.id.item_delivery_num)
        TextView itemDeliveryNum;

        @BindView(R.id.item_allocation_num)
        TextView itemAllocationNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
