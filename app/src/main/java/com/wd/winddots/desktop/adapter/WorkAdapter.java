package com.wd.winddots.desktop.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.bean.DesktopListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class WorkAdapter extends BaseQuickAdapter<DesktopListBean.StoreListBena,BaseViewHolder> {
    public WorkAdapter(int layoutResId, @Nullable List<DesktopListBean.StoreListBena> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, DesktopListBean.StoreListBena item) {

//        helper.itemView.setVisibility(View.VISIBLE);
//        RelativeLayout content = helper.getView(R.id.ll_content);
//        ImageView closeIv = helper.getView(R.id.iv_close);
//        if (item.isEditModel()){
//            content.setBackgroundColor(mContext.getResources().getColor(R.color.colorF0));
//            closeIv.setVisibility(View.VISIBLE);
//        }else {
//            content.setBackgroundColor(Color.WHITE);
//            closeIv.setVisibility(View.GONE);
//        }

        GlideApp.with(mContext)
                .load(item.getApplicationPhoto())
                .into((ImageView) helper.getView(R.id.item_tap_store_icon));
        helper.setText(R.id.item_tap_store_name,item.getName());
    }
}
