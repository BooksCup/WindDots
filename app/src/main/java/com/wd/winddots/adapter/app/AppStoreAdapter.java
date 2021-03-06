package com.wd.winddots.adapter.app;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.entity.App;

import java.util.List;

import androidx.annotation.Nullable;

public class AppStoreAdapter extends BaseQuickAdapter<App, BaseViewHolder> {

    public AppStoreAdapter(int layoutResId, @Nullable List<App> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final App item) {

    }

}
