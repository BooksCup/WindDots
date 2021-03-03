package com.wd.winddots.adapter.work;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.entity.App;


import java.util.List;

import androidx.annotation.Nullable;

public class AppAdapter extends BaseQuickAdapter<App, BaseViewHolder> {

    public AppAdapter(int layoutResId, @Nullable List<App> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, App item) {
        SimpleDraweeView mIconSdv = helper.getView(R.id.sdv_icon);
        if (!TextUtils.isEmpty(item.getIcon())) {
            mIconSdv.setImageURI(Uri.parse(item.getIcon()));
        }
        helper.setText(R.id.tv_name, item.getName());
    }
}
