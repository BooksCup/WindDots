package com.wd.winddots.schedule.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.components.users.UserBean;
import com.wd.winddots.entity.User;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: ScheduleUserAdapter
 * Author: 郑
 * Date: 2020/6/4 11:00 AM
 * Description:
 */
public class ScheduleUserAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public ScheduleUserAdapter(int layoutResId, @Nullable List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {

        GlideApp.with(mContext).load(item.getAvatar()+ Utils.OSSImageSize(100)).into((ImageView) helper.getView(R.id.item_schedule_icon));

        helper.setText(R.id.item_schedule_name,item.getName());

    }
}
