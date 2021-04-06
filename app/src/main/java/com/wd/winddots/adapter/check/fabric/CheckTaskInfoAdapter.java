package com.wd.winddots.adapter.check.fabric;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.entity.FabricCheckLotInfo;
import com.wd.winddots.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

public class CheckTaskInfoAdapter extends BaseQuickAdapter<FabricCheckLotInfo, BaseViewHolder> {


    private OnSubItemDidClickListener onSubItemDidClickListener;

    public String fabricCheckTaskId;
    public String goodsName;

    public void setOnSubItemDidClickListener(OnSubItemDidClickListener onSubItemDidClickListener) {
        this.onSubItemDidClickListener = onSubItemDidClickListener;
    }

    public CheckTaskInfoAdapter(int layoutResId, @Nullable List<FabricCheckLotInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckLotInfo item) {

        LinearLayout llBody = helper.getView(R.id.ll_body);
        LinearLayout llEdit = helper.getView(R.id.ll_edit);
        TextView tvLotNo = helper.getView(R.id.tv_lot_no);
        TextView tvPb = helper.getView(R.id.tv_fa);
        TextView tvNum = helper.getView(R.id.tv_num);
        TextView tvWeight = helper.getView(R.id.tv_weight);
        TextView tvLength = helper.getView(R.id.tv_length);
        TextView tvStatus = helper.getView(R.id.tv_status);
        llEdit.setVisibility(View.GONE);
        tvLotNo.setText(item.getLotNo());
        tvPb.setText(Utils.nullOrEmpty(item.getFabricNumber()));
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

                if (null != onSubItemDidClickListener){
                    onSubItemDidClickListener.onSubItemDidClick(helper.getPosition());
                }


            }
        });
    }


    public interface OnSubItemDidClickListener{
        void onSubItemDidClick(int position);
    }
}



