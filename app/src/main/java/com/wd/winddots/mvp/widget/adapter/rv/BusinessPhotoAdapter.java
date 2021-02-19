package com.wd.winddots.mvp.widget.adapter.rv;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;

import java.util.List;

public class BusinessPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public BusinessPhotoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideApp.with(mContext)
                .load(item)
                .into(((ImageView) helper.getView(R.id.item_business_photo_iv)));
    }
}
