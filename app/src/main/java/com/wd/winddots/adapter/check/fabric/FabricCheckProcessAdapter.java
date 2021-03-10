package com.wd.winddots.adapter.check.fabric;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.check.bean.CheckGoodsBean;
import com.wd.winddots.desktop.view.PinnedHeaderRecyclerView.ExpandGroupItemEntity;
import com.wd.winddots.entity.FabricCheckLotInfo;
import com.wd.winddots.entity.FabricCheckTask;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 面料盘点任务处理
 *
 * @author zhou
 */
public class FabricCheckProcessAdapter extends BaseQuickAdapter<FabricCheckTask, BaseViewHolder> {

    public FabricCheckProcessAdapter(int layoutResId, @Nullable List<FabricCheckTask> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckTask item) {
        String goodsInfo = Utils.nullOrEmpty(item.getGoodsName()) + "(" + Utils.nullOrEmpty(item.getGoodsNo()) + ")";
        helper.setText(R.id.tv_goods_info, goodsInfo)
                .setText(R.id.tv_order_no, "#" + Utils.nullOrEmpty(item.getOrderNo()))
                .setText(R.id.tv_theme, Utils.nullOrEmpty(item.getOrderTheme()))
                .setText(R.id.tv_related_company, Utils.nullOrEmpty(item.getRelatedCompanyShortName()));

        ImageView icon = helper.getView(R.id.sdv_goods_photo);
        String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(item.getGoodsPhotos());
        if (!TextUtils.isEmpty(goodsPhoto)) {
            GlideApp.with(mContext).load(goodsPhoto + Utils.OSSImageSize(200)).into(icon);
        } else {
            icon.setImageResource(R.mipmap.icon_default_goods);
        }
    }


}
