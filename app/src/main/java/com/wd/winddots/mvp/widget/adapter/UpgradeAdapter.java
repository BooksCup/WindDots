package com.wd.winddots.mvp.widget.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: UpgradeAdapter
 * Author: 郑
 * Date: 2020/6/8 11:33 AM
 * Description:
 */
public class UpgradeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public UpgradeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_upgrade,item);
    }
}
