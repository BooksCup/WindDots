package com.wd.winddots.adapter.check.fabric;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kevin.photo_browse.PhotoBrowse;
import com.kevin.photo_browse.ShowType;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.check.view.SpinnerView;
import com.wd.winddots.entity.FabricCheckProblem;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FabricCheckTaskLotProcessPositionAdapter
 * Author: 郑
 * Date: 2021/2/23 11:47 AM
 * Description:
 */
public class FabricCheckProblemBrowseAdapter extends BaseQuickAdapter<FabricCheckProblem, BaseViewHolder> {


    private OnImageClickListener onImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public FabricCheckProblemBrowseAdapter(int layoutResId, @Nullable List<FabricCheckProblem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckProblem item) {

        String level = "A";
        if (!StringUtils.isNullOrEmpty(item.getTagATimes())){
            level = "A";
        }else if (!StringUtils.isNullOrEmpty(item.getTagBTimes())){
            level = "B";
        }else if (!StringUtils.isNullOrEmpty(item.getTagCTimes())){
            level = "C";
        }else {
            level = "D";
        }

        helper.setText(R.id.tv_problem, Utils.nullOrEmpty(item.getTag()))
                .setText(R.id.tv_level,level);
        ImageView icon = helper.getView(R.id.iv_icon);
        if (item.getImageEntities() != null && item.getImageEntities().size() > 0){
            GlideApp.with(mContext).load(item.getImageEntities().get(0).getUrl() + Utils.OSSImageSize(100)).into(icon);
        }else {
            icon.setImageResource(R.mipmap.icon_default_goods);
        }




        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (item.getImages() == null || item.getImages().size() == 0){
                    return;
                }
                if (null != onImageClickListener){
                    onImageClickListener.onImageDidClick(helper.getPosition());
                }
            }
        });
    }

    public interface OnImageClickListener{
        void onImageDidClick(int position);
    }


}
