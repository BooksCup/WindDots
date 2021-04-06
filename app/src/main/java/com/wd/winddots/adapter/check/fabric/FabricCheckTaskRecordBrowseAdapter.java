package com.wd.winddots.adapter.check.fabric;

import android.widget.TextView;

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
public class FabricCheckTaskRecordBrowseAdapter extends BaseQuickAdapter<FabricCheckTaskRecord, BaseViewHolder> {
    public FabricCheckTaskRecordBrowseAdapter(int layoutResId, @Nullable List<FabricCheckTaskRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckTaskRecord item) {

        TextView weightEt = helper.getView(R.id.et_weight);
        TextView lengthEt = helper.getView(R.id.et_length);
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

        helper.setText(R.id.tv_number, item.getSno())
                .setText(R.id.et_weight, Utils.numberNullOrEmpty(item.getWeightAfter()) + "/" + Utils.numberNullOrEmpty(item.getWeightBefore()))
                .setText(R.id.et_length, Utils.numberNullOrEmpty(item.getLengthAfter()) + "/" + Utils.numberNullOrEmpty(item.getLengthBefore()))
                .setText(R.id.tv_count, Utils.nullOrEmpty(item.getProblemCount()))
                .setText(R.id.tv_problem_a, problemA)
                .setText(R.id.tv_problem_b, problemB)
                .setText(R.id.tv_problem_c, problemC)
                .setText(R.id.tv_problem_d, problemD);
    }
}
