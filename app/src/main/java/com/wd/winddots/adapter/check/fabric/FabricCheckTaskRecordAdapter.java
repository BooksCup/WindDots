package com.wd.winddots.adapter.check.fabric;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.entity.FabricCheckTaskRecord;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FabricCheckTaskNumberLotAdapter
 * Author: 郑
 * Date: 2021/2/23 12:13 PM
 * Description:
 */
public class FabricCheckTaskRecordAdapter extends BaseQuickAdapter<FabricCheckTaskRecord, BaseViewHolder> {
    public FabricCheckTaskRecordAdapter(int layoutResId, @Nullable List<FabricCheckTaskRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckTaskRecord item) {

        EditText weightEt = helper.getView(R.id.et_weight);
        EditText lengthEt = helper.getView(R.id.et_length);

        if (StringUtils.isNullOrEmpty(item.getSno())){
            try {
                if (helper.getPosition() == 0){
                    item.setSno(helper.getPosition() + 1 + "");
                }else {
                    FabricCheckTaskRecord previousItem = getData().get(helper.getPosition() - 1);
                    String[] temp = null;
                    temp = previousItem.getSno().split("-");
                    String positionS = temp[0];
                    item.setSno((Integer.parseInt(positionS) + 1) + "");


                }
            } catch (Exception ignored) {
            }
        }
        try {
            String[] temp = null;
            temp = item.getSno().split("-");
            if (temp.length == 1) {
            } else {
                weightEt.setVisibility(View.INVISIBLE);
                lengthEt.setVisibility(View.INVISIBLE);
            }
        } catch (Exception ignored) {
        }


        helper.setText(R.id.tv_number, item.getSno())
                .setText(R.id.et_weight, Utils.nullOrEmpty(item.getWeightBefore()))
                .setText(R.id.et_length, Utils.nullOrEmpty(item.getLengthBefore()));

        weightEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                item.setWeightBefore(weightEt.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lengthEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                item.setLengthBefore(lengthEt.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
