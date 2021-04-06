package com.wd.winddots.adapter.check.fabric;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.FabricCheckProblem;
import com.wd.winddots.entity.FabricCheckTaskRecord;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FabricCheckTaskNumberLotAdapter
 * Author: 郑
 * Date: 2021/2/23 12:13 PM
 * Description:
 */
public class FabricCheckTaskRecordProcessAdapter extends BaseQuickAdapter<FabricCheckTaskRecord, BaseViewHolder> {

    private VolleyUtil mVolleyUtil;
    String mFabricCheckTaskId;
    String mCheckLotInfoId;
    String mDeliveryDate;


    public FabricCheckTaskRecordProcessAdapter(int layoutResId, @Nullable List<FabricCheckTaskRecord> data) {
        super(layoutResId, data);
        mVolleyUtil = VolleyUtil.getInstance(mContext);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckTaskRecord item) {

        EditText weightEt = helper.getView(R.id.et_weight);
        EditText lengthEt = helper.getView(R.id.et_length);
        ImageView addIcon = helper.getView(R.id.iv_add);
        FabricCheckProblem problemItem = item.getFabricCheckRecordProblem();
        String problemA = "";
        String problemB = "";
        String problemC = "";
        String problemD = "";
        if (null != problemItem) {
            if (!StringUtils.isNullOrEmpty(problemItem.getTagATimes())) {
                problemA = "A" + Utils.numberNullOrEmpty(problemItem.getTagATimes());
            }
            if (!StringUtils.isNullOrEmpty(problemItem.getTagBTimes())) {
                problemB = "B" + Utils.numberNullOrEmpty(problemItem.getTagBTimes());
            }
            if (!StringUtils.isNullOrEmpty(problemItem.getTagCTimes())) {
                problemC = "C" + Utils.numberNullOrEmpty(problemItem.getTagCTimes());
            }
            if (!StringUtils.isNullOrEmpty(problemItem.getTagDTimes())) {
                problemD = "D" + Utils.numberNullOrEmpty(problemItem.getTagDTimes());
            }

        }


        try {
            String[] temp = null;
            temp = item.getSno().split("-");
            if (temp.length == 1) {
            } else {
                addIcon.setVisibility(View.INVISIBLE);
            }
        } catch (Exception ignored) {
        }
        helper.setText(R.id.tv_number, item.getSno())
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

        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sno = item.getSno();
                int subCount = 0;
                int index = -1;
                List<FabricCheckTaskRecord> data = getData();
                for (int i = 0; i < data.size(); i++) {
                    FabricCheckTaskRecord item1 = data.get(i);
                    String[] temp = null;
                    temp = item1.getSno().split("-");
                    if ((item.getSno() + "-").equals(temp[0] + "-")) {
                        subCount += 1;
                        index = i;
                    }
                }
                FabricCheckTaskRecord taskRecord = new FabricCheckTaskRecord();
                taskRecord.setSno(item.getSno() + "-" + subCount);
                if (index != -1) {
                    addData(index + 1, taskRecord);
                } else {
                    addData(helper.getPosition() + 1, taskRecord);
                }

                Map<String, String> paramsMaps = new HashMap<>();
                paramsMaps.put("sno", taskRecord.getSno());
                paramsMaps.put("checkLotInfoId", mCheckLotInfoId);
                paramsMaps.put("lengthBefore", "0");
                paramsMaps.put("weightBefore", "0");
                paramsMaps.put("deliveryDate", mDeliveryDate);


                String url = Constant.APP_BASE_URL + "fabricCheckRecord/insert?&modifyTime=modifyTimeExamine&fabricCheckTaskId=" + mFabricCheckTaskId;
                Log.e("net666", url);
                String paramsJson = JSON.toJSONString(paramsMaps);
                Log.e("net666", paramsJson);
                Map<String, String> params = new HashMap<>();
                params.put("fabricCheckRecordJson", paramsJson);
                mVolleyUtil.httpPostRequest(url, params, response -> {
                    Log.e("net666",response);
                    FabricCheckTaskRecord taskRecord1 = JSON.parseObject(response,FabricCheckTaskRecord.class);
                    taskRecord.setId(taskRecord1.getId());
                    Toast.makeText(mContext,"拆分成功",Toast.LENGTH_LONG).show();
                }, volleyError -> {
                    mVolleyUtil.handleCommonErrorResponse(mContext, volleyError);
                    Log.e("net666", String.valueOf(volleyError));
                });


            }
        });

    }
}
