package com.wd.winddots.mvp.widget.adapter.rv;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.PointsListBean;

import java.util.List;

import androidx.annotation.Nullable;

public class PointsAdapter extends BaseQuickAdapter<PointsListBean, BaseViewHolder> {


    public PointsAdapter(int layoutResId, @Nullable List<PointsListBean> data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, PointsListBean item) {

        helper.setText(R.id.item_points_time,item.getCreateTime());

    }
}
