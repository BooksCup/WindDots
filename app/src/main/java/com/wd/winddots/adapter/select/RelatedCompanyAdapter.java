package com.wd.winddots.adapter.select;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.entity.RelatedCompany;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 往来单位
 *
 * @author zhou
 */
public class RelatedCompanyAdapter extends BaseQuickAdapter<RelatedCompany, BaseViewHolder> {


    public RelatedCompanyAdapter(int layoutResId, @Nullable List<RelatedCompany> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RelatedCompany item) {

    }
}
