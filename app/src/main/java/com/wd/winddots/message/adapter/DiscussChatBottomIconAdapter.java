package com.wd.winddots.message.adapter;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.DiscussChatBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: DiscussChatBottomIconAdapter
 * Author: 郑
 * Date: 2020/9/25 10:54 AM
 * Description:
 */
public class DiscussChatBottomIconAdapter extends BaseQuickAdapter<DiscussChatBean.DataBean.QuestionUsersBean, BaseViewHolder> {
    public DiscussChatBottomIconAdapter(int layoutResId, @Nullable List<DiscussChatBean.DataBean.QuestionUsersBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscussChatBean.DataBean.QuestionUsersBean item) {
        ImageView icon = helper.getView(R.id.iv_icon);
        if ("group".equals(item.getType())){
            icon.setImageResource(R.mipmap.group_members);
        }else {
            GlideApp.with(mContext).
                    load(item.getUserAvatar() + Utils.OSSImageSize(200)).
                    apply(RequestOptions.bitmapTransform(new CircleCrop())).
                    into(icon);
        }

    }
}
