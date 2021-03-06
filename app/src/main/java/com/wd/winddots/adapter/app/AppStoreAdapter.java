package com.wd.winddots.adapter.app;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.App;

import java.util.List;

import androidx.annotation.Nullable;

public class AppStoreAdapter extends BaseQuickAdapter<App, BaseViewHolder> {

    public AppStoreAdapter(int layoutResId, @Nullable List<App> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final App item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_describe, item.getDescribes());

        SimpleDraweeView mIconSdv = helper.getView(R.id.sdv_icon);
        if (!TextUtils.isEmpty(item.getIcon())) {
            mIconSdv.setImageURI(Uri.parse(item.getIcon()));
        } else {
            mIconSdv.setImageResource(R.mipmap.default_user_avatar);
        }

        TextView mInstallTv = helper.getView(R.id.tv_install);
        TextView mInstalledTv = helper.getView(R.id.tv_installed);

        if (Constant.IS_INSTALL.equals(item.getIsInstall())) {
            // 已安装
            mInstallTv.setVisibility(View.GONE);
            mInstalledTv.setVisibility(View.VISIBLE);
        } else {
            // 未安装
            mInstallTv.setVisibility(View.VISIBLE);
            mInstalledTv.setVisibility(View.GONE);
        }

    }

}
