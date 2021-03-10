package com.wd.winddots.adapter.check.fabric;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.entity.FabricCheckProblem;
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
public class FabricCheckTaskRecordProcessAdapter extends BaseQuickAdapter<FabricCheckTaskRecord, BaseViewHolder> {
    public FabricCheckTaskRecordProcessAdapter(int layoutResId, @Nullable List<FabricCheckTaskRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckTaskRecord item) {

        EditText weightEt = helper.getView(R.id.et_weight);
        EditText lengthEt = helper.getView(R.id.et_length);
        FabricCheckProblem problemItem = item.getFabricCheckRecordProblem();
        String problemA = "";
        String problemB = "";
        String problemC = "";
        String problemD = "";
        if (null != problemItem) {
            if (!StringUtils.isNullOrEmpty(problemItem.getTagATimes())){
                problemA = "A" + Utils.numberNullOrEmpty(problemItem.getTagATimes());
            }
            if (!StringUtils.isNullOrEmpty(problemItem.getTagBTimes())){
                problemB = "B" + Utils.numberNullOrEmpty(problemItem.getTagBTimes());
            }
            if (!StringUtils.isNullOrEmpty(problemItem.getTagCTimes())){
                problemC = "C" + Utils.numberNullOrEmpty(problemItem.getTagCTimes());
            }
            if (!StringUtils.isNullOrEmpty(problemItem.getTagDTimes())){
                problemD = "D" + Utils.numberNullOrEmpty(problemItem.getTagDTimes());
            }

        }

        helper.setText(R.id.tv_number, (helper.getPosition() + 1) + "")
                .setText(R.id.et_weight, Utils.nullOrEmpty(item.getWeightAfter()))
                .setText(R.id.et_length, Utils.nullOrEmpty(item.getLengthAfter()))
                .setText(R.id.tv_count, Utils.nullOrEmpty(item.getProblemCount()))
                .setText(R.id.tv_problem_a, problemA)
                .setText(R.id.tv_problem_b, problemB)
                .setText(R.id.tv_problem_c, problemC)
                .setText(R.id.tv_problem_d, problemD);

        weightEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                item.setWeightAfter(weightEt.getText().toString().trim());
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
                item.setLengthAfter(lengthEt.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
