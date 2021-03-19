package com.wd.winddots.adapter.stock.out;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.entity.StockOutApply;
import com.wd.winddots.enums.StockApplyStatusEnum;
import com.wd.winddots.enums.StockBizTypeEnum;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.TimeUtil;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 出库申请
 *
 * @author zhou
 */
public class StockOutApplyAdapter extends BaseQuickAdapter<StockOutApply, BaseViewHolder> {

    private String mKeyword;

    public StockOutApplyAdapter(Context context, int layoutResId, @Nullable List<StockOutApply> data) {
        super(layoutResId, data);
        mContext = context;
    }

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }

    @Override
    protected void convert(BaseViewHolder helper, StockOutApply item) {
        String stockBizType = StockBizTypeEnum.getValue(item.getBizType());
        if (!TextUtils.isEmpty(stockBizType)) {
            helper.setText(R.id.tv_biz_type, stockBizType);
        }
        String date = TimeUtil.formatTimeToDate(item.getCreateTime());
        String goodsInfo = item.getGoodsName() + "(" + item.getGoodsNo() + ")";
        helper.setText(R.id.tv_goods_info, goodsInfo)
                .setText(R.id.tv_create_user_name, item.getCreateUserName())
                .setText(R.id.tv_create_date, date);

        if (StockBizTypeEnum.STOCK_BIZ_TYPE_OFFICE_SUPPLIES_IN.getType().equals(item.getBizType())) {
            String enterpriseName = SpHelper.getInstance(mContext).getUser().getEnterpriseName();
            if (!TextUtils.isEmpty(enterpriseName)) {
                helper.setText(R.id.tv_related_company, enterpriseName);
            } else {
                helper.setText(R.id.tv_related_company, "");
            }
        } else {
            helper.setText(R.id.tv_related_company, item.getExchangeEnterpriseName());
        }

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

        } else if (StockApplyStatusEnum.STOCK_APPLY_STATUS_REJECT.getStatus().equals(item.getApplyStatus())) {

            // 未通过
            mApplyStatusTv.setVisibility(View.VISIBLE);
            mApplyStatusTv.setText("未通过");
            mApplyStatusTv.setTextColor(mContext.getResources().getColor(R.color.tag_red));
            mApplyStatusTv.setBackgroundResource(R.drawable.bg_tag_red);

        } else {

            // 未知
            mApplyStatusTv.setVisibility(View.GONE);
        }

    }

}
