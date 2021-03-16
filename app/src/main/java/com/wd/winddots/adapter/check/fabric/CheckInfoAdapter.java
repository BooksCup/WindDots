package com.wd.winddots.adapter.check.fabric;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.FabricCheckLotInfo;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckInfoAdapter extends BaseQuickAdapter<FabricCheckLotInfo, BaseViewHolder> {

    private OnSubItemDidClickListener onSubItemDidClickListener;

    private VolleyUtil mVolleyUtil;

    public String fabricCheckTaskId;
    public String goodsName;

    public void setOnSubItemDidClickListener(OnSubItemDidClickListener onSubItemDidClickListener) {
        this.onSubItemDidClickListener = onSubItemDidClickListener;
    }



    public CheckInfoAdapter(int layoutResId, @Nullable List<FabricCheckLotInfo> data) {
        super(layoutResId, data);
        mVolleyUtil = VolleyUtil.getInstance(mContext);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckLotInfo item) {

        LinearLayout llBody = helper.getView(R.id.ll_body);
        LinearLayout llEdit = helper.getView(R.id.ll_edit);
        EditText etGh = helper.getView(R.id.et_gh);
        EditText etJs = helper.getView(R.id.et_js);
        EditText etZl = helper.getView(R.id.et_zl);
        EditText etSl = helper.getView(R.id.et_sl);
        ImageView ivSave = helper.getView(R.id.iv_save);
        TextView tvLotNo = helper.getView(R.id.tv_lot_no);
        TextView tvNum = helper.getView(R.id.tv_num);
        TextView tvWeight = helper.getView(R.id.tv_weight);
        TextView tvLength = helper.getView(R.id.tv_length);
        TextView tvStatus = helper.getView(R.id.tv_status);


        if (item.isEdit()) {
            llBody.setVisibility(View.GONE);
            llEdit.setVisibility(View.VISIBLE);
            etGh.setText(item.getLotNo());
            etJs.setText(item.getNum());
            etZl.setText(item.getWeight());
            etSl.setText(item.getLength());
            etGh.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    item.setLotNo(s.toString());
                    //lotInfos.set(position, fabricCheckLotInfo);
                }
            });

            ivSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> params = new HashMap<>();
                    String lotNo = etGh.getText().toString().trim();
                    params.put("fabricCheckTaskId", fabricCheckTaskId);
                    params.put("lotNo", Utils.nullOrEmpty(lotNo));
                    String url = Constant.APP_BASE_URL + "fabricCheckLotInfo/addFabricCheckLotInfo";
                    Log.e("net666", JSON.toJSONString(params));
                    mVolleyUtil.httpPostRequest(url, params, response -> {
                        if (null == response) {
                            return;
                        }
                        Log.e("net666", response);
                        FabricCheckLotInfo lotInfo = JSON.parseObject(response, FabricCheckLotInfo.class);
                        item.setId(lotInfo.getId());
                        Toast.makeText(mContext, "保存成功", Toast.LENGTH_LONG).show();
                        item.setEdit(false);
                        notifyDataSetChanged();
                    }, volleyError -> {
                        Log.e("net666", String.valueOf(volleyError));
                        mVolleyUtil.handleCommonErrorResponse(mContext, volleyError);
                    });
                }
            });
        } else {
            llBody.setVisibility(View.VISIBLE);
            llEdit.setVisibility(View.GONE);
            tvLotNo.setText(item.getLotNo());
            String status = "";
            if ("1".equals(item.getStatus())) {
                status = "已完成";
            } else {
                status = "盘点中";
            }
           tvStatus.setText(status);

            FabricCheckLotInfo.WarehouseIdVo warehouseIdVo = item.getGetByWarehouseIdVo();
            if (null != warehouseIdVo) {
               tvNum.setText(Utils.nullOrEmpty(warehouseIdVo.getTotalNum()));
                tvWeight.setText(Utils.numberNullOrEmpty(warehouseIdVo.getWeightAfterTotal()) + "/" + Utils.numberNullOrEmpty(warehouseIdVo.getWeightBeforeTotal()));
               tvLength.setText(Utils.numberNullOrEmpty(warehouseIdVo.getLengthAfterTotal()) + "/" + Utils.numberNullOrEmpty(warehouseIdVo.getLengthBeforeTotal()));
            } else {
               tvNum.setText("");
                tvWeight.setText("");
               tvLength.setText("");
            }
            llBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent;
//                    if ("1".equals(item.getStatus())) {
//                        intent = new Intent(mContext, FabricCheckLotBrowseActivity.class);
//                    } else {
//                        intent = new Intent(mContext, FabricCheckLotTaskActivity.class);
//                    }
//                    intent.putExtra("data", item.getId());
//                    intent.putExtra("goodsName", goodsName);
//                    intent.putExtra("goodsNo", item.getLotNo());
//                    intent.putExtra("status", item.getStatus());
//                    intent.putExtra("fabricCheckTaskId", item.getFabricCheckTaskId());
//                    mContext.startActivity(intent);

                    if (null != onSubItemDidClickListener){
                        onSubItemDidClickListener.onSubItemDidClick(helper.getPosition());
                    }
                }
            });
        }

    }

    public interface OnSubItemDidClickListener{
        void onSubItemDidClick(int position);
    }
}
