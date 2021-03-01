package com.wd.winddots.adapter.check.fabric;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.activity.check.fabric.FabricCheckProblemActivity;
import com.wd.winddots.entity.FabricCheckTaskLot;
import com.wd.winddots.entity.FabricCheckTaskRecord;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: FabricCheckTaskNumberAdapter
 * Author: 郑
 * Date: 2021/2/23 11:47 AM
 * Description:
 */
public class FabricCheckTaskLotProcessAdapter extends BaseQuickAdapter<FabricCheckTaskLot, BaseViewHolder> {
    public FabricCheckTaskLotProcessAdapter(int layoutResId, @Nullable List<FabricCheckTaskLot> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckTaskLot item) {

        RecyclerView recyclerView = helper.getView(R.id.rv_check_lot);
        List<FabricCheckTaskRecord> lotList = item.getFabricCheckRecords();
        if (lotList == null) {
            lotList = new ArrayList<>();
        }
        FabricCheckTaskRecordProcessAdapter adapter = new FabricCheckTaskRecordProcessAdapter(R.layout.item_fabric_check_task_lot_record_process, lotList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, FabricCheckProblemActivity.class);
                mContext.startActivity(intent);
            }
        });

        TextView dateTv = helper.getView(R.id.tv_date);
        dateTv.setText(item.getDeliveryDate());


    }
}
