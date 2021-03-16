package com.wd.winddots.adapter.check.fabric;

import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.entity.ProblemImage;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FabricCheckLotBrowseImageAdapter
 * Author: 郑
 * Date: 2021/3/10 9:36 AM
 * Description:
 */
public class FabricCheckLotBrowseImageAdapter extends BaseQuickAdapter<ProblemImage, BaseViewHolder> {

    public FabricCheckLotBrowseImageAdapter(int layoutResId, @Nullable List<ProblemImage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProblemImage item) {

        ImageView icon = helper.getView(R.id.iv_image);

        String photo = "";
        int length = 0;
        if (item.getPhotos() != null && item.getPhotos().size() > 0){
            photo = item.getPhotos().get(0);
            length = item.getPhotos().size();
        }else {
            try {
                List<String> photos = JSON.parseArray(item.getImages(),String.class);
                if (photos.size() > 0){
                    photo = photos.get(0);
                    length = photos.size();
                    item.setPhotos(photos);
                }
            }catch (Exception ignored){
                Log.e("net666111",item.getImages());
            }
            Log.e("net666",photo);
        }




        if (!StringUtils.isNullOrEmpty(photo)) {
            GlideApp.with(mContext).load(photo + Utils.OSSImageSize(200)).into(icon);
        } else {
            icon.setImageResource(R.mipmap.icon_default_goods);
        }
        helper.setText(R.id.tv_count,length + "")
        .setText(R.id.tv_problem,Utils.nullOrEmpty(item.getTag()));

    }
}
