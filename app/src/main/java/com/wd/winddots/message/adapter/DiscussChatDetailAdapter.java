package com.wd.winddots.message.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.DiscussChatBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: DiscussChatDetailAdapter
 * Author: 郑
 * Date: 2020/6/15 3:28 PM
 * Description:
 */
public class DiscussChatDetailAdapter extends BaseQuickAdapter<DiscussChatBean.DataBean.QuestionUsersBean, BaseViewHolder> {
    public DiscussChatDetailAdapter(int layoutResId, @Nullable List<DiscussChatBean.DataBean.QuestionUsersBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscussChatBean.DataBean.QuestionUsersBean item) {
        ImageView icon = helper.getView(R.id.iv_icon);
        if (StringUtils.isNullOrEmpty(item.getUserId())){
            helper.setText(R.id.tv_name,"");
            icon.setImageResource(R.mipmap.add2);
            return;
        }
        helper.setText(R.id.tv_name,item.getUserName());
        GlideApp.with(mContext).load(item.getUserAvatar() + Utils.OSSImageSize(200)).into(icon);
    }
}
