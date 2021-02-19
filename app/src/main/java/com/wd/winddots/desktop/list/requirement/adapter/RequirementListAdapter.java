package com.wd.winddots.desktop.list.requirement.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.requirement.bean.RequirementListBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: RequirementListAdapter
 * Author: 郑
 * Date: 2020/12/10 9:58 AM
 * Description: 需求列表
 */
public class RequirementListAdapter extends BaseQuickAdapter<RequirementListBean.RequirementItem, BaseViewHolder> {
    public RequirementListAdapter(int layoutResId, @Nullable List<RequirementListBean.RequirementItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RequirementListBean.RequirementItem item) {

        ImageView icon = helper.getView(R.id.icon);
        if (item.getPhoto() != null) {
            String imageurl =  item.getPhoto();
            GlideApp.with(mContext).load(imageurl + Utils.OSSImageSize(200)).into(icon);
        }else {
            icon.setImageResource(R.mipmap.default_img);
        }
        String title = Utils.nullOrEmpty(item.getMaterialNumber()) + " (" + Utils.nullOrEmpty(item.getGoodsNo())+ ")";
        String title1 = "品名: "  + Utils.nullOrEmpty(item.getGoodsName());
        String title2 = "主题: "  + Utils.nullOrEmpty(item.getThemeTitle());
        String title3 = "数量: "  + Utils.nullOrEmpty(item.getApplyCount()) + Utils.nullOrEmpty(item.getGoodsUnit());
        String type = "S".equals(item.getType()) ? "销售" : "采购";
        helper.setText(R.id.tv_1,title).
                setText(R.id.tv_2,title1).
                setText(R.id.tv_3,title2).
                setText(R.id.tv_4,title3).
                setText(R.id.tv_status,type);
    }
}
