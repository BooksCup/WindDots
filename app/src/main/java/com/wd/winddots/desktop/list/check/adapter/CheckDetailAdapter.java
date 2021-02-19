package com.wd.winddots.desktop.list.check.adapter;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.check.bean.CheckDetailBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: CheckDetailAdapter
 * Author: 郑
 * Date: 2021/1/15 11:49 AM
 * Description:
 */
public class CheckDetailAdapter extends BaseQuickAdapter<CheckDetailBean.CheckDetailItem, BaseViewHolder> {
    public CheckDetailAdapter(int layoutResId, @Nullable List<CheckDetailBean.CheckDetailItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckDetailBean.CheckDetailItem item) {
        String questionName = Utils.nullOrEmpty(item.getTag());
        String levelA = Utils.numberNullOrEmpty(item.getTagATimes());
        String levelB = Utils.numberNullOrEmpty(item.getTagBTimes());
        String levelC = Utils.numberNullOrEmpty(item.getTagCTimes());
        String levelD = Utils.numberNullOrEmpty(item.getTagDTimes());

        helper.setText(R.id.tv_question_name, questionName)
                .setText(R.id.tv_level_a, levelA)
                .setText(R.id.tv_level_b, levelB)
                .setText(R.id.tv_level_c, levelC)
                .setText(R.id.tv_level_d, levelD);


       String imageUrl = null;
        try {
            List<String> imageList = JSON.parseArray(item.getImage(),String.class);
            if (imageList.size() > 0){
                imageUrl = imageList.get(0);
            }
        }catch (Exception ignored){

        }
        SimpleDraweeView icon = helper.getView(R.id.iv_icon);
        if (null != imageUrl){
            icon.setImageURI(Uri.parse(imageUrl + Utils.OSSImageSize(100)));
        }else {
            icon.setImageResource(R.mipmap.default_img);
        }
        Log.e("net666",item.getImage());
    }
}
