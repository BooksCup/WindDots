package com.wd.winddots.adapter.select;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.entity.Order;
import com.wd.winddots.utils.CommonUtil;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 订单
 *
 * @author zhou
 */
public class OrderAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {

    public OrderAdapter(int layoutResId, @Nullable List<Order> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Order item) {

        String goodsInfo = item.getGoodsName() + "(" + item.getGoodsNo() + ")";
        helper.setText(R.id.tv_goods_info, goodsInfo)
                .setText(R.id.tv_related_company, item.getRelatedCompanyShortName())
                .setText(R.id.tv_order_no, "#" + item.getOrderNo())
                .setText(R.id.tv_theme, item.getOrderTheme());

        SimpleDraweeView mGoodsPhotoSdv = helper.getView(R.id.sdv_goods_photo);
        String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(item.getGoodsPhotos());
        if (!TextUtils.isEmpty(goodsPhoto)) {
            mGoodsPhotoSdv.setImageURI(Uri.parse(goodsPhoto));
        } else {
            mGoodsPhotoSdv.setImageResource(R.mipmap.icon_default_goods);
        }

    }

}
