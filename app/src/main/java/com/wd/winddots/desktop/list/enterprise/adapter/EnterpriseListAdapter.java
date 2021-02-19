package com.wd.winddots.desktop.list.enterprise.adapter;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseListBean;
import com.wd.winddots.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: EnterpriseListAdapter
 * Author: 郑
 * Date: 2020/12/22 11:06 AM
 * Description:
 */
public class EnterpriseListAdapter extends BaseQuickAdapter<EnterpriseListBean.EnterpriseItem, BaseViewHolder> {


    public String searchKey;


    public EnterpriseListAdapter(int layoutResId, @Nullable List<EnterpriseListBean.EnterpriseItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnterpriseListBean.EnterpriseItem item) {
        String enterpriseName = Utils.nullOrEmpty(item.getName());
        String status = Utils.nullOrEmpty(item.getRegStatus());
        String legalPersonName = Utils.nullOrEmpty(item.getLegalPersonName());
        String regCapital = Utils.nullOrEmpty(item.getRegCapital());
        String add = "注册地:" + Utils.nullOrEmpty(item.getBase());

        Date d = new Date(item.getEstiblishTime());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(d);

        helper.setText(R.id.tv_name, configHtml(enterpriseName)).
                setText(R.id.tv_status, status).
                setText(R.id.tv_legalPersonName, configHtml(legalPersonName)).
                setText(R.id.tv_regCapital, regCapital).
                setText(R.id.tv_date, date).
                setText(R.id.tv_sort, add);

    }


    private Spanned configHtml(String content) {
        if (StringUtils.isNullOrEmpty(searchKey)) {
            return Html.fromHtml(content);
        }
        if (StringUtils.isNullOrEmpty(content)) {
            return Html.fromHtml("");
        }
        String[] searchKeyItems = searchKey.split("");
        String[] contentItems = content.split("");
        StringBuilder htmlStr = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            String contentItem = contentItems[i];
            boolean target = false;
            for (int m = 0; m < searchKey.length(); m++) {
                String searchKeytItem = searchKeyItems[m];
                if (searchKeytItem.equals(contentItem)) {
                    target = true;
                    break;
                }
            }
            if (target) {
                htmlStr.append("<font color='red'>").append(contentItem).append("</font>");
            } else {
                htmlStr.append(contentItem);
            }
        }
        return Html.fromHtml(htmlStr.toString());
    }
}
