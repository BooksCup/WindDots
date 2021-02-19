package com.wd.winddots.desktop.list.check.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.check.bean.CheckBean;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: CheckAdapter
 * Author: 郑
 * Date: 2021/1/15 10:50 AM
 * Description:
 */
public class CheckAdapter extends BaseQuickAdapter<CheckBean.FabricQcRecord, BaseViewHolder> {

    public CheckItemTextChangeListener textChangeListener;

    public void setTextChangeListener(CheckItemTextChangeListener textChangeListener) {
        this.textChangeListener = textChangeListener;
    }

    public CheckAdapter(int layoutResId, @Nullable List<CheckBean.FabricQcRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckBean.FabricQcRecord item) {

        EditText afterLengthEt = helper.getView(R.id.et_after_length);
        EditText afterWeightEt = helper.getView(R.id.et_after_weight);
//        afterLengthEt.setText("");
//        afterWeightEt.setText("");
        afterLengthEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                item.setLengthAfter(afterLengthEt.getText().toString().trim());
                if (textChangeListener != null) {
                    textChangeListener.afterLengthChange();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        afterWeightEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                item.setWeightAfter(afterWeightEt.getText().toString().trim());
                if (textChangeListener != null) {
                    textChangeListener.afterWeightChange();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        String lengthBefore;
        if (StringUtils.isNullOrEmpty(item.getLengthAfter())) {
            lengthBefore = "***";
        } else {
            lengthBefore = item.getLengthBefore();
        }

        String weightBefore;
        if (StringUtils.isNullOrEmpty(item.getWeightAfter())) {
            weightBefore = "***";
        } else {
            weightBefore = item.getWeightBefore();
        }
        helper.setText(R.id.tv_number, Utils.nullOrEmpty(item.getSno()))
//                .setText(R.id.tv_before_length, lengthBefore)
                .setText(R.id.et_after_length, Utils.nullOrEmpty(item.getLengthAfter()))
//                .setText(R.id.tv_before_weight, weightBefore)
                .setText(R.id.et_after_weight, Utils.nullOrEmpty(item.getWeightAfter()));
    }


    public interface CheckItemTextChangeListener {
        void afterLengthChange();

        void afterWeightChange();
    }


}
