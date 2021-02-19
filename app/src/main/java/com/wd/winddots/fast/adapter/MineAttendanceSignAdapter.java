package com.wd.winddots.fast.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.fast.bean.MineAttendanceSignBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FileName: MineAttendanceSignAdapter
 * Author: 郑
 * Date: 2020/6/2 10:32 AM
 * Description:
 */
public class MineAttendanceSignAdapter extends BaseQuickAdapter<MineAttendanceSignBean.AttendRecordList, BaseViewHolder> {
    public MineAttendanceSignAdapter(int layoutResId, @Nullable List<MineAttendanceSignBean.AttendRecordList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineAttendanceSignBean.AttendRecordList item) {

        String type = "";
        switch (item.getRecordType()) {
            case "1":
                type = "上班";
                break;
            case "2":
                type = "下班";
                break;
            case "3":
                type = "外勤";
                break;
            case "4":
                type = "回勤";
                break;
            case "5":
                type = "离岗";
                break;
            case "6":
                type = "回岗";
                break;
            default:
                break;
        }

        String recordTime = item.getRecordTime().substring(11, 16);

        helper.setText(R.id.item_attendance_sign_time, recordTime)
                .setText(R.id.item_attendance_sign_type, type)
                .setText(R.id.item_attendance_sign_address, item.getRecordAddressName())
                .setText(R.id.item_attendance_sign_remark, item.getRecordRemark());


    }
}
