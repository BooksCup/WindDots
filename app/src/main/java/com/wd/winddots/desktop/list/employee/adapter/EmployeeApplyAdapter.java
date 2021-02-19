package com.wd.winddots.desktop.list.employee.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.employee.bean.EmployeeApplyBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: EmployeeApplyAdapter
 * Author: 郑
 * Date: 2020/11/10 11:42 AM
 * Description:
 */
public class EmployeeApplyAdapter extends BaseQuickAdapter<EmployeeApplyBean.EmployeeApplyItem, BaseViewHolder> {

    private EmployeeApplyAdapterListener listener;

    public void setEmployeeApplyAdapterListener(EmployeeApplyAdapterListener listener1){
        listener = listener1;
    }

    public EmployeeApplyAdapter(int layoutResId, @Nullable List<EmployeeApplyBean.EmployeeApplyItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final EmployeeApplyBean.EmployeeApplyItem item) {
        helper.setText(R.id.tv_phone, item.getPhone());

        TextView agree = helper.getView(R.id.tv_agree);
        TextView refuse = helper.getView(R.id.tv_refuse);

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.agreeDidClick(item);
                }
            }
        });

        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.refuseDidClick(item);
                }
            }
        });

    }

    public interface EmployeeApplyAdapterListener {
        void agreeDidClick(EmployeeApplyBean.EmployeeApplyItem item);
        void refuseDidClick(EmployeeApplyBean.EmployeeApplyItem item);
    }
}
