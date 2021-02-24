package com.wd.winddots.adapter.check.fabric;

import android.app.DatePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.entity.FabricCheckTaskRecord;
import com.wd.winddots.entity.FabricCheckTaskLot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: FabricCheckTaskNumberAdapter
 * Author: 郑
 * Date: 2021/2/23 11:47 AM
 * Description:
 */
public class FabricCheckTaskLotAdapter extends BaseQuickAdapter<FabricCheckTaskLot, BaseViewHolder> {
    public FabricCheckTaskLotAdapter(int layoutResId, @Nullable List<FabricCheckTaskLot> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FabricCheckTaskLot item) {

        EditText numberEt = helper.getView(R.id.et_lot_number);
        ImageView deleteIv = helper.getView(R.id.iv_delete);
        if (helper.getPosition() == 0){
            deleteIv.setVisibility(View.GONE);
        }else {
            deleteIv.setVisibility(View.VISIBLE);
        }
        deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberEt.isFocused()){
                    return;
                }
                remove(helper.getPosition());
            }
        });
        RecyclerView recyclerView = helper.getView(R.id.rv_check_lot);
        List<FabricCheckTaskRecord> lotList = item.getFabricCheckRecords();
        if (lotList == null){
            lotList = new ArrayList<>();
        }
        FabricCheckTaskRecordAdapter adapter = new FabricCheckTaskRecordAdapter(R.layout.item_fabric_check_task_number_lot,lotList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if (lotList.size() != 0){
            numberEt.setText(lotList.size() + "");
        }else {
            numberEt.setText("");
        }

        numberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               String numberS = numberEt.getText().toString().trim();
               if (StringUtils.isNullOrEmpty(numberS)){
                   return;
               }
               int lenght = Integer.parseInt(numberS);
               if (lenght == 0){
                   adapter.setNewData(new ArrayList<>());
                   return;
               }
               int size = adapter.getData().size();
               List<FabricCheckTaskRecord> newList = new ArrayList<>();

               if (size < lenght){
                   for (int m = 0;m < lenght  - size;m++){
                       newList.add(new FabricCheckTaskRecord());
                   }
                   adapter.addData(newList);
               }else if (size > lenght){
                   List<FabricCheckTaskRecord> lotList1 = adapter.getData();
                   adapter.setNewData(lotList1.subList(0,lenght));
               }
               item.setFabricCheckRecords(adapter.getData());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        numberEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                int index = helper.getPosition();
                if (index == getData().size() -1){
                    addData(new FabricCheckTaskLot());
                }
            }
        });

        TextView dateTv = helper.getView(R.id.tv_date);
        TextView dateTitleTv = helper.getView(R.id.tv_date_title);
        if (StringUtils.isNullOrEmpty(item.getDeliveryDate())){
            dateTv.setText("选择日期");
        }else {
            dateTv.setText(item.getDeliveryDate());
        }
        View.OnClickListener dateClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dp = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                        long maxDate = datePicker.getMaxDate();//日历最大能设置的时间的毫秒值
                        int year = datePicker.getYear();//年
                        int month = datePicker.getMonth() + 1;//月-1
                        int day = datePicker.getDayOfMonth();//日*
                        //iyear:年，monthOfYear:月-1，dayOfMonth:日
                        String monthS;
                        String dayS;
                        if (month < 10) {
                            monthS = "0" + month;
                        } else {
                            monthS = "" + month;
                        }

                        if (day < 10) {
                            dayS = "0" + day;
                        } else {
                            dayS = "" + day;
                        }
                        String date = year + "-" + monthS + '-' + dayS;
                        dateTv.setText(date);
                        item.setDeliveryDate(date);
                    }
                }, year, month, day);//2013:初始年份，2：初始月份-1 ，1：初始日期
                dp.show();
            }
        };
        dateTv.setOnClickListener(dateClick);
        dateTitleTv.setOnClickListener(dateClick);
    }
}
