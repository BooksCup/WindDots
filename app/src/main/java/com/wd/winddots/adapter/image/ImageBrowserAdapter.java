package com.wd.winddots.adapter.image;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.activity.work.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片浏览
 *
 * @author zhou
 */
public class ImageBrowserAdapter extends RecyclerView.Adapter<ImageBrowserAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mImageList;

    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public ImageBrowserAdapter(Context context) {
        mContext = context;
        mImageList = new ArrayList<>();
    }

    public void setList(List<String> imageList) {
        this.mImageList = imageList;
        notifyDataSetChanged();
    }

    public List<String> getList() {
        return mImageList;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public ImageBrowserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_browse, parent, false);
        return new ImageBrowserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageBrowserAdapter.ViewHolder holder, int position) {

//        ImageEntity imageEntity = mImageEntityList.get(position);
//        if (position == mImageEntityList.size() - 1) {
//            holder.mImageIv.setImageResource(R.mipmap.group_menber_add);
//        } else {
//            GlideApp.with(mContext)
//                    .load(imageEntity.getPath())
//                    .placeholder(R.mipmap.default_img)
//                    .error(R.mipmap.default_img)
//                    .into(holder.mImageIv);
//        }
//
//        holder.itemView.setOnClickListener(v -> {
//            if (mOnRecyclerItemClickListener != null) {
//                mOnRecyclerItemClickListener.onItemClick(position);
//            }
//        });

        String image = mImageList.get(position);
        if (!TextUtils.isEmpty(image)) {
            holder.mImageSdv.setImageURI(Uri.parse(image));
        }

        holder.itemView.setOnClickListener(v -> {
            if (mOnRecyclerItemClickListener != null) {
                mOnRecyclerItemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImageList != null ? mImageList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sdv_image)
        SimpleDraweeView mImageSdv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
