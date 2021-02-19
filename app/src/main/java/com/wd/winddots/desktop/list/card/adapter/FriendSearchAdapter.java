package com.wd.winddots.desktop.list.card.adapter;

import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.card.bean.FriendSrarchBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FriendSearchAdapter
 * Author: 郑
 * Date: 2020/8/31 11:14 AM
 * Description:
 */
public class FriendSearchAdapter extends BaseQuickAdapter<FriendSrarchBean.FriendSrarchItem, BaseViewHolder> {

    private String mSearchKey;

    public void setSearchKey(String searchKey){
        mSearchKey = searchKey;
    }

    public FriendSearchAdapter(int layoutResId, @Nullable List<FriendSrarchBean.FriendSrarchItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendSrarchBean.FriendSrarchItem item) {
        ImageView icon = helper.getView(R.id.iv_icon);

        if (!StringUtils.isNullOrEmpty(item.getUserNameHl()) && !StringUtils.isNullOrEmpty(mSearchKey)){
            SpannableString textSpanned1 = new SpannableString(item.getUserName());
            helper.setText(R.id.tv_name, Html.fromHtml(item.getUserNameHl()));
        }else {
            helper.setText(R.id.tv_name, Utils.nullOrEmpty(item.getUserName()));
        }

        if (!StringUtils.isNullOrEmpty(item.getJobNameHl()) && !StringUtils.isNullOrEmpty(mSearchKey)){
            SpannableString textSpanned1 = new SpannableString(item.getJobName());
            helper.setText(R.id.tv_position, Html.fromHtml(item.getJobNameHl()));
        }else {
            helper.setText(R.id.tv_position, Utils.nullOrEmpty(item.getJobName()));
        }

        if (!StringUtils.isNullOrEmpty(item.getEnterpriseNameHl()) && !StringUtils.isNullOrEmpty(mSearchKey)){
            SpannableString textSpanned1 = new SpannableString(item.getEnterpriseName());
            helper.setText(R.id.tv_company, Html.fromHtml(item.getEnterpriseNameHl()));
        }else {
            helper.setText(R.id.tv_company, Utils.nullOrEmpty(item.getEnterpriseName()));
        }
        GlideApp.with(mContext).load(item.getUserAvatar() + Utils.OSSImageSize(100)).placeholder(R.mipmap.default_img).into(icon);
    }
}
