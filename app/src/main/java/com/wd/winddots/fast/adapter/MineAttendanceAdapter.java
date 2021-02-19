package com.wd.winddots.fast.adapter;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.fast.bean.MineAttendanceListBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: MineAttendanceAdapter
 * Author: 郑
 * Date: 2020/5/27 11:40 AM
 * Description:
 */
public class MineAttendanceAdapter extends BaseQuickAdapter<MineAttendanceListBean.MineAttendanceBean, BaseViewHolder> {


    private String mType="";
    private String mTypeS ="";
    private MineAttendanceAdapterOnItemClickListener listener;

    public void setMineAttendanceAdapterOnItemClickListener(MineAttendanceAdapterOnItemClickListener listener1){
        listener = listener1;
    }


    public MineAttendanceAdapter(int layoutResId, @Nullable List<MineAttendanceListBean.MineAttendanceBean> data,String type) {
        super(layoutResId, data);
        mType = type;
        if ("0".equals(type) ){
            mTypeS = "请假";
        }else if ("1".equals(type)){
            mTypeS = "加班";
        }else if ("11".equals(type)){
            mTypeS = "公出";
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, final MineAttendanceListBean.MineAttendanceBean item) {


        final RelativeLayout body = helper.getView(R.id.item_mine_attendance_item_body);
//
//        body.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener != null){
//                    listener.onItemClick(item);
//                }
//            }
//        });



        String status = "";
        switch (item.getApprovalStatus()) {
            case "0":
                status = "状态: 未完成";
                break;
            case "1":
                status = "状态: 已完成";
                break;
            case "2":
                status = "状态: 驳回";
                break;
            default:
                status = "状态: 未完成";
        }

        helper.setText(R.id.item_mine_attendance_type,mTypeS).
        setText(R.id.item_mine_attendance_status,status);

        RecyclerView recyclerView = helper.getView(R.id.item_mine_attendance_rlist);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        List<MineAttendanceListBean.MineAttendanceDetailBean> data = item.getDetailList();
        if (data == null){
            data = new ArrayList<>();
        }
        MineAttendanceTimeAdapter adapter = new MineAttendanceTimeAdapter(R.layout.item_approvalpecess_time_header_time,data);


        recyclerView.setAdapter(adapter);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    body.performClick();
                }
                return false;
            }
        });


    }


    public class MineAttendanceTimeAdapter extends BaseQuickAdapter<MineAttendanceListBean.MineAttendanceDetailBean,BaseViewHolder>{

        public MineAttendanceTimeAdapter(int layoutResId, @Nullable List<MineAttendanceListBean.MineAttendanceDetailBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MineAttendanceListBean.MineAttendanceDetailBean item) {
            helper.setText(R.id.item_approvalecess_time_header_time_tv,mTypeS+"时间" + (helper.getAdapterPosition() + 1) + ": " +item.getStartTime() + "~" + item.getEndTime());
        }
    }

    public interface MineAttendanceAdapterOnItemClickListener{
        void onItemClick(MineAttendanceListBean.MineAttendanceBean item);
    }
}
