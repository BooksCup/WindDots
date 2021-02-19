package com.wd.winddots.message.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.message.bean.PrivateChatListBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

public class PrivateChatTabAdapter extends BaseQuickAdapter<PrivateChatListBean, BaseViewHolder> {
    public PrivateChatTabAdapter(int layoutResId, @Nullable List<PrivateChatListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PrivateChatListBean item) {
        GlideApp.with(mContext)
                .load(item.getUserAvatar() + Utils.OSSImageSize(100))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(((ImageView) helper.getView(R.id.item_privatechat_userAvatar_iv)));

        String singleJobName = item.getSingleJobName();
        String departmentName = Utils.nullOrEmpty(item.getShortName()) + " " + Utils.nullOrEmpty(item.getDepartmentName());
        String time = item.getCreateTime();
        if (!StringUtils.isNullOrEmpty(time) && time.length() > 16) {
            time = time.substring(5, 16);
        } else {
            time = "";
        }

        TextView unreadTv = helper.getView(R.id.tv_unread);
        if (0 == item.getUnread()){
            unreadTv.setVisibility(View.GONE);
        }else {
            unreadTv.setVisibility(View.VISIBLE);
        }

        helper.setText(R.id.item_privatechat_short_and_department_name_tv, singleJobName == null ? "" : singleJobName + " " + departmentName == null ? "" : departmentName)
                .setText(R.id.item_privatechat_singlejobname_tv, item.getSingleJobName() == null ? "" : item.getSingleJobName())
                .setText(R.id.item_privatechat_username_tv, item.getSingleName() == null ? "" : item.getSingleName())
                .setText(R.id.item_privatechat_title_tv, item.getText() == null ? "" : item.getText())
                .setText(R.id.item_privatechat_time_tv, time)
                .setText(R.id.tv_unread, item.getUnread() + "");

    }
}
