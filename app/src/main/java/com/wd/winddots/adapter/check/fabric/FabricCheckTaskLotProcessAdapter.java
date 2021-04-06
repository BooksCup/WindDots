package com.wd.winddots.adapter.check.fabric;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.desktop.list.check.view.SpinnerView;
import com.wd.winddots.entity.FabricCheckTaskLot;
import com.wd.winddots.entity.FabricCheckTaskRecord;
import com.wd.winddots.utils.Utils;

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

    private SubItemDidClickListener subItemDidClickListener;
    public String mFabricCheckTaskId;
    public String mCheckLotInfoId;

    public List<SpinnerView.SpinnerBean> machineList = new ArrayList<>();

    public void setSubItemDidClickListener(SubItemDidClickListener listener1){
        subItemDidClickListener = listener1;
    }


    public FabricCheckTaskLotProcessAdapter(int layoutResId, @Nullable List<FabricCheckTaskLot> data) {
        super(layoutResId, data);
        SpinnerView.SpinnerBean bean0 = new SpinnerView.SpinnerBean();
        bean0.setName("1号");
        SpinnerView.SpinnerBean bean1 = new SpinnerView.SpinnerBean();
        bean1.setName("2号");
        SpinnerView.SpinnerBean bean2 = new SpinnerView.SpinnerBean();
        bean2.setName("3号");
        machineList.add(bean0);
        machineList.add(bean1);
        machineList.add(bean2);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckTaskLot item) {

        SpinnerView spinnerView = helper.getView(R.id.sv_machine);
        spinnerView.setTextColor(mContext.getColor(R.color.colorInputBlue));
        spinnerView.setSelectList(machineList);
        if (null == item.getMachineNumber()){
            item.setMachineNumber(machineList.get(0).getName());
            spinnerView.setDefaultPosition(0);
        }else {
            for (int i = 0;i < machineList.size();i++){
                if (item.getMachineNumber().equals(machineList.get(i).getName())){
                    spinnerView.setDefaultPosition(i);
                    break;
                }
            }
        }
        spinnerView.setOnselectListener(new SpinnerView.OnselectListener() {
            @Override
            public void onselect(int position, SpinnerView view) {
                item.setMachineNumber(machineList.get(position).getName());
            }
        });

        EditText etNumber = helper.getView(R.id.ed_number);
        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                item.setPalletNumber(editable.toString());
            }
        });
        etNumber.setText(Utils.nullOrEmpty(item.getPalletNumber()));

        RecyclerView recyclerView = helper.getView(R.id.rv_check_lot);
        List<FabricCheckTaskRecord> lotList = item.getFabricCheckRecords();
        if (lotList == null) {
            lotList = new ArrayList<>();
        }
        FabricCheckTaskRecordProcessAdapter recordProcessAdapter = new FabricCheckTaskRecordProcessAdapter(R.layout.item_fabric_check_task_lot_record_process, lotList);
        recordProcessAdapter.mFabricCheckTaskId = mFabricCheckTaskId;
        recordProcessAdapter.mCheckLotInfoId = mCheckLotInfoId;
        recordProcessAdapter.mDeliveryDate = item.getDeliveryDate();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recordProcessAdapter);

        recordProcessAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (subItemDidClickListener != null){
                    subItemDidClickListener.onSubItemDidClickListener(helper.getPosition(), recordProcessAdapter.getData().get(position));
                }
            }
        });

        TextView dateTv = helper.getView(R.id.tv_date);
        dateTv.setText(item.getDeliveryDate());
    }

    public interface SubItemDidClickListener{
        void onSubItemDidClickListener(int position,FabricCheckTaskRecord subItem);
    }
}
