package com.wd.winddots.fast.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.TimeLineHeaderFooterView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: ClaimingHeaderView
 * Author: 郑
 * Date: 2020/5/5 5:10 PM
 * Description: 报销 header
 */
public class ClaimingHeaderView extends LinearLayout implements BaseQuickAdapter.OnItemClickListener {


    /*
     * view
     * */
    private ImageView mIcon;
    private TextView mNameText;
    private TextView mCopyUserText;
    private TextView mTimeText;
    private TextView mTitleText;
    private TextView mTypeText;
    private TextView mEnterpriseText;
    private TextView mAmountText;
    private TextView mRemarkText;
    private TextView mStatusText;
    private ImageView mStatusIcon;
    private LinearLayout mEnterpriseCell;
    private TimeLineHeaderFooterView mHeader;
    private LinearLayout mStatusCell;
    private RecyclerView mMoneyList;
    private LinearLayout mMoneyCell;
    private ImageView mMoneyIsopenIcon;


    private ClaimingStatusOnclickListener listener;
    private Context mContext;
    private ApplyDetailBean mDataBean;

    private int target = 0;
    private boolean moneyListIsOpen = false;


    public ClaimingHeaderView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public ClaimingHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ClaimingHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_claiming_header, this, false);

        mIcon = view.findViewById(R.id.view_claimingheader_icon);
        mNameText = view.findViewById(R.id.view_claimingheader_name);
        mCopyUserText = view.findViewById(R.id.view_claimingheader_copy);
        mTimeText = view.findViewById(R.id.view_claimingheader_time);
        mTitleText = view.findViewById(R.id.view_claimingheader_title);
        mTypeText = view.findViewById(R.id.view_claimingheader_type);
        mEnterpriseText = view.findViewById(R.id.view_claimingheader_enterprise);
        mAmountText = view.findViewById(R.id.view_claimingheader_amount);
        mRemarkText = view.findViewById(R.id.view_claimingheader_remark);
        mStatusText = view.findViewById(R.id.view_claimingheader_statusText);
        mStatusIcon = view.findViewById(R.id.view_claimingheader_statusicon);
        mEnterpriseCell = view.findViewById(R.id.view_claimingheader_enterprisecell);
        mHeader = view.findViewById(R.id.view_claimingheader_timeline);
        mStatusCell = view.findViewById(R.id.view_claimingheader_statuscell);
        mMoneyList = view.findViewById(R.id.view_claimingheader_moneylist);
        mMoneyCell = view.findViewById(R.id.view_claimingheader_amountcell);
        mMoneyIsopenIcon = view.findViewById(R.id.view_claimingmoney_icon);

        mMoneyList.setVisibility(View.GONE);


        mMoneyCell.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moneyListIsOpen){
                    mMoneyList.setVisibility(View.GONE);
                    moneyListIsOpen = false;
                    mMoneyIsopenIcon.setImageResource(R.mipmap.icon_bottom);
                }else {
                    mMoneyList.setVisibility(View.VISIBLE);
                    moneyListIsOpen = true;
                    mMoneyIsopenIcon.setImageResource(R.mipmap.icon_top);
                }
            }
        });

        mStatusCell.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener != null) {
                    listener.onClaimingStatusClick(target);
                }
                if (target == 0) {
                    mHeader.setVisibility(View.GONE);
                    target = 1;
                } else {
                    if ("1".equals(mDataBean.getStatus())) {
                        mHeader.setVisibility(View.GONE);
                    } else {
                        if ("0".equals(mDataBean.getApprovalStatus())) {
                            mHeader.setVisibility(View.GONE);
                        } else if ("1".equals(mDataBean.getApprovalStatus())) {
                            mHeader.setVisibility(View.VISIBLE);
                        } else if ("2".equals(mDataBean.getApprovalStatus())) {
                            mHeader.setVisibility(View.VISIBLE);
                        }
                    }
                    target = 0;
                }
            }
        });


        this.addView(view);

    }

    public void setData(ApplyDetailBean bean) {

        mDataBean = bean;

        GlideApp.with(mContext).load(bean.getAvatar()).into(mIcon);

        String type = bean.getType();
        String typeName = "";
        switch (type) {
            case "4":
                typeName = "内部报销";
                mEnterpriseCell.setVisibility(View.GONE);
                break;
            case "5":
                typeName = "外部报销";
                mEnterpriseCell.setVisibility(View.VISIBLE);
                break;
            case "6":
                typeName = "个人借款";
                mEnterpriseCell.setVisibility(View.GONE);
                break;
            case "7":
                typeName = "单位借款";
                mEnterpriseCell.setVisibility(View.VISIBLE);
                break;
            default:
                typeName = "";
        }

        final String currency = StringUtils.isNullOrEmpty(bean.getCurrency()) ? "人民币" : bean.getCurrency();
        String amount = StringUtils.isNullOrEmpty(bean.getAmount()) ? "0" : bean.getAmount();
        amount = amount + currency;

        if ("1".equals(bean.getStatus())) {
            mStatusText.setText("已撤回");
            mStatusText.setTextColor(Color.parseColor("#FB9637"));
            mStatusIcon.setVisibility(View.GONE);
            mHeader.setVisibility(View.GONE);
        } else {
            if ("0".equals(bean.getApprovalStatus())) {
                mStatusText.setText("等待处理");
                mStatusText.setTextColor(Color.parseColor("#FB9637"));
                mStatusIcon.setImageResource(R.mipmap.waiting);
                mHeader.setVisibility(View.GONE);
            } else if ("1".equals(bean.getApprovalStatus())) {
                mStatusText.setText("已通过");
                mStatusText.setTextColor(Color.parseColor("#329934"));
                mStatusIcon.setImageResource(R.mipmap.success);
                mHeader.setVisibility(View.VISIBLE);
            } else if ("2".equals(bean.getApprovalStatus())) {
                mStatusText.setText("已驳回");
                mStatusText.setTextColor(Color.parseColor("#D74049"));
                mStatusIcon.setImageResource(R.mipmap.fail);
                mHeader.setVisibility(View.VISIBLE);
            }
        }

        List<ApplyDetailBean.User> copyUsers = bean.getCopyUsers();
        if (copyUsers == null){
            copyUsers = new ArrayList<>();
        }
        String copyNames = "";
        for (int i = 0;i < copyUsers.size();i++){
            ApplyDetailBean.User user = copyUsers.get(i);
            copyNames = copyNames + " " + user.getUserName();
        }


        mNameText.setText(bean.getUserName());
        mCopyUserText.setText(copyNames);
        mTimeText.setText(bean.getCreatedTime());
        mTitleText.setText(bean.getTitle());
        mTypeText.setText(typeName);
        mEnterpriseText.setText(bean.getToEnterpriseShortName());
        mAmountText.setText(amount);
        mNameText.setText(bean.getUserName());
        mNameText.setText(bean.getUserName());
        mRemarkText.setText(bean.getRemark());


       List<ApplyDetailBean.ClaimingModel> moneyList = bean.getDetailList();
        BaseQuickAdapter<ApplyDetailBean.ClaimingModel, BaseViewHolder> adapter = new BaseQuickAdapter<ApplyDetailBean.ClaimingModel, BaseViewHolder>(R.layout.item_approvalpecess_money_list,moneyList) {
            @Override
            protected void convert(BaseViewHolder helper, ApplyDetailBean.ClaimingModel item) {
                int position = helper.getPosition() + 1;
                helper.setText(R.id.item_approvalpecess_money_position_tv, position + "")
                        .setText(R.id.item_approvalpecess_money_fee_tv, item.getCostName())
                        .setText(R.id.item_approvalpecess_money_count_tv, item.getAmount() + currency);

            }
        };

        int detailListSize = moneyList.size();
        int dp = 50 * detailListSize;
        int height = Utils.dip2px(mContext, dp);
        mMoneyList.setHasFixedSize(true);
        mMoneyList.getLayoutParams().height = height;
        mMoneyList.setNestedScrollingEnabled(false);

        LinearLayoutManager manager1 = new LinearLayoutManager(mContext);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        mMoneyList.setLayoutManager(manager1);
        mMoneyList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }


    public void setStatusOnClickListener(ClaimingStatusOnclickListener clickListener) {
        listener = clickListener;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (listener != null){
            listener.onClaimingExpenseDetailClick(mDataBean.getDetailList().get(position));
        }

    }


    public interface ClaimingStatusOnclickListener {
        void onClaimingStatusClick(int target);
        void onClaimingExpenseDetailClick(ApplyDetailBean.ClaimingModel item);
    }






}
