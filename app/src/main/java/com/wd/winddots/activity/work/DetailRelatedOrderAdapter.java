package com.wd.winddots.activity.work;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wd.winddots.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailRelatedOrderAdapter extends RecyclerView.Adapter<DetailRelatedOrderAdapter.ViewHolder> {
    Context mContext;
    public DetailRelatedOrderAdapter(Context context){
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_related_order_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter();
        LinearLayoutManager layoutManager = new  LinearLayoutManager(mContext);
        holder.infoRecycler.setLayoutManager(layoutManager);
        holder.infoRecycler.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        holder.infoRecycler.setAdapter(orderInfoAdapter);
    }

    @Override
    public int getItemCount() {
        return 2;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.order_info_recycler)
        RecyclerView infoRecycler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
