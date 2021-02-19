package com.wd.winddots.mvp.widget.adapter.rv;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.GroupChatListBean;

import java.util.List;

public class GroupChatTabAdapter extends BaseQuickAdapter<GroupChatListBean.DataBean, BaseViewHolder> {
    public GroupChatTabAdapter(int layoutResId, @Nullable List<GroupChatListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupChatListBean.DataBean item) {
        GlideApp.with(mContext)
                .load(item.getQuestionUsers().get(0).getReduceAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(((ImageView) helper.getView(R.id.item_groupchat_userAvatar_iv)));

        String lastTime = item.getLastTime();
        String substring = lastTime.substring(5, lastTime.length() - 3);
        helper.setText(R.id.item_groupchat_title_tv,item.getTitle())
                .setText(R.id.item_groupchat_personsum_tv,item.getQuestionUsers().size() + "人")
                .setText(R.id.item_groupchat_content_tv,item.getLastContent())
                .setText(R.id.item_groupchat_time_tv,substring);

    }
}
