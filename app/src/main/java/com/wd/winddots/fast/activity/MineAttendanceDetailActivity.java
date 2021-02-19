package com.wd.winddots.fast.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.fast.adapter.MineClaimingDetailAdapter;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.fast.presenter.impl.MineAttendanceDetailPresenterImpl;
import com.wd.winddots.fast.presenter.view.MineAttendanceDetailView;
import com.wd.winddots.fast.view.MineAttendanceDetailHeaderView;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.view.TimeLineHeaderFooterView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: MineAttendanceDetailActivity
 * Author: 郑
 * Date: 2020/5/29 11:31 AM
 * Description: 请假 加班 公出详情
 */
public class MineAttendanceDetailActivity extends CommonActivity<MineAttendanceDetailView,MineAttendanceDetailPresenterImpl> implements MineAttendanceDetailView {

    private RecyclerView mRecyclerView;
    private MineAttendanceDetailHeaderView mHeader;
    private TextView mBottomBar;
    private MineClaimingDetailAdapter mAdapter;

    private List<ApplyDetailBean.User> mUsers = new ArrayList<>();
    private ApplyDetailBean mDataBean;

    private String mId;
    private String mType;


    @Override
    public MineAttendanceDetailPresenterImpl initPresenter() {
        return new MineAttendanceDetailPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_mine_attendance_detail);
        setTitleText("审批流程");

        Intent intent= getIntent();
        mType = intent.getStringExtra("type");
        mId = intent.getStringExtra("id");

        mBottomBar = findViewById(R.id.activity_mine_attendance__detail_bottombar);
        mRecyclerView = findViewById(R.id.activity_mine_attendance__detail_rilist);
        mAdapter = new MineClaimingDetailAdapter(R.layout.item_approvalpecess_time_line, mUsers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mHeader = new MineAttendanceDetailHeaderView(mContext);
        mAdapter.setHeaderView(mHeader);
        TimeLineHeaderFooterView footerView = new TimeLineHeaderFooterView(mContext);
        footerView.setmText("流程开始");
        mAdapter.setFooterView(footerView);
    }

    @Override
    public void initData() {
        super.initData();
        presenter.getApplyDetail(mId, SpHelper.getInstance(mContext).getUserId());
    }

    @Override
    public void initListener() {
        super.initListener();
        mBottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("0".equals(mDataBean.getStatus())) {
                    if ("0".equals(mDataBean.getApprovalStatus())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("提示");
                        builder.setMessage("确定要撤回该申请吗?");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                presenter.recallApply(mDataBean.getId());
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    } else if ("2".equals(mDataBean.getApprovalStatus())) {
                        Intent intent = new Intent(mContext,MineAttendanceAddActivity.class);
                        Gson gson = new Gson();
                        String dataS = gson.toJson(mDataBean);
                        intent.putExtra("data",dataS);
                        intent.putExtra("type",mType);
                        startActivityForResult(intent,100);
                    } else {
                        mBottomBar.setVisibility(View.GONE);
                    }
                } else {
                    Intent intent = new Intent(mContext,MineAttendanceAddActivity.class);
                    Gson gson = new Gson();
                    String dataS = gson.toJson(mDataBean);
                    intent.putExtra("data",dataS);
                    intent.putExtra("type",mType);
                    startActivityForResult(intent,100);
                }
            }
        });
    }

    @Override
    public void getApplyDetailSuccess(ApplyDetailBean bean) {
        mUsers.clear();
        mUsers.addAll(bean.getUsers());
        mAdapter.notifyDataSetChanged();
        mHeader.setData(bean);
        mDataBean = bean;

        if ("1".equals(bean.getApplyStatus())) { //进行中
            mBottomBar.setVisibility(View.GONE);
        } else {
            if ("0".equals(bean.getStatus())) {
                if ("0".equals(bean.getApprovalStatus())) {
                    mBottomBar.setText("撤回");
                    mBottomBar.setVisibility(View.VISIBLE);
                } else if ("2".equals(bean.getApprovalStatus())) {
                    mBottomBar.setText("已被驳回 修改并重新提交");
                    mBottomBar.setVisibility(View.VISIBLE);
                } else {
                    mBottomBar.setVisibility(View.GONE);
                }
            } else if ("1".equals(bean.getStatus())) {
                mBottomBar.setText("已撤回 修改并重新提交");
                mBottomBar.setVisibility(View.VISIBLE);
            } else if ("2".equals(bean.getStatus())) {
                mBottomBar.setText("已被驳回 修改并重新提交");
                mBottomBar.setVisibility(View.VISIBLE);
            }
        }

    }
    @Override
    public void getApplyDetailError(String error) {

    }
    @Override
    public void getApplyDetailCompleted() {

    }


    /*
    * 撤回
    * */
    @Override
    public void recallApplySuccess() {
        showToast("撤回成功");
        finish();
    }
    @Override
    public void recallApplyError() {
        showToast("撤回失败,请稍后重试");
    }
    @Override
    public void recallApplyDetailCompleted() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            initData();
        }
    }
}
