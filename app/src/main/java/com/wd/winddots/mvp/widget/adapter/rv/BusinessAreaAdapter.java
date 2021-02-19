package com.wd.winddots.mvp.widget.adapter.rv;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.BusinessBean;
import com.wd.winddots.utils.Utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class BusinessAreaAdapter extends BaseQuickAdapter<BusinessBean.MaterialsPrice, BaseViewHolder> {
    public BusinessAreaAdapter(int layoutResId, @Nullable List<BusinessBean.MaterialsPrice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessBean.MaterialsPrice item) {


        String priceChange = Utils.numberNullOrEmpty(item.getChange());
        Number n1;
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        try {
            n1  = numberFormat.parse(priceChange);
        } catch (Exception e) {
            n1 = 0;
            e.printStackTrace();
        }
        TextView changeTv = helper.getView(R.id.tv_change);
        if (n1.floatValue() > 0) {
            changeTv.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else if (n1.floatValue() < 0) {
            changeTv.setTextColor(mContext.getResources().getColor(R.color.colorStyleG));
        } else {
            changeTv.setTextColor(mContext.getResources().getColor(R.color.color50));
        }

        helper.setText(R.id.tv_name,Utils.nullOrEmpty(item.getName()))
                .setText(R.id.tv_price,Utils.nullOrEmpty(item.getLastTrade()))
                .setText(R.id.tv_change,Utils.nullOrEmpty(item.getChange()))
                .setText(R.id.tv_unit,Utils.nullOrEmpty(item.getUnit()));


//        GlideApp.with(mContext)
//                .load("http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/f61a270d94ca42959681e74df0b42507.png" + Utils.OSSImageSize(100))
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into(((ImageView) helper.getView(R.id.item_business_userAvatar_iv)));
//        String target = "";
//        if ("0".equals(item.getMomentsType())) {
//            target = "原材料 走势 价格";
//        } else if ("1".equals(item.getMomentsType())){
//            target = "汇率 外币";
//        }
//
//        helper.setText(R.id.item_business_title_tv, item.getMomentsTitle())
//                //setText(R.id.item_business_content_tv, item.getMomentsContent())
//                .setText(R.id.item_business_time_tv, item.getMomentsDate())
//                .setText(R.id.item_business_target_tv, target);
//        setPhotos(helper, item);
    }


    private void setPhotos(BaseViewHolder helper, BusinessBean.MaterialsPrice item) {
//        List<String> relativePhotos = new ArrayList<>();
//        if (!StringUtils.isNullOrEmpty(item.getMomentsImage())) {
//            relativePhotos.add(item.getMomentsImage());
//        }
//        RecyclerView recyclerView = helper.getView(R.id.item_business_img_rv);
//        if (relativePhotos.size() == 0) {
//            recyclerView.setVisibility(View.GONE);
//            return;
//        } else {
//            recyclerView.setVisibility(View.VISIBLE);
//        }

//        if ("1".equals(item.getMomentsType()) || "0".equals(item.getMomentsType())){
//            recyclerView.setVisibility(View.GONE);
//            return;
//        }


//        LinearLayoutManager manager = new LinearLayoutManager(mContext);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(manager);
//        final LinearLayout body = helper.getView(R.id.ll_body);
//
//        recyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    body.performClick();
//                }
//                return false;
//            }
//        });
//
//        List<BusinessBean.BusinessSelectItem> list;
//        if ("1".equals(item.getMomentsType())){
//            list = item.getExchangeRate();
//        }else {
//            list = item.getWeavePrice();
//        }
//
//        if (list == null || list.size() == 0){
//            return;
//        }
//        BusinessRateMaterialsAdapter adapter = new BusinessRateMaterialsAdapter(R.layout.item_approvalpecess_time_header_time, list);
//        recyclerView.setAdapter(adapter);

    }

}
