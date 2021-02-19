package com.wd.winddots.desktop.list.check.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.check.bean.CheckQuestionBean;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: CheckQuestionAdapter
 * Author: 郑
 * Date: 2021/1/15 2:54 PM
 * Description:
 */
public class CheckQuestionAdapter extends BaseQuickAdapter<CheckQuestionBean, BaseViewHolder> {
    public CheckQuestionAdapter(int layoutResId, @Nullable List<CheckQuestionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckQuestionBean item) {

        RelativeLayout body = helper.getView(R.id.rl_body);
        TextView nameTv = helper.getView(R.id.tv_name);
        ImageView addIv = helper.getView(R.id.iv_add);

        if (StringUtils.isNullOrEmpty(item.getTag())){
            nameTv.setVisibility(View.GONE);
            addIv.setVisibility(View.VISIBLE);
        }else {
            nameTv.setText(item.getTag());
            nameTv.setVisibility(View.VISIBLE);
            addIv.setVisibility(View.GONE);
        }

        if (item.isSelect()){
            body.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlue));
            nameTv.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        }else {
            body.setBackgroundResource(R.drawable.shape_select_border);
            nameTv.setTextColor(mContext.getResources().getColor(R.color.color50));
        }


    }
}
