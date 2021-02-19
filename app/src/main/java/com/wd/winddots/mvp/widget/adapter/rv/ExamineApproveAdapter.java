package com.wd.winddots.mvp.widget.adapter.rv;

import android.app.AlertDialog;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wd.winddots.GlideApp;
import com.wd.winddots.MyApplication;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.ExamineApproveBean;
import com.wd.winddots.mvp.presenter.impl.ExamineApproveAdapterPresenterImpl;
import com.wd.winddots.mvp.view.ExamineApproveAdapterView;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.Logg;
import com.wd.winddots.utils.SpHelper;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ExamineApproveAdapter extends BaseQuickAdapter<ExamineApproveBean.ListBean, BaseViewHolder> {

    private AlertDialog mDialog;

    public final ElseDataManager dataManager;
    public final CompositeSubscription compositeSubscription;

    public ExamineApproveAdapter(int layoutResId, @Nullable List<ExamineApproveBean.ListBean> data) {
        super(layoutResId, data);

        dataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ExamineApproveBean.ListBean item) {

        GlideApp.with(mContext)
                .load(item.getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(((ImageView) helper.getView(R.id.item_examineapprove_userAvatar_iv)));

        final String userName = item.getUserName();
        helper.setText(R.id.item_examineapprove_username_tv, userName);

        String type = item.getType();

        // 条目类别 请假，报销，加班
        String title = "";
        switch (type) {
            case "0"://请假
                title = typeIs0(helper, item);
                break;
            case "1"://加班
                title = typeIs1(helper, item);
                break;
            case "4"://报销
            case "5":
                title = typeIs4(helper, item, "报销");
                break;
            case "6":
            case "7":
                title = typeIs4(helper, item, "借款");
                break;
        }


        String approvalStatus = item.getApprovalStatus();

        final TextView tvState = (TextView) helper.getView(R.id.item_examineapprove_state_tv);
        final LinearLayout btnState = (LinearLayout) helper.getView(R.id.item_examineapprove_state_btn_ll);
        TextView tvYes = (TextView) helper.getView(R.id.item_examineapprove_state_btn_yes_tv);
        TextView tvNo = (TextView) helper.getView(R.id.item_examineapprove_state_btn_no_tv);
        // 审批状态

        if (item.getUserType().equals("0")) {//身份是审核人
            switch (approvalStatus) {
                case "0"://
                    btnState.setVisibility(View.VISIBLE);
                    tvState.setVisibility(View.GONE);
                    String subTitle = "";
                    if (title.contains(":")) {
                        subTitle = title.substring(0, title.indexOf(":"));
                    }
                    final String finalSubTitle = subTitle;
                    tvYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            examineApprove(btnState, tvState, item, "1", finalSubTitle, userName);
                        }
                    });
                    tvNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            examineApprove(btnState, tvState, item, "2", finalSubTitle, userName);
                        }
                    });
                    break;
                case "1"://同意
                    btnState.setVisibility(View.GONE);
                    tvState.setVisibility(View.VISIBLE);
                    tvState.setText("(已同意)");
                    tvState.setTextColor(Color.parseColor("#2C992D"));
                    break;
                case "2"://驳回
                    btnState.setVisibility(View.GONE);
                    tvState.setVisibility(View.VISIBLE);
                    tvState.setText("(已驳回)");
                    tvState.setTextColor(Color.parseColor("#D22B34"));
                    break;
            }
        } else {
            btnState.setVisibility(View.GONE);
            tvState.setVisibility(View.GONE);
        }
    }


    private String typeIs4(BaseViewHolder helper, ExamineApproveBean.ListBean item, String type) {
        ExamineApproveBean.ListBean.DetailListBean detailListBean = item.getDetailList().get(0);
        String costName = detailListBean.getCostName();
        String amount = detailListBean.getAmount();
        String currency = TextUtils.isEmpty(item.getCurrency()) ? "人民币" : item.getCurrency();

        helper.setText(R.id.item_examineapprove_time_or_submitexpense_tv, costName + amount + currency);

        String title = type + item.getAmount() + currency + ":" + item.getTitle();
        helper.setText(R.id.item_examineapprove_content_tv, title);
        return title;
    }

    private String typeIs1(BaseViewHolder helper, ExamineApproveBean.ListBean item) {
        ExamineApproveBean.ListBean.DetailListBean detailListBean = item.getDetailList().get(0);
        String startTime = detailListBean.getStartTime();
        startTime = startTime.substring(5, startTime.length() - 3);
        String endTime = detailListBean.getEndTime();
        endTime = endTime.substring(5, endTime.length() - 3);
        helper.setText(R.id.item_examineapprove_time_or_submitexpense_tv, startTime + "～" + endTime);

        String title = "加班" + item.getLeaveDays() + "小时:" + item.getCause();
        helper.setText(R.id.item_examineapprove_content_tv, title);
        return title;
    }


    private String typeIs0(BaseViewHolder helper, ExamineApproveBean.ListBean item) {
        ExamineApproveBean.ListBean.DetailListBean detailListBean1 = item.getDetailList().get(0);
        String startTime = detailListBean1.getStartTime();
        startTime = startTime.substring(5, startTime.length() - 3);
        String endTime = detailListBean1.getEndTime();
        endTime = endTime.substring(5, endTime.length() - 3);

        helper.setText(R.id.item_examineapprove_time_or_submitexpense_tv, startTime + "～" + endTime);

        String leaveType = item.getLeaveType();
        String leaveDays = item.getLeaveDays();
        String cause = item.getCause();

        String title = leaveType + leaveDays + "天:" + cause;
        helper.setText(R.id.item_examineapprove_content_tv, leaveType + leaveDays + "天:" + cause);
        return title;
    }


    /**
     * 审批 同意或驳回
     * @param btnState
     * @param tvState
     * @param item
     * @param type
     * @param title
     * @param user
     */
    private void examineApprove(final LinearLayout btnState, final TextView tvState, final ExamineApproveBean.ListBean item, final String type, String title, String user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_examineapprove, null, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.dialog_examineapprove_title_tv);
        final EditText edtNote = (EditText) view.findViewById(R.id.dialog_examineapprove_note_edt);
        TextView tvBtn = (TextView) view.findViewById(R.id.dialog_examineapprove_btn);

        tvTitle.setText(user + title);
        if (type.equals("1")) {// 同意
            tvBtn.setText("同意");
            tvBtn.setBackgroundColor(Color.parseColor("#2C992D"));
        } else {// 驳回
            tvBtn.setText("驳回");
            tvBtn.setBackgroundColor(Color.parseColor("#D22B34"));
        }

        builder.setView(view);
        mDialog = builder.show();

        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compositeSubscription.add(dataManager.examineApprove(MyApplication.USER_ID, item.getId(), type, edtNote.getText().toString(), SpHelper.getInstance(mContext).getEnterpriseId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {
                                notifyDataSetChanged();
                                mDialog.dismiss();
                            }


                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(String data) {
                                if (!TextUtils.isEmpty(data)) {
                                    item.setApprovalStatus(type);
                                    item.setRecallType("0");
                                }
                            }
                        })
                );
            }
        });
    }


}
