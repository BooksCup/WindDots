package com.wd.winddots.adapter.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.activity.work.OnRecyclerItemClickListener;
import com.wd.winddots.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片选择器
 *
 * @author zhou
 */
public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.ViewHolder> {

    private Context mContext;
    private List<ImageEntity> mImageEntityList;

    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public ImagePickerAdapter(Context context) {
        mContext = context;
        mImageEntityList = new ArrayList<>();
    }

    public void setList(List<ImageEntity> imageEntityList) {
        this.mImageEntityList = imageEntityList;
        notifyDataSetChanged();
    }

    public List<ImageEntity> getList() {
        return mImageEntityList;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_picker, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ImageEntity imageEntity = mImageEntityList.get(position);
        if (position == mImageEntityList.size() - 1) {
            holder.mImageIv.setImageResource(R.mipmap.group_menber_add);
        } else {
            GlideApp.with(mContext)
                    .load(imageEntity.getUrl())
                    .placeholder(R.mipmap.default_img)
                    .error(R.mipmap.default_img)
                    .into(holder.mImageIv);
        }

        holder.itemView.setOnClickListener(v -> {
            if (mOnRecyclerItemClickListener != null) {
                mOnRecyclerItemClickListener.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mImageEntityList != null ? mImageEntityList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView mImageIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
