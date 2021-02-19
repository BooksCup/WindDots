package com.wd.winddots.desktop.list.order.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.order.bean.OrderListBean;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: OrderListAdapter
 * Author: 郑
 * Date: 2020/8/7 2:14 PM
 * Description: 订单列表
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderListBean.OrderListItem, BaseViewHolder> {

    private Context mContext;

    public OrderListAdapter(int layoutResId, @Nullable List<OrderListBean.OrderListItem> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.OrderListItem item) {
        ImageView icon = helper.getView(R.id.icon);
        if (item.getPhoto() != null) {
            String imageurl =  item.getPhoto();
            GlideApp.with(mContext).load(imageurl + Utils.OSSImageSize(200)).into(icon);
        }else {
            icon.setImageResource(R.mipmap.default_img);
        }

        String goodsNoName = Utils.nullOrEmpty(item.getGoodsNo()) + " (" + Utils.nullOrEmpty(item.getGoodsName())+ ")";
        String theme = mContext.getString(R.string.order_theme) + Utils.nullOrEmpty(item.getThemeTitle());
        String number = mContext.getString(R.string.order_number) + Utils.numberNullOrEmpty(item.getDeliveryCount()) + "/" + Utils.numberNullOrEmpty(item.getApplyCount());
        String dateS = Utils.nullOrEmpty(item.getCreateTime());
        if (dateS.length() > 10){
            dateS = dateS.substring(0,10);
        }else {
            dateS = "";
        }
        String date = mContext.getString(R.string.order_date) + dateS;

        String enterpriseId = SpHelper.getInstance(mContext).getEnterpriseId();
        String enterpriseName = "";
        if (enterpriseId.equals(item.getFromEnterpriseId())) {
            enterpriseName = Utils.nullOrEmpty(item.getToEnterpriseName());
        } else {
            enterpriseName = Utils.nullOrEmpty(item.getFromEnterpriseName());
        }
        String customer = mContext.getString(R.string.order_customer) + enterpriseName;



        String status = "未发送";

        if (!StringUtils.isNullOrEmpty(item.getSendStatus())){
            switch (item.getSendStatus()) {
                case "0":
                    status = "未发送";
                    break;
                case "1":
                    status = "发送中";
                    break;
                case "2":
                    status = "已确认";
                    break;
                default:
                    status = "未发送";
            }
        }


        helper.setText(R.id.tv_1,goodsNoName).
                setText(R.id.tv_2,theme).
                setText(R.id.tv_3,number).
                setText(R.id.tv_4,date).
                setText(R.id.tv_5,customer).
                setText(R.id.tv_status,status);
    }
}
