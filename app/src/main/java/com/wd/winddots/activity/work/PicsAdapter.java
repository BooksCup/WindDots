package com.wd.winddots.activity.work;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wd.winddots.GlideApp;
import com.wd.winddots.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicsAdapter extends RecyclerView.Adapter<PicsAdapter.ViewHolder> {

    private Context mContext;
    private List<PicBean> list;

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public PicsAdapter(Context context){
        mContext = context;
        list = new ArrayList<>();
    }

    public void setList(List<PicBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public List<PicBean> getList() {
        return list;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pics, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PicBean picBean = list.get(position);
        if (position==list.size()-1){
            holder.itemPic.setImageResource(R.mipmap.group_menber_add);
        }else {
            GlideApp.with(mContext)
                    .load(picBean.picUrl)
                    .placeholder(R.mipmap.default_img)
                    .error(R.mipmap.default_img)
                    .into(holder.itemPic);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener!=null){
                    onRecyclerItemClickListener.onItemClick(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_pic)
        ImageView itemPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
