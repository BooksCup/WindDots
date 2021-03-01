package com.wd.winddots.activity.work;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wd.winddots.R;
import com.wd.winddots.desktop.list.goods.bean.GoodsStockBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsInfoAdapter extends RecyclerView.Adapter<GoodsInfoAdapter.ViewHolder>  {

    private Context mContext;
    private List<GoodsInfoBean> list;

    public GoodsInfoAdapter(Context context){
        mContext = context;
    }

    public void setGoodsInfoBeans(List<GoodsInfoBean> goodsInfoBeans) {
        this.list = goodsInfoBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_info, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsInfoBean goodsInfoBean = list.get(position);
        holder.itemColor.setText(goodsInfoBean.goodColor);
        holder.itemSize.setText(goodsInfoBean.goodSize);
        holder.itemNum.setText(goodsInfoBean.goodNum+"");
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

        @BindView(R.id.item_num)
        TextView itemNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
