package com.wd.winddots.message.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.components.users.UserBean;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.desktop.list.card.bean.FriendSrarchBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: AddGroupSearchAdapter
 * Author: 郑
 * Date: 2020/9/23 2:40 PM
 * Description:
 */
public class AddGroupSearchAdapter extends BaseQuickAdapter<FriendSrarchBean.FriendSrarchItem, BaseViewHolder> {


    public Activity activity;


    public AddGroupSearchAdapter(int layoutResId, @Nullable List<FriendSrarchBean.FriendSrarchItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final FriendSrarchBean.FriendSrarchItem item) {

        GlideApp.with(mContext).load(item.getUserAvatar()).into((ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_name, item.getUserName());
        helper.setText(R.id.tv_job_name, Utils.nullOrEmpty(item.getShortName()) + " " + Utils.nullOrEmpty(item.getDepartmentName()));

        ImageView visitingIcon = helper.getView(R.id.visiting_card_icon);
        visitingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                intent.putExtra("id", item.getUserId());

                activity.startActivity(intent);
            }
        });


        ImageView imageView = helper.getView(R.id.item_usersel_icon);
        if (item.isDisable()) {
            imageView.setImageResource(R.mipmap.selectgray);
        } else if (item.isSelect()) {
            imageView.setImageResource(R.mipmap.select);
        } else {
            imageView.setImageResource(R.mipmap.unselect);
        }
    }


}
