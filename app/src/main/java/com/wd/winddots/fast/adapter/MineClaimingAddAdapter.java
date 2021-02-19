package com.wd.winddots.fast.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.fast.bean.ApplyDetailBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: MineClaimingAddAdapter
 * Author: 郑
 * Date: 2020/5/8 2:17 PM
 * Description:
 */
public class MineClaimingAddAdapter extends BaseQuickAdapter<ApplyDetailBean.ClaimingModel, BaseViewHolder> {

    private MineClaimingAddAdapterSelectListener listener;

    public void setMineClaimingAddAdapterSelectListener(MineClaimingAddAdapterSelectListener listener1){
            listener = listener1;
    }


    public MineClaimingAddAdapter(int layoutResId, @Nullable List<ApplyDetailBean.ClaimingModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ApplyDetailBean.ClaimingModel item) {

        helper.setText(R.id.item_claiming_add_costname,item.getCostName()).
        setText(R.id.item_claiming_add_amount,item.getAmount());

        final ImageView icon = helper.getView(R.id.item_claiming_add_icon);
        if (item.isSelect()){
            icon.setImageResource(R.mipmap.select);
        }else {
            icon.setImageResource(R.mipmap.unselect);
        }

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isSelect()){
                    item.setSelect(false);
                    icon.setImageResource(R.mipmap.unselect);
                }else {
                    item.setSelect(true);
                    icon.setImageResource(R.mipmap.select);
                }

                if (listener != null){
                    listener.selectIconDidClick();
                }

            }
        });

    }

    public interface MineClaimingAddAdapterSelectListener{
        void selectIconDidClick();
    }
}
