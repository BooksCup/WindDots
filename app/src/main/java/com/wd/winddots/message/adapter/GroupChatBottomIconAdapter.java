package com.wd.winddots.message.adapter;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.message.bean.GroupChatHistoryBean;
import com.wd.winddots.message.bean.GroupChatListBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: GroupChatBottomIconAdapter
 * Author: 郑
 * Date: 2020/9/24 5:34 PM
 * Description: 群聊底部头像
 */
public class GroupChatBottomIconAdapter extends BaseQuickAdapter<GroupChatHistoryBean.AvatarMapBean, BaseViewHolder> {
    public GroupChatBottomIconAdapter(int layoutResId, @Nullable List<GroupChatHistoryBean.AvatarMapBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupChatHistoryBean.AvatarMapBean item) {
        ImageView icon = helper.getView(R.id.iv_icon);

        if ("add".equals(item.getType())){
            icon.setImageResource(R.mipmap.icon_add);
        }else if ("delete".equals(item.getType())){
            icon.setImageResource(R.mipmap.group_members);
        }else {
            GlideApp.with(mContext).
                    load(item.getAvatar() + Utils.OSSImageSize(200)).
                    apply(RequestOptions.bitmapTransform(new CircleCrop())).
                    into(icon);
        }



    }
}
