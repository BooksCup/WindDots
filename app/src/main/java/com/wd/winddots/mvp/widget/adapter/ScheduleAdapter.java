package com.wd.winddots.mvp.widget.adapter;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.ScheduleBena;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: ScheduleAdapter
 * Author: 郑
 * Date: 2020/6/3 10:45 AM
 * Description:
 */
public class ScheduleAdapter extends BaseQuickAdapter<ScheduleBena, BaseViewHolder> {
    public ScheduleAdapter(int layoutResId, @Nullable List<ScheduleBena> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScheduleBena item) {

        GlideApp.with(mContext)
                .load(item.getUserList().get(0).getAvatar() + Utils.OSSImageSize(100))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.item_schedule_icon));


        String startTime = "";
        String endTime = "";

        if (item.getScheduleStartTime().length() >= 16){
            if (item.getScheduleStartTime().substring(11, 16).equals("00:00")) {
                startTime = item.getScheduleStartTime().substring(5, 10);
            } else {
                startTime = item.getScheduleStartTime().substring(5, 16);
            }
        }
        if (item.getScheduleEndTime().length() >= 16){
            if (item.getScheduleEndTime().substring(11, 16).equals("00:00")) {
                endTime = item.getScheduleEndTime().substring(5, 10);
            } else {
                endTime = item.getScheduleEndTime().substring(5, 16);
            }
        }


        helper.setText(R.id.item_schedule_start,startTime)
                .setText(R.id.item_schedule_end,endTime)
                .setText(R.id.item_schedule_content,item.getScheduleTitle());

    }
}
