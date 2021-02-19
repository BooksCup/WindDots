package com.wd.winddots.mvp.widget;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.bean.resp.ApprovalProcessBean;
import com.wd.winddots.fast.adapter.MineClaimingImagePickerAdpater;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.message.presenter.impl.ApprovalProcessPresenterImpl;
import com.wd.winddots.mvp.view.ApprovalProcessView;
import com.wd.winddots.mvp.widget.adapter.ApprovalProcessTimeAdapter;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ApprovalProcessActivity extends CommonActivity<ApprovalProcessView, ApprovalProcessPresenterImpl> implements ApprovalProcessView {

    private RecyclerView mRvTime;
    private String mId;
    private String mType;
    private String mTypeS;

    private ApprovalProcessBean approvalProcessBean;


    private List<ApprovalProcessBean.TimeData.UsersBean> timeUserList = new ArrayList<>();
    private ApprovalProcessTimeAdapter mTimeAdapter;
    private View mTimeHeaderView;
    private LinearLayout mLlMoney;
    private TextView mTvMoneyApplyUserName;
    private TextView mTvMoneyCopyUserName;
    private TextView mTvMoneyApplyTime;
    private TextView mTvMoneyTheme;
    private TextView mTvMoneyType;
    private TextView mTvMoneyTotal;
    private ImageView mIvMoneyExpansionState;
    private TextView mTvMoneyNote;
    private ImageView mIvMoneyApprovalState;
    private TextView mTvMoneyApprovalState;
    private ImageView mIvMoneyApprovalExpansionState;
    private RecyclerView mRvMoneyCount;
    private RecyclerView mRvMoneyApproval;
    private TextView mTvMoneyToEnterpriseName;
    private RelativeLayout mRlMoneyTotalLayout;
    private RelativeLayout mRlApprovalLayout;
    private ImageView mIvApplyUser;
    private int mMoneyListHeiget;
    private int mApprovalListPx;
    private ScrollView mSvMoney;
    private RelativeLayout mAndFromCompanyLayout;
    private RelativeLayout mBtnLayout;
    private TextView mBtnRecall;
    private LinearLayout mBtnYN;
    private TextView mBtnY;
    private TextView mBtnN;
    private String mRecallType;
    private String mUserType;
    private String mApprovalStatus;
    private int mPosition;
    private AlertDialog mDialog;

    @Override
    public ApprovalProcessPresenterImpl initPresenter() {
        return new ApprovalProcessPresenterImpl();
    }


    @Override
    public void initView() {
        super.initView();

        addBadyView(R.layout.activity_approvalpocess);
        setTitleText("审批流程");

        mRvTime = ((RecyclerView) findViewById(R.id.activity_approvalprocess_time_rv));//请假加班布局


        mSvMoney = ((ScrollView) findViewById(R.id.activity_approvalprocess_money_sv));
        mLlMoney = ((LinearLayout) findViewById(R.id.activity_approvalprocess_money_ll));//报销布局

        mTvMoneyApplyUserName = ((TextView) findViewById(R.id.activity_approvalprocess_money_apply_user_name_tv));
        mIvApplyUser = ((ImageView) findViewById(R.id.activity_approvalprocess_money_apply_user_iv));
        mTvMoneyCopyUserName = ((TextView) findViewById(R.id.activity_approvalprocess_money_copy_user_name_tv));
        mTvMoneyApplyTime = ((TextView) findViewById(R.id.activity_approvalprocess_money_apply_time_tv));
        mTvMoneyTheme = ((TextView) findViewById(R.id.activity_approvalprocess_money_theme_tv));
        mTvMoneyType = ((TextView) findViewById(R.id.activity_approvalprocess_money_type_tv));
        mTvMoneyToEnterpriseName = ((TextView) findViewById(R.id.activity_approvalprocess_money_to_enterprise_name_tv));
        mTvMoneyTotal = ((TextView) findViewById(R.id.activity_approvalprocess_money_money_tv));
        mRlMoneyTotalLayout = ((RelativeLayout) findViewById(R.id.activity_approvalprocess_money_money_rl));
        mIvMoneyExpansionState = ((ImageView) findViewById(R.id.activity_approvalprocess_money_money_state_iv));//金额展开状态
        mTvMoneyNote = ((TextView) findViewById(R.id.activity_approvalprocess_money_note_tv));
        mRlApprovalLayout = ((RelativeLayout) findViewById(R.id.activity_approvalprocess_money_approval_rl));
        mIvMoneyApprovalState = ((ImageView) findViewById(R.id.activity_approvalprocess_money_approval_state_iv));//审核状态
        mTvMoneyApprovalState = ((TextView) findViewById(R.id.activity_approvalprocess_money_approval_state_tv));
        mIvMoneyApprovalExpansionState = ((ImageView) findViewById(R.id.activity_approvalprocess_money_approval_expansion_state_iv));//审核展开状态
        mRvMoneyCount = ((RecyclerView) findViewById(R.id.activity_approvalprocess_money_money_list_rv));
        mRvMoneyApproval = ((RecyclerView) findViewById(R.id.activity_approvalprocess_money_approval_list_rv));

        mAndFromCompanyLayout = ((RelativeLayout) findViewById(R.id.activity_approvalprocess_money_andfromcompany_layout));//来往单位


        //底部按钮
        mBtnLayout = (RelativeLayout) findViewById(R.id.activity_approvalprocess_btn_layout);
        mBtnRecall = ((TextView) findViewById(R.id.activity_approvalprocess_recall_btn));//撤回
        mBtnYN = ((LinearLayout) findViewById(R.id.activity_approvalprocess_y_and_n_btn));
        mBtnY = ((TextView) findViewById(R.id.activity_approvalprocess_y_btn));
        mBtnN = ((TextView) findViewById(R.id.activity_approvalprocess_n_btn));


        Intent intent = getIntent();
        if (intent != null) {
            mPosition = intent.getIntExtra("position", 0);
            mId = intent.getStringExtra("id");
            mType = intent.getStringExtra("type");
            mRecallType = intent.getStringExtra("recallType");
            mUserType = intent.getStringExtra("userType");
            mApprovalStatus = intent.getStringExtra("approvalStatus");
        }


        if ("0".equals(mType) || "1".equals(mType) || "2".equals(mType) || "3".equals(mType) || "11".equals(mType)) {
            mSvMoney.setVisibility(View.GONE);
            mRvTime.setVisibility(View.VISIBLE);
            initTimeData();
        } else {
            mRvTime.setVisibility(View.GONE);
            mSvMoney.setVisibility(View.VISIBLE);
        }


    }


    //设置时间数据 加班请假
    private void initTimeData() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvTime.setLayoutManager(manager);
        mTimeAdapter = new ApprovalProcessTimeAdapter(R.layout.item_approvalpecess_time, timeUserList);
        mRvTime.setAdapter(mTimeAdapter);
    }


    @Override
    public void initListener() {
        super.initListener();

        mRlMoneyTotalLayout.setOnClickListener(onMoneyClickListener);
        mRlApprovalLayout.setOnClickListener(onApprovalClickListener);

        mBtnRecall.setOnClickListener(this);
        mBtnY.setOnClickListener(this);
        mBtnN.setOnClickListener(this);

        mTvMoneyApplyUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.activity_approvalprocess_y_btn://同意
                agreeY_N("1");
                break;
            case R.id.activity_approvalprocess_n_btn://驳回
                agreeY_N("2");
                break;
            case R.id.activity_approvalprocess_recall_btn://撤销
                recall();
                break;
        }
    }


    @Override
    public void initData() {
        super.initData();
        presenter.loadData(mId, SpHelper.getInstance(mContext.getApplicationContext()).getString("id"), mType);
    }


    /**
     * 初始化数据成功
     *
     * @param bean
     */
    @Override
    public void onLoadSuccess(ApprovalProcessBean bean) {
        // TODO: 2020/4/21   数据更新 状态传回给上一个界面
        Intent intent = new Intent();
        intent.putExtra("position", mPosition);

        approvalProcessBean = bean;
        ApprovalProcessBean.TimeData timeData = bean.getTimeData();
        ApprovalProcessBean.MoneyData moneyData = bean.getMoneyData();
        if (timeData != null) {
            setTimeData(timeData);
            intent.putExtra("status", timeData.getApprovalStatus());
        } else if (moneyData != null) {
            setMoneyData(moneyData);
            intent.putExtra("status", moneyData.getApprovalStatus());
        }

        setResult(101, intent);
    }

    /**
     * 设置 时间数据
     *
     * @param timeData
     */
    private void setTimeData(ApprovalProcessBean.TimeData timeData) {
        // TODO: 2020/4/21 底部按钮的显示与隐藏
        String approvalStatus = timeData.getApprovalStatus();
        if ("0".equals(approvalStatus) && !"1".equals(mUserType)) {
            mBtnLayout.setVisibility(View.VISIBLE);
            mBtnRecall.setVisibility(View.GONE);
            mBtnYN.setVisibility(View.VISIBLE);
        } else {
            if ("0".equals(mRecallType)) {
                mBtnLayout.setVisibility(View.VISIBLE);
                mBtnYN.setVisibility(View.GONE);
                mBtnRecall.setVisibility(View.VISIBLE);
            } else {
                mBtnLayout.setVisibility(View.GONE);
            }
        }

        List<ApprovalProcessBean.TimeData.UsersBean> users = timeData.getUsers();
        timeUserList.addAll(users);
        mTimeAdapter.notifyDataSetChanged();
        addTimeHeader(timeData);
        addTimeFooter();
    }


    private void addTimeFooter() {
        View timeFooterView = LayoutInflater.from(mContext).inflate(R.layout.footer_approvalprocess_time, null, false);
        mTimeAdapter.addFooterView(timeFooterView);
    }

    private void addTimeHeader(ApprovalProcessBean.TimeData timeData) {
        if (mTimeHeaderView == null)
            mTimeHeaderView = LayoutInflater.from(mContext).inflate(R.layout.header_approvalprocess_time, null, false);
        ImageView ivUser = (ImageView) mTimeHeaderView.findViewById(R.id.header_approvalprocess_time_user_iv);
        TextView tvUser = (TextView) mTimeHeaderView.findViewById(R.id.header_approvalprocess_time_user_tv);
        TextView tvTime = (TextView) mTimeHeaderView.findViewById(R.id.header_approvalprocess_time_time_tv);
        TextView tvClass = (TextView) mTimeHeaderView.findViewById(R.id.header_approvalprocess_time_class_tv);
        TextView tvToUser = (TextView) mTimeHeaderView.findViewById(R.id.header_approvalprocess_time_touser_tv);
        TextView tvTimeCount = (TextView) mTimeHeaderView.findViewById(R.id.header_approvalprocess_time_time_count_tv);
        RecyclerView rvTime = (RecyclerView) mTimeHeaderView.findViewById(R.id.header_approvalprocess_time_time_rv);
        TextView tvCause = (TextView) mTimeHeaderView.findViewById(R.id.header_approvalprocess_time_cause_tv);
        RelativeLayout rlEnd = (RelativeLayout) mTimeHeaderView.findViewById(R.id.header_approvalprocess_time_end_rl);
        RecyclerView imageListRcy = mTimeHeaderView.findViewById(R.id.header_approvalprocess_time_imagelist);

        String imageJson = timeData.getVoucher();
        List<String> images = new ArrayList<>();
        try {
            Gson gson = new Gson();
            List<String> temp = gson.fromJson(imageJson, new TypeToken<List<String>>() {
            }.getType());
            images.addAll(temp);
        } catch (Exception e) {
        }

        if (images.size() > 0) {
            List<ApplyDetailBean.Invoice> imageList = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                ApplyDetailBean.Invoice item = new ApplyDetailBean.Invoice();
                item.setInvoiceImage(images.get(i));
                imageList.add(item);
            }
            int mItemS = Utils.getScreenWidth(mContext) / 3;

            MineClaimingImagePickerAdpater adpater1 = new MineClaimingImagePickerAdpater(R.layout.item_image_pcker, imageList, mItemS);
            adpater1.setDeleteIconVisibility(View.GONE);
            imageListRcy.setAdapter(adpater1);
            GridLayoutManager manager = new GridLayoutManager(mContext, 3);
            imageListRcy.setLayoutManager(manager);
            int rowCount = ((imageList.size() - 1) / 3) + 1;
            imageListRcy.getLayoutParams().height = rowCount * mItemS;
        }


        GlideApp.with(mContext)
                .load(timeData.getAvatar())
                .into(ivUser);

        String jobName = timeData.getJobName();
        jobName = jobName == null ? "" : jobName;
        tvUser.setText(timeData.getUserName() + "  " + jobName);

        tvTime.setText(timeData.getCreatedTime());

        String type = timeData.getType();
        String timeType = "";
        String timeCount = "";
        switch (type) {
            case "0"://请假
                timeType = "请假";
                timeCount = timeType + "天数: " + timeData.getLeaveDays() + "天";
                mTypeS = "请假";
                break;
            case "1"://加班
                timeType = "加班";
                mTypeS = "加班";
                timeCount = timeType + "时长: " + timeData.getLeaveDays() + "小时";
                break;
            case "11"://加班
                timeType = "公出";
                mTypeS = "公出";
                timeCount = timeType + "天数: " + timeData.getLeaveDays() + "天";
                break;
        }

        tvClass.setText(timeType);

        tvTimeCount.setText(timeCount);

        if (!"0".equals(timeData.getApprovalStatus())) {
            rlEnd.setVisibility(View.VISIBLE);
        } else {
            rlEnd.setVisibility(View.GONE);
        }


        List<ApprovalProcessBean.TimeData.CopyUsersBean> copyUsers = timeData.getCopyUsers();
        int size = copyUsers.size();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("抄送人: ");
        for (int i = 0; i < size; i++) {
            stringBuilder.append(copyUsers.get(i).getUserName());
            stringBuilder.append(" ");
        }

        tvToUser.setText(stringBuilder.toString());

        tvCause.setText("理由: " + timeData.getCause());


        //时间列表
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTime.setLayoutManager(manager);

        BaseQuickAdapter<ApprovalProcessBean.TimeData.DetailListBean, BaseViewHolder> adapter =
                new BaseQuickAdapter<ApprovalProcessBean.TimeData.DetailListBean, BaseViewHolder>(R.layout.item_approvalpecess_time_header_time, timeData.getDetailList()) {
                    @Override
                    protected void convert(BaseViewHolder helper, ApprovalProcessBean.TimeData.DetailListBean item) {
                        int position = helper.getPosition();
                        StringBuilder sb = new StringBuilder();
                        sb.append(mTypeS + "时间").append(position + 1).append(": ").append(item.getStartTime()).append(" ～ ").append(item.getEndTime());
                        helper.setText(R.id.item_approvalecess_time_header_time_tv, sb.toString());
                    }
                };

        rvTime.setAdapter(adapter);

        mTimeAdapter.addHeaderView(mTimeHeaderView);
    }


    /**
     * 设置报销数据
     *
     * @param moneyData
     */
    private void setMoneyData(final ApprovalProcessBean.MoneyData moneyData) {
        // TODO: 2020/4/21 底部按钮的显示与隐藏
        String approvalStatus = moneyData.getApprovalStatus();
        if ("0".equals(approvalStatus) && !"1".equals(mUserType)) {
            mBtnLayout.setVisibility(View.VISIBLE);
            mBtnRecall.setVisibility(View.GONE);
            mBtnYN.setVisibility(View.VISIBLE);
        } else {
            if ("0".equals(mRecallType)) {
                mBtnLayout.setVisibility(View.VISIBLE);
                mBtnYN.setVisibility(View.GONE);
                mBtnRecall.setVisibility(View.VISIBLE);
            } else {
                mBtnLayout.setVisibility(View.GONE);
            }
        }


        GlideApp.with(mContext)
                .load(moneyData.getAvatar())
                .into(mIvApplyUser);


        mTvMoneyApplyUserName.setText(moneyData.getUserName());

        List<ApprovalProcessBean.MoneyData.CopyUsersBean> copyUsers = moneyData.getCopyUsers();
        StringBuilder copyUserSb = new StringBuilder();
        for (int i = 0; i < copyUsers.size(); i++) {
            ApprovalProcessBean.MoneyData.CopyUsersBean copyUsersBean = copyUsers.get(i);
            copyUserSb.append(copyUsersBean.getUserName());
            copyUserSb.append(" ");
        }
        mTvMoneyCopyUserName.setText(copyUserSb.toString());

        mTvMoneyApplyTime.setText(moneyData.getCreatedTime());

        mTvMoneyTheme.setText(moneyData.getTitle());

        String type = moneyData.getType();

        String typeName = "";
        switch (type) {
            case "4":
                typeName = "内部报销";
                break;
            case "5":
                typeName = "外部报销";
                break;
            case "6":
                typeName = "个人借款";
                break;
            case "7":
                typeName = "单位借款";
                break;
        }
        mTvMoneyType.setText(typeName);

        mTvMoneyNote.setText(moneyData.getRemark());


        if ("5".equals(type) || "7".equals(type)) {
            mAndFromCompanyLayout.setVisibility(View.VISIBLE);
            mTvMoneyToEnterpriseName.setText(moneyData.getToEnterpriseName());
        } else {
            mAndFromCompanyLayout.setVisibility(View.GONE);
        }

        final String currency = moneyData.getCurrency();//币种

        mTvMoneyTotal.setText(moneyData.getAmount() + currency);

        switch (approvalStatus) {
            case "0":
                mIvMoneyApprovalState.setImageResource(R.mipmap.waiting);
                mTvMoneyApprovalState.setText("等待处理");
                break;
            case "1":
                mIvMoneyApprovalState.setImageResource(R.mipmap.success);
                mTvMoneyApprovalState.setText("已通过");
                break;
            case "2":
                mIvMoneyApprovalState.setImageResource(R.mipmap.fail);
                mTvMoneyApprovalState.setText("已驳回");
                break;
        }

        final List<ApprovalProcessBean.MoneyData.DetailListBean> detailList = moneyData.getDetailList();


        int detailListSize = detailList.size();
        int dp = 40 * detailListSize;
        mMoneyListHeiget = dip2px(mContext, dp);
        mRvMoneyCount.setHasFixedSize(true);
        mRvMoneyCount.setNestedScrollingEnabled(false);


        // 金额列表
        BaseQuickAdapter<ApprovalProcessBean.MoneyData.DetailListBean, BaseViewHolder> moneyListAdapter =
                new BaseQuickAdapter<ApprovalProcessBean.MoneyData.DetailListBean, BaseViewHolder>(R.layout.item_approvalpecess_money_list, detailList) {
                    @Override
                    protected void convert(BaseViewHolder helper, ApprovalProcessBean.MoneyData.DetailListBean item) {
                        int position = helper.getPosition() + 1;
                        helper.setText(R.id.item_approvalpecess_money_position_tv, position + "")
                                .setText(R.id.item_approvalpecess_money_fee_tv, item.getCostName())
                                .setText(R.id.item_approvalpecess_money_count_tv, item.getAmount() + currency);
                    }
                };
        LinearLayoutManager manager1 = new LinearLayoutManager(mContext);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        mRvMoneyCount.setLayoutManager(manager1);
        mRvMoneyCount.setAdapter(moneyListAdapter);

        moneyListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ApprovalProcessBean.MoneyData.DetailListBean detailListBean = detailList.get(position);
                EventBus.getDefault().postSticky(detailListBean);
                Intent intent = new Intent(mContext, ApprovalProcessFeeActivity.class);
                intent.putExtra("currency", moneyData.getCurrency());
                startActivity(intent);
            }
        });


        List<ApprovalProcessBean.MoneyData.UsersBean> users = moneyData.getUsers();
//
//
        // TODO: 2020/4/20 审核流程的RecyclerView 不能滚动设置固定高度
        int size = users.size();
        int approvalDP = 0;
        for (int i = 0; i < size; i++) {
            String approvalRemark = users.get(i).getApprovalRemark();
            approvalDP = approvalDP + 72;//审核人及状态
            if (!TextUtils.isEmpty(approvalRemark)) {
                approvalDP = approvalDP + 32;//理由
            }
        }
        approvalDP = approvalDP + 40;//流程开始
        if (!"0".equals(moneyData.getApprovalStatus())) {
            approvalDP = approvalDP + 40;//流程结束
        }


        mApprovalListPx = dip2px(mContext, ((float) approvalDP));

        mRvMoneyApproval.getLayoutParams().height = mApprovalListPx;
        mRvMoneyApproval.setHasFixedSize(true);
        mRvMoneyApproval.setNestedScrollingEnabled(false);


        // 审核列表
        BaseQuickAdapter<ApprovalProcessBean.MoneyData.UsersBean, BaseViewHolder> approvalListAdapter =
                new BaseQuickAdapter<ApprovalProcessBean.MoneyData.UsersBean, BaseViewHolder>(R.layout.item_approvalpecess_time, users) {
                    @Override
                    protected void convert(BaseViewHolder helper, ApprovalProcessBean.MoneyData.UsersBean item) {
                        helper.setText(R.id.item_approvalpecess_user_name_tv, item.getUserName());

                        ImageView ivApprovalState = (ImageView) helper.getView(R.id.item_approvalpecess_state_iv);
                        TextView tvApprovalState = (TextView) helper.getView(R.id.item_approvalpecess_state_tv);
                        TextView tvCause = (TextView) helper.getView(R.id.item_approvalpecess_cause_tv);
                        LinearLayout llCause = (LinearLayout) helper.getView(R.id.item_approvalpecess_cause_ll);
                        TextView tvTime = (TextView) helper.getView(R.id.item_approvalpecess_time_tv);

                        String approvalStatus = item.getApprovalStatus();//审核状态
                        switch (approvalStatus) {
                            case "0"://待处理
                                ivApprovalState.setImageResource(R.mipmap.waiting);
                                tvApprovalState.setText("等待处理");
                                tvApprovalState.setTextColor(Color.parseColor("#FB9637"));
                                break;
                            case "1"://已通过
                                ivApprovalState.setImageResource(R.mipmap.success);
                                tvApprovalState.setText("已通过");
                                tvApprovalState.setTextColor(Color.parseColor("#329934"));
                                break;
                            case "2"://已驳回
                                ivApprovalState.setImageResource(R.mipmap.fail);
                                tvApprovalState.setText("已驳回");
                                tvApprovalState.setTextColor(Color.parseColor("#D74049"));
                                break;
                        }

                        String cause = item.getApprovalRemark();
                        if (TextUtils.isEmpty(cause)) {
                            llCause.setVisibility(View.GONE);
                        } else {
                            llCause.setVisibility(View.VISIBLE);
                            tvCause.setText("理由:" + cause);
                        }

                        String approvalTime = item.getApprovalTime();
                        if (TextUtils.isEmpty(approvalTime) || approvalStatus.equals("0")) {
                            tvTime.setText("");
                        } else {
                            tvTime.setText(approvalTime);
                        }
                    }
                };
        LinearLayoutManager manager2 = new LinearLayoutManager(mContext);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        mRvMoneyApproval.setLayoutManager(manager2);
        mRvMoneyApproval.setAdapter(approvalListAdapter);

        // 添加footer
        View moneyFooterView = LayoutInflater.from(mContext).inflate(R.layout.footer_approvalprocess_time, null, false);
        TextView tvFooter = (TextView) moneyFooterView.findViewById(R.id.footer_approvalprocess_time_tv);
        tvFooter.setText("流程开始");
        approvalListAdapter.addFooterView(moneyFooterView);

        if (!"0".equals(approvalStatus)) {
            View moneyHeaderView = LayoutInflater.from(mContext).inflate(R.layout.footer_approvalprocess_time, null, false);
            TextView tvHeader = (TextView) moneyHeaderView.findViewById(R.id.footer_approvalprocess_time_tv);
            tvHeader.setText("流程结束");
            approvalListAdapter.addHeaderView(moneyHeaderView);
        }

    }


    @Override
    public void onLoadError(String errMsg) {


    }

    @Override
    public void onLoadComplete() {

    }



    private boolean moneyIsExpansion;//金额列表是否展开
    private boolean approvalIsExpansion = true;//审核列表是否展开

    /**
     * 点击金额
     */
    private View.OnClickListener onMoneyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (moneyIsExpansion) {
                mRvMoneyCount.getLayoutParams().height = 0;
                mRvMoneyCount.setVisibility(View.GONE);
                mIvMoneyExpansionState.setImageResource(R.mipmap.icon_bottom);
                moneyIsExpansion = false;
            } else {
                mRvMoneyCount.getLayoutParams().height = mMoneyListHeiget;
                mRvMoneyCount.setVisibility(View.VISIBLE);
                mIvMoneyExpansionState.setImageResource(R.mipmap.icon_top);
                moneyIsExpansion = true;
            }
        }
    };


    /**
     * 点击审核
     */
    private View.OnClickListener onApprovalClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (approvalIsExpansion) {
                mRvMoneyApproval.getLayoutParams().height = 0;
                mRvMoneyApproval.setVisibility(View.GONE);
                mIvMoneyApprovalExpansionState.setImageResource(R.mipmap.icon_bottom);
                approvalIsExpansion = false;
            } else {
                mRvMoneyApproval.getLayoutParams().height = mApprovalListPx;
                mRvMoneyApproval.setVisibility(View.VISIBLE);
                mIvMoneyApprovalExpansionState.setImageResource(R.mipmap.icon_top);
                approvalIsExpansion = true;
            }
        }
    };




    /**
     * type 同意（“1”） 驳回（“2”）
     *
     * @param type
     */
    private void agreeY_N(final String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_examineapprove, null, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.dialog_examineapprove_title_tv);
        final EditText edtNote = (EditText) view.findViewById(R.id.dialog_examineapprove_note_edt);
        TextView tvBtn = (TextView) view.findViewById(R.id.dialog_examineapprove_btn);


        if (type.equals("1")) {// 同意
            tvTitle.setText("同意");
            tvBtn.setText("提交");
            tvBtn.setBackgroundColor(Color.parseColor("#2C992D"));
        } else {// 驳回
            tvTitle.setText("驳回");
            tvBtn.setText("提交");
            tvBtn.setBackgroundColor(Color.parseColor("#D22B34"));
        }


        builder.setView(view);


        mDialog = builder.show();

        String id = "";

        ApprovalProcessBean.TimeData timeData = approvalProcessBean.getTimeData();
        ApprovalProcessBean.MoneyData moneyData = approvalProcessBean.getMoneyData();
        if (timeData != null) {
            id = timeData.getId();
        } else if (moneyData != null) {
            id = moneyData.getId();
        }


        final String finalId = id;
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.examineApprove(SpHelper.getInstance(mContext).getUserId(),finalId,type,edtNote.getText().toString(),SpHelper.getInstance(mContext).getEnterpriseId());
            }
        });
    }
    @Override
    public void onExamineApproveSuccess(String data) {
        presenter.loadData(mId, SpHelper.getInstance(mContext.getApplicationContext()).getString("id"), mType);
    }

    @Override
    public void onExamineApproveError(String errMsg) {
        mDialog.dismiss();
    }

    @Override
    public void onExamineApproveComplete() {
        mDialog.dismiss();
        //刷新数据 清空数据
        approvalProcessBean = null;
        timeUserList.clear();
        if (mTimeAdapter != null) {
            mTimeAdapter.removeAllFooterView();
            mTimeAdapter.removeAllHeaderView();
            mTimeAdapter.notifyDataSetChanged();
        }
    }




    /**
     * 撤销 //MyApplication.USER_ID
     */
    private void recall() {
        if (approvalProcessBean != null){


            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("提示");
            builder.setMessage("确定要撤销该审批吗?");

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ApprovalProcessBean.TimeData timeData = approvalProcessBean.getTimeData();
                    ApprovalProcessBean.MoneyData moneyData = approvalProcessBean.getMoneyData();
                    String id = "";
                    if (timeData != null){
                        id = timeData.getId();
                    } else if (moneyData != null) {
                        id = moneyData.getId();
                    }
                    presenter.recallApproval(SpHelper.getInstance(mContext).getUserId(),id);
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
    }

    @Override
    public void onRecallApprovalSuccess(String data) {
        presenter.loadData(mId, SpHelper.getInstance(mContext.getApplicationContext()).getString("id"), mType);
    }

    @Override
    public void onRecallApprovalError(String errMsg) {
        showToast("撤回失败: " + errMsg);
    }

    @Override
    public void onRecallApprovalComplete() {
        approvalProcessBean = null;
        timeUserList.clear();
        if (mTimeAdapter != null) {
            mTimeAdapter.removeAllFooterView();
            mTimeAdapter.removeAllHeaderView();
            mTimeAdapter.notifyDataSetChanged();
        }
    }

}
