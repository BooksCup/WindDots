package com.wd.winddots.adapter.stock.in;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.entity.GoodsSpec;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsSpecAdapter extends RecyclerView.Adapter<GoodsSpecAdapter.ViewHolder> {

    private Context mContext;
    private List<GoodsSpec> mGoodsSpecList;

    public GoodsSpecAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<GoodsSpec> goodsSpecList) {
        this.mGoodsSpecList = goodsSpecList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_spec, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsSpec goodsSpec = mGoodsSpecList.get(position);
    }

    @Override
    public int getItemCount() {
        return mGoodsSpecList != null ? mGoodsSpecList.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_color)
        TextView itemColor;

        @BindView(R.id.item_size)
        TextView itemSize;

        @BindView(R.id.item_num)
        TextView itemNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
