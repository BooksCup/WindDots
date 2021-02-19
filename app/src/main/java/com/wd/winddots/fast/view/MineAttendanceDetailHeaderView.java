package com.wd.winddots.fast.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.fast.adapter.MineClaimingImagePickerAdpater;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.fast.bean.MineAttendanceListBean;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.TimeLineHeaderFooterView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: MineAttendanceDetailHeaderView
 * Author: 郑
 * Date: 2020/5/29 11:34 AM
 * Description:
 */
public class MineAttendanceDetailHeaderView extends LinearLayout {

    private ImageView mIconImageView;
    private TextView mNameTextView;
    private TextView mPositionTextView;
    private TextView mAddtimeTextView;
    private TextView mTypeTextView;
    private TextView mCopyUserTextView;
    private TextView mTimeTextView;
    private TextView mCauseTextView;
    private RecyclerView mTimeRecyclerView;
    private RecyclerView mImageRecyclerView;
    private TimeLineHeaderFooterView mHeader;

    String mTypeS;


    public MineAttendanceDetailHeaderView(Context context) {
        super(context);
        initView();
    }

    public MineAttendanceDetailHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MineAttendanceDetailHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_mine_attendance_detail_header, this, false);
        mIconImageView = view.findViewById(R.id.view_attendance_header_icon);
        mNameTextView = view.findViewById(R.id.view_attendance_header_name);
        mPositionTextView = view.findViewById(R.id.view_attendance_header_position);
        mAddtimeTextView = view.findViewById(R.id.view_attendance_header_addtime);
        mTypeTextView = view.findViewById(R.id.view_attendance_header_type);
        mCopyUserTextView = view.findViewById(R.id.view_attendance_header_copy);
        mTimeTextView = view.findViewById(R.id.view_attendance_header_time);
        mCauseTextView = view.findViewById(R.id.view_attendance_header_cause);
        mTimeRecyclerView = view.findViewById(R.id.view_attendance_header_timelist);
        mImageRecyclerView = view.findViewById(R.id.view_attendance_header_imagelist);
        mHeader = view.findViewById(R.id.view_attendance_header_timelinestart);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mTimeRecyclerView.setLayoutManager(layoutManager);

        this.addView(view);
    }


    public void setData(ApplyDetailBean bean) {
        GlideApp.with(getContext()).load(bean.getAvatar() + Utils.OSSImageSize(200)).
                into(mIconImageView);
        mNameTextView.setText(bean.getUserName());
        mPositionTextView.setText(bean.getJobName());
        mAddtimeTextView.setText(bean.getCreatedTime());
        mCauseTextView.setText("事由: " + bean.getCause());

        List<ApplyDetailBean.User> copyUsers = bean.getCopyUsers();
        if (copyUsers == null) {
            copyUsers = new ArrayList<>();
        }
        String copyNames = "";
        for (int i = 0; i < copyUsers.size(); i++) {
            ApplyDetailBean.User user = copyUsers.get(i);
            copyNames = copyNames + " " + user.getUserName();
        }

        mCopyUserTextView.setText("抄送人: " + copyNames);


        switch (bean.getType()) {
            case Const.APPROVA_LEAVE_0:
                mTypeTextView.setText("请假" + "  " + bean.getLeaveType());
                mTimeTextView.setText("请假天数: " + bean.getLeaveDays() + "天");
                mTypeS = "请假";
                break;
            case Const.APPROVA_OVERTIME_1:
                mTypeTextView.setText("加班");
                mTimeTextView.setText("加班时长: " + bean.getLeaveDays() + "小时");
                mTypeS = "加班";
                break;
            case Const.APPROVA_TRIP_11:
                mTypeTextView.setText("公出");
                mTimeTextView.setText("公出天数: " + bean.getLeaveDays() + "天");
                mTypeS = "公出";
                break;
        }


        List<ApplyDetailBean.ClaimingModel> data = bean.getDetailList();
        if (data == null) {
            data = new ArrayList<>();
        }
        MineAttendanceTimeAdapter adapter = new MineAttendanceTimeAdapter(R.layout.item_approvalpecess_time_header_time, data);
        mTimeRecyclerView.setAdapter(adapter);


        String imageJson = bean.getVoucher();
        List<String> images = new ArrayList<>();
        try {
            Gson gson = new Gson();
            List<String> temp = gson.fromJson(imageJson, new TypeToken<List<String>>() {
            }.getType());
            images.addAll(temp);
        } catch (Exception e) {
        }

        if (images.size() > 0) {
            mImageRecyclerView.setVisibility(View.VISIBLE);
            List<ApplyDetailBean.Invoice> imageList = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                ApplyDetailBean.Invoice item = new ApplyDetailBean.Invoice();
                item.setInvoiceImage(images.get(i));
                imageList.add(item);
            }
            int mItemS = Utils.getScreenWidth(getContext()) / 3;

            MineClaimingImagePickerAdpater adpater1 = new MineClaimingImagePickerAdpater(R.layout.item_image_pcker, imageList, mItemS);
            adpater1.setDeleteIconVisibility(View.GONE);
            mImageRecyclerView.setAdapter(adpater1);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
            mImageRecyclerView.setLayoutManager(manager);
            int rowCount = ((imageList.size() - 1) / 3) + 1;
            mImageRecyclerView.getLayoutParams().height = rowCount * mItemS;
        } else {
            mImageRecyclerView.setVisibility(View.GONE);
        }


        if ("1".equals(bean.getStatus())) {
            mHeader.setVisibility(View.GONE);
        } else {
            if ("0".equals(bean.getApprovalStatus())) {
                mHeader.setVisibility(View.GONE);
            } else if ("1".equals(bean.getApprovalStatus())) {
                mHeader.setVisibility(View.VISIBLE);
            } else if ("2".equals(bean.getApprovalStatus())) {
                mHeader.setVisibility(View.VISIBLE);
            }
        }


    }


    public class MineAttendanceTimeAdapter extends BaseQuickAdapter<ApplyDetailBean.ClaimingModel, BaseViewHolder> {
        public MineAttendanceTimeAdapter(int layoutResId, @Nullable List<ApplyDetailBean.ClaimingModel> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ApplyDetailBean.ClaimingModel item) {
            helper.setText(R.id.item_approvalecess_time_header_time_tv, mTypeS + "时间" + (helper.getAdapterPosition() + 1) + ": " + item.getStartTime() + "~" + item.getEndTime());
        }

    }

}
