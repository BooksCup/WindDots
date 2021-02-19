package com.wd.winddots.register.adapter;

import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Enterprise;
import com.wd.winddots.utils.TextHighLight;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: CompanySearchAdapter
 * Author: 郑
 * Date: 2021/1/13 11:21 AM
 * Description: 搜索企业适配器
 */
public class SearchEnterpriseAdapter extends BaseQuickAdapter<Enterprise, BaseViewHolder> {

    private String mKeyword;

    public SearchEnterpriseAdapter(int layoutResId, @Nullable List<Enterprise> data) {
        super(layoutResId, data);
    }

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }

    @Override
    protected void convert(BaseViewHolder helper, Enterprise item) {
        String[] keywords = new String[mKeyword.length()];
        for (int i = 0; i < mKeyword.length(); i++) {
            keywords[i] = String.valueOf(mKeyword.charAt(i));
        }

        if (!TextUtils.isEmpty(item.getName())) {
            SpannableStringBuilder nameBuilder = TextHighLight.
                    matcherSearchContent(mContext, item.getName(), keywords);
            helper.setText(R.id.tv_name, nameBuilder);
        } else {
            helper.setText(R.id.tv_name, item.getName());
        }

        if (!TextUtils.isEmpty(item.getShortName())) {
            SpannableStringBuilder shortNameBuilder = TextHighLight.
                    matcherSearchContent(mContext, item.getShortName(), keywords);
            helper.setText(R.id.tv_short_name, shortNameBuilder);
        } else {
            helper.setText(R.id.tv_short_name, item.getShortName());
        }

        helper.setText(R.id.tv_legal_person_name, item.getLegalPersonName())
                .setText(R.id.tv_estiblish_date, item.getEstiblishDate())
                .setText(R.id.tv_reg_capital, item.getRegCapital());

        // 企业状态
        TextView regStatusTv = helper.getView(R.id.tv_reg_status);
        if (Constant.ENTERPRISE_STATUS_CANCEL.equals(item.getRegStatus())) {
            // 注销
            regStatusTv.setText("注销");
            regStatusTv.setBackgroundResource(R.drawable.shape_select_border_red);
            regStatusTv.setTextColor(mContext.getResources().getColor(R.color.enterprise_status_tag_red));
        } else if (Constant.ENTERPRISE_STATUS_AT_WORK.equals(item.getRegStatus())) {
            // 在业
            regStatusTv.setText("在业");
            regStatusTv.setBackgroundResource(R.drawable.shape_select_border_green);
            regStatusTv.setTextColor(mContext.getResources().getColor(R.color.enterprise_status_tag_green));
        } else {
            // 存续
            regStatusTv.setText("存续");
            regStatusTv.setBackgroundResource(R.drawable.shape_select_border_green);
            regStatusTv.setTextColor(mContext.getResources().getColor(R.color.enterprise_status_tag_green));
        }

        SimpleDraweeView logoSdv = helper.getView(R.id.sdv_logo);
        if (!TextUtils.isEmpty(item.getLogo())) {
            logoSdv.setImageURI(Uri.parse(item.getLogo()));
        } else {
            logoSdv.setImageResource(R.mipmap.icon_company);
        }
    }

}
