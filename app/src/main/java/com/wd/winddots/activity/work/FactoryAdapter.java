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

public class FactoryAdapter extends RecyclerView.Adapter<FactoryAdapter.ViewHolder>{

    private Context mContext;

    public FactoryAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_factory, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FactoryInfoAdapter factoryInfoAdapter = new FactoryInfoAdapter();
        LinearLayoutManager layoutManager = new  LinearLayoutManager(mContext);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        holder.recyclerView.setAdapter(factoryInfoAdapter);
    }

    @Override
    public int getItemCount() {
        return 3;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.factory_info_recycler)
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
