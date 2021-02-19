package com.wd.winddots.fast.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.R;
import com.wd.winddots.fast.bean.MineAttendanceListBean;

import java.util.List;

import androidx.annotation.Nullable;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: MineAttendanceAddTimeAdapter
 * Author: 郑
 * Date: 2020/5/27 3:32 PM
 * Description:
 */
public class MineAttendanceAddTimeAdapter extends BaseQuickAdapter<MineAttendanceListBean.MineAttendanceDetailBean, BaseViewHolder> {

    private String mTypeS;

    private MineAttendanceAddTimeAdapterActionListener listener;

    public void setMineAttendanceAddTimeAdapterActionListener(MineAttendanceAddTimeAdapterActionListener listener1){
        listener = listener1;
    }

    public MineAttendanceAddTimeAdapter(int layoutResId, @Nullable List<MineAttendanceListBean.MineAttendanceDetailBean> data,String typeS) {
        super(layoutResId, data);
        mTypeS = typeS;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MineAttendanceListBean.MineAttendanceDetailBean item) {
        String title = mTypeS + "时间" + (helper.getAdapterPosition() + 1);
        helper.setText(R.id.item_mine_attendance_addtime_title,title);
        if (!StringUtils.isNullOrEmpty(item.getStartTime()) && !StringUtils.isNullOrEmpty(item.getEndTime())){
            helper.setText(R.id.item_mine_attendance_addtime_content,item.getStartTime() + "~" + item.getEndTime());
        }


        LinearLayout timeCell = helper.getView(R.id.item_mine_attendance_addtime_cell);
        timeCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onTimeDidClick(helper);
                }
            }
        });
        LinearLayout deleteBtn = helper.getView(R.id.item_mine_attendance_delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onDeleteIconDidClick(item);
                }
            }
        });
    }


    public interface MineAttendanceAddTimeAdapterActionListener{
        void onDeleteIconDidClick(MineAttendanceListBean.MineAttendanceDetailBean item);
        void onTimeDidClick(BaseViewHolder holder);
    }
}
