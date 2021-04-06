package com.wd.winddots.adapter.check.fabric;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.activity.check.fabric.FabricCheckProblemBrowseActivity;
import com.wd.winddots.entity.FabricCheckTaskLot;
import com.wd.winddots.entity.FabricCheckTaskRecord;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: FabricCheckTaskLotBrowseAdapter
 * Author: 郑
 * Date: 2021/2/23 11:47 AM
 * Description:
 */
public class FabricCheckTaskLotBrowseAdapter extends BaseQuickAdapter<FabricCheckTaskLot, BaseViewHolder> {


    public String goodsName;
    public String goodsNo;
    public String date;
    public String recordPosition;


    public FabricCheckTaskLotBrowseAdapter(int layoutResId, @Nullable List<FabricCheckTaskLot> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckTaskLot item) {

        RecyclerView recyclerView = helper.getView(R.id.rv_check_lot);
        List<FabricCheckTaskRecord> lotList = item.getFabricCheckRecords();
        if (lotList == null) {
            lotList = new ArrayList<>();
        }
        FabricCheckTaskRecordBrowseAdapter recordBrowseAdapter = new FabricCheckTaskRecordBrowseAdapter(R.layout.item_fabric_check_task_lot_record_browse, lotList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recordBrowseAdapter);

        recordBrowseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, FabricCheckProblemBrowseActivity.class);
                intent.putExtra("recordId",recordBrowseAdapter.getData().get(position).getId());
                intent.putExtra("goodsName",goodsName);
                intent.putExtra("goodsNo",goodsNo);
                intent.putExtra("date",item.getDeliveryDate());
                intent.putExtra("position",(position + 1) + "");
                mContext.startActivity(intent);
            }
        });

//        TextView dateTv = helper.getView(R.id.tv_date);
//        dateTv.setText(item.getDeliveryDate());
        String total = "合计: " + Utils.numberNullOrEmpty(item.getWeightAfterTotal()) + "/"
                + Utils.numberNullOrEmpty(item.getWeightBeforeTotal()) + "  "
                + Utils.numberNullOrEmpty(item.getLengthAfterTotal()) + "/"
                + Utils.numberNullOrEmpty(item.getLengthBeforeTotal());
        helper.setText(R.id.tv_date,Utils.nullOrEmpty(item.getDeliveryDate()))
        .setText(R.id.tv_total,total)
        .setText(R.id.tv_machine,Utils.nullOrEmpty(item.getMachineNumber()))
        .setText(R.id.tv_number,Utils.nullOrEmpty(item.getPalletNumber()));


    }
}
