package com.wd.winddots.desktop.list.card.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.card.bean.SearchUserBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: SearchUserAdapter
 * Author: 郑
 * Date: 2020/6/22 11:35 AM
 * Description:
 */
public class SearchUserAdapter extends BaseQuickAdapter<SearchUserBean, BaseViewHolder> {
    public SearchUserAdapter(int layoutResId, @Nullable List<SearchUserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchUserBean item) {
        GlideApp.with(mContext).
                load(item.getAvatar()+ Utils.OSSImageSize(100)).
                into((ImageView) helper.getView(R.id.iv_avatar));

        TextView textView;
        helper.setText(R.id.tv_name,item.getName()).
                setText(R.id.tv_phone,item.getPhone()).
                setText(R.id.tv_company,item.getEnterpriseName());
    }
}
