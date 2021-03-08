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
        String problem = "";
        if (null != problemItem) {
            problem = "A" + Utils.numberNullOrEmpty(problemItem.getTagATimes()) +
                    " B" + Utils.numberNullOrEmpty(problemItem.getTagBTimes()) +
                    " C" + Utils.numberNullOrEmpty(problemItem.getTagCTimes()) +
                    " D" + Utils.numberNullOrEmpty(problemItem.getTagDTimes());
        }

        helper.setText(R.id.tv_number, (helper.getPosition() + 1) + "")
                .setText(R.id.et_weight, Utils.numberNullOrEmpty(item.getWeightAfter()) + "/" + Utils.numberNullOrEmpty(item.getWeightBefore()))
                .setText(R.id.et_length, Utils.numberNullOrEmpty(item.getLengthAfter()) + "/" + Utils.numberNullOrEmpty(item.getLengthBefore()))
                .setText(R.id.tv_count, Utils.nullOrEmpty(item.getProblemCount()))
                .setText(R.id.tv_problem, problem);
    }
}
