package com.wd.winddots.mvp.widget.adapter.rv;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.PrivateChatListBean;

import java.util.List;

public class PrivateChatTabAdapter extends BaseQuickAdapter<PrivateChatListBean, BaseViewHolder> {
    public PrivateChatTabAdapter(int layoutResId, @Nullable List<PrivateChatListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PrivateChatListBean item) {
        GlideApp.with(mContext)
                .load(item.getUserAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(((ImageView) helper.getView(R.id.item_privatechat_userAvatar_iv)));

        String singleJobName = item.getSingleJobName();
        String departmentName = item.getDepartmentName();
        helper.setText(R.id.item_privatechat_short_and_department_name_tv,singleJobName == null ? "" : singleJobName + " " + departmentName == null ? "" : departmentName)
                .setText(R.id.item_privatechat_singlejobname_tv,item.getSingleJobName() == null ? "" : item.getSingleJobName())
                .setText(R.id.item_privatechat_username_tv,item.getSingleName() == null ? "" : item.getSingleName())
                .setText(R.id.item_privatechat_title_tv,item.getText() == null ? "" : item.getText())
                .setText(R.id.item_privatechat_time_tv,item.getCreateTime() == null ? "" : item.getCreateTime());

    }
}
