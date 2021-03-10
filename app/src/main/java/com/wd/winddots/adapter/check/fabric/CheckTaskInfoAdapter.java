package com.wd.winddots.adapter.check.fabric;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.activity.check.fabric.FabricCheckLotBrowseActivity;
import com.wd.winddots.activity.check.fabric.FabricCheckLotProcessActivity;
import com.wd.winddots.activity.check.fabric.FabricCheckLotTaskActivity;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.FabricCheckLotInfo;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckTaskInfoAdapter extends BaseQuickAdapter<FabricCheckLotInfo, BaseViewHolder> {

    public String fabricCheckTaskId;
    public String goodsName;


    public CheckTaskInfoAdapter(int layoutResId, @Nullable List<FabricCheckLotInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckLotInfo item) {

        LinearLayout llBody = helper.getView(R.id.ll_body);
        LinearLayout llEdit = helper.getView(R.id.ll_edit);
        TextView tvLotNo = helper.getView(R.id.tv_lot_no);
        TextView tvNum = helper.getView(R.id.tv_num);
        TextView tvWeight = helper.getView(R.id.tv_weight);
        TextView tvLength = helper.getView(R.id.tv_length);
        TextView tvStatus = helper.getView(R.id.tv_status);
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
                Intent intent;
                if ("1".equals(item.getStatus())) {
                    intent = new Intent(mContext, FabricCheckLotBrowseActivity.class);
                } else {
                    intent = new Intent(mContext, FabricCheckLotProcessActivity.class);
                }
                intent.putExtra("data", item.getId());
                intent.putExtra("goodsName", goodsName);
                intent.putExtra("goodsNo", item.getLotNo());
                intent.putExtra("status", item.getStatus());
                intent.putExtra("fabricCheckTaskId", item.getFabricCheckTaskId());
                mContext.startActivity(intent);
            }
        });
    }

}



