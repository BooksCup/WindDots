package com.wd.winddots.message.adapter;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.message.bean.GroupChatHistoryBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: GroupMenbersAdapter
 * Author: 郑
 * Date: 2020/10/20 11:56 AM
 * Description: 群成员信息
 */
public class GroupMenbersAdapter extends BaseQuickAdapter<GroupChatHistoryBean.AvatarMapBean, BaseViewHolder> {
    public GroupMenbersAdapter(int layoutResId, @Nullable List<GroupChatHistoryBean.AvatarMapBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupChatHistoryBean.AvatarMapBean item) {
        ImageView icon = helper.getView(R.id.iv_icon);

        if ("add".equals(item.getType())){
            icon.setImageResource(R.mipmap.group_menber_add);
            helper.setText(R.id.tv_name, "");
        }else if ("delete".equals(item.getType())){
            icon.setImageResource(R.mipmap.group_menber_deleted);
            helper.setText(R.id.tv_name, "");
        }else{
            GlideApp.with(mContext).
                    load(item.getAvatar() + Utils.OSSImageSize(200)).
                    into(icon);
            helper.setText(R.id.tv_name, Utils.nullOrEmpty(item.getName()));
        }




    }
}
