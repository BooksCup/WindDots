package com.wd.winddots.adapter.stock.in;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.entity.StockInApply;
import com.wd.winddots.enums.StockApplyStatusEnum;
import com.wd.winddots.enums.StockBizTypeEnum;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.TimeUtil;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 入库申请
 *
 * @author zhou
 */
public class StockInApplyAdapter extends BaseQuickAdapter<StockInApply, BaseViewHolder> {

    private String mKeyword;

    public StockInApplyAdapter(int layoutResId, @Nullable List<StockInApply> data) {
        super(layoutResId, data);
    }

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }

    @Override
    protected void convert(BaseViewHolder helper, StockInApply item) {
        String stockBizType = StockBizTypeEnum.getValue(item.getBizType());
        if (!TextUtils.isEmpty(stockBizType)) {
            helper.setText(R.id.tv_biz_type, stockBizType);
        }
        String date = TimeUtil.formatTimeToDate(item.getCreateTime());
        String goodsInfo = item.getGoodsName() + "(" + item.getGoodsNo() + ")";
        helper.setText(R.id.tv_goods_info, goodsInfo)
                .setText(R.id.tv_create_user_name, item.getCreateUserName())
                .setText(R.id.tv_related_company, item.getExchangeEnterpriseName())
                .setText(R.id.tv_create_date, date);

        SimpleDraweeView mGoodsPhotoSdv = helper.getView(R.id.sdv_goods_photo);
        String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(item.getGoodsPhotos());
        if (!TextUtils.isEmpty(goodsPhoto)) {
            mGoodsPhotoSdv.setImageURI(Uri.parse(goodsPhoto));
        } else {
            mGoodsPhotoSdv.setImageResource(R.mipmap.icon_default_goods);
        }

        TextView mApplyStatusTv = helper.getView(R.id.tv_apply_status);
        // tag
        if (StockApplyStatusEnum.STOCK_APPLY_STATUS_DRAFT.getStatus().equals(item.getApplyStatus())) {

            // 草稿
            mApplyStatusTv.setVisibility(View.VISIBLE);
            mApplyStatusTv.setText("草稿单");
            mApplyStatusTv.setTextColor(mContext.getResources().getColor(R.color.tag_grey));
            mApplyStatusTv.setBackgroundResource(R.drawable.bg_tag_grey);

        } else if (StockApplyStatusEnum.STOCK_APPLY_STATUS_UNCONFIRMED.getStatus().equals(item.getApplyStatus())) {

            // 未确认
            mApplyStatusTv.setVisibility(View.VISIBLE);
            mApplyStatusTv.setText("未确认");
            mApplyStatusTv.setTextColor(mContext.getResources().getColor(R.color.tag_green));
            mApplyStatusTv.setBackgroundResource(R.drawable.bg_tag_green);

        } else if (StockApplyStatusEnum.STOCK_APPLY_STATUS_CONFIRMED.getStatus().equals(item.getApplyStatus())) {

            // 已确认
            mApplyStatusTv.setVisibility(View.VISIBLE);
            mApplyStatusTv.setText("已确认");
            mApplyStatusTv.setTextColor(mContext.getResources().getColor(R.color.tag_orange));
            mApplyStatusTv.setBackgroundResource(R.drawable.bg_tag_orange);

        } else {

            // 未知
            mApplyStatusTv.setVisibility(View.GONE);
        }

    }

}
