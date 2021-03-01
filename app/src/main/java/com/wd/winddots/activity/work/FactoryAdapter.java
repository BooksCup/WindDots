package com.wd.winddots.activity.work;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wd.winddots.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactoryAdapter extends RecyclerView.Adapter<FactoryAdapter.ViewHolder>{

    private Context mContext;

    private List<FactoryBean> list;

    public FactoryAdapter(Context context){
        mContext = context;
    }

    public void setList(List<FactoryBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_factory, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FactoryBean factoryBean = list.get(position);
        holder.itemTitle.setText(factoryBean.factoryName);
        holder.itemPerson.setText(factoryBean.personName);
        holder.itemPhone.setText(factoryBean.phoneNum);
        holder.itemAddress.setText(factoryBean.factoryAddress);

        FactoryInfoAdapter factoryInfoAdapter = new FactoryInfoAdapter();
        LinearLayoutManager layoutManager = new  LinearLayoutManager(mContext);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        holder.recyclerView.setAdapter(factoryInfoAdapter);

        factoryInfoAdapter.setList(factoryBean.beanList);
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.factory_info_recycler)
        RecyclerView recyclerView;

        @BindView(R.id.item_title)
        TextView itemTitle;

        @BindView(R.id.item_person)
        TextView itemPerson;

        @BindView(R.id.item_phone)
        TextView itemPhone;

        @BindView(R.id.item_address)
        TextView itemAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
