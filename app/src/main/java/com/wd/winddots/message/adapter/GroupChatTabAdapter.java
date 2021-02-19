package com.wd.winddots.message.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import okhttp3.internal.Util;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.message.bean.GroupChatListBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

public class GroupChatTabAdapter extends BaseQuickAdapter<GroupChatListBean.DataBean, BaseViewHolder> {
    public GroupChatTabAdapter(int layoutResId, @Nullable List<GroupChatListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupChatListBean.DataBean item) {
        GlideApp.with(mContext)
                .load(item.getQuestionUsers().get(0).getUserAvatar()+ Utils.OSSImageSize(100))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(((ImageView) helper.getView(R.id.item_groupchat_userAvatar_iv)));

        TextView unreadTv = helper.getView(R.id.tv_unread);
        if (0 == item.getUnReadCount()){
            unreadTv.setVisibility(View.GONE);
        }else {
            unreadTv.setVisibility(View.VISIBLE);
        }
        TextView atTv = helper.getView(R.id.item_groupchat_at_tv);
        TextView contentTv = helper.getView(R.id.item_groupchat_content_tv);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)contentTv.getLayoutParams();


        if ("0".equals(item.getUserGroupStatus())){
            atTv.setVisibility(View.GONE);
            params.leftMargin = Utils.dip2px(mContext,20);
        }else {
            atTv.setVisibility(View.VISIBLE);
            params.leftMargin = Utils.dip2px(mContext,3);
        }
        contentTv.setLayoutParams(params);

        String title = Utils.nullOrEmpty(item.getTitle());
        if (title.length() > 10) {
            title = title.substring(0,10) + "...";
        }
        String lastTime = item.getLastTime();
        String substring = lastTime.substring(5, lastTime.length() - 3);
        helper.setText(R.id.item_groupchat_title_tv,title)
                .setText(R.id.item_groupchat_personsum_tv,item.getQuestionUsers().get(0).getUserName() + " 等" + item.getQuestionUsers().size() + "人")
                .setText(R.id.item_groupchat_content_tv,item.getLastContent())
                .setText(R.id.item_groupchat_time_tv,substring)
                .setText(R.id.tv_unread,item.getUnReadCount() + "");

    }
}
