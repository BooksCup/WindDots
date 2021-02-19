package com.wd.winddots.fast.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.fast.adapter.MineClaimingDetailAdapter;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.fast.view.ClaimingHeaderView;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.TimeLineHeaderFooterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: MineClaimingDetailActivity
 * Author: 郑
 * Date: 2020/5/5 11:37 AM
 * Description: 报销详情
 */
public class MineClaimingDetailActivity extends CommonActivity
        implements  ClaimingHeaderView.ClaimingStatusOnclickListener {


    private RecyclerView mRecyclerView;
    private ClaimingHeaderView mHeaderView;
    private TimeLineHeaderFooterView mFooter;
    private TextView mBottomBar;

    private MineClaimingDetailAdapter mAdapter;
    private List<ApplyDetailBean.User> mUsers = new ArrayList<>();


    private ApplyDetailBean mDataBean;
    private String id;

    private VolleyUtil mVolleyUtil;

    @Override
    public BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        super.initView();

        addBadyView(R.layout.activity_mine_claiming_detail);
        mVolleyUtil = VolleyUtil.getInstance(this);

        setTitleText("报销");

        mRecyclerView = findViewById(R.id.activity_mine_claimingdetail_rlist);
        mBottomBar = findViewById(R.id.activity_mine_claimingdetail_bottombar);
        mAdapter = new MineClaimingDetailAdapter(R.layout.item_approvalpecess_time_line, mUsers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mHeaderView = new ClaimingHeaderView(mContext);
        mAdapter.setHeaderView(mHeaderView);
        mHeaderView.setStatusOnClickListener(this);

        mFooter = new TimeLineHeaderFooterView(mContext);
        mFooter.setmText(mContext.getString(R.string.time_line_footer));
        mAdapter.setFooterView(mFooter);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
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
                                //presenter.recallApply(mDataBean.getId());
                                String url = Constant.APP_BASE_URL_ELSE + "/cloud-app/apply/recall?id=" + mDataBean.getId();
                                mVolleyUtil.httpDeleteRequest(url,response -> {
                                    Log.e("net",response);
                                    if (StringUtils.isNullOrEmpty(response)){
                                        showToast("撤回失败,请稍后重试");
                                    }else {
                                        Map map = Utils.getMapForJson(response);
                                        if (map != null && "0".equals(map.get("code"))){
                                            showToast("撤回成功");
                                            finish();
                                        }else {
                                            showToast("撤回失败,请稍后重试");
                                        }
                                    }
                                }, volleyError->{
                                    mVolleyUtil.handleCommonErrorResponse(MineClaimingDetailActivity.this, volleyError);
                                });


                            }
                        });

                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    } else if ("2".equals(mDataBean.getApprovalStatus())) {
                        Intent intent = new Intent(mContext, ExpenseAddActivity.class);
                        Gson gson = new Gson();
                        String dataS = gson.toJson(mDataBean);
                        intent.putExtra("data",dataS);
                        startActivityForResult(intent,100);
                    } else {
                        mBottomBar.setVisibility(View.GONE);
                    }
                } else {
                    Intent intent = new Intent(mContext, ExpenseAddActivity.class);
                    Gson gson = new Gson();
                    String dataS = gson.toJson(mDataBean);
                    intent.putExtra("data",dataS);
                    startActivityForResult(intent,100);
                }

            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        getData();
    }


    /*
    * 获取数据
    * */
    private void getData(){
        String url = Constant.APP_BASE_URL_ELSE + "cloud-app/apply/getById/" + id + "?userId=" + SpHelper.getInstance(mContext).getEnterpriseId()+"&parentId=0";
        mVolleyUtil.httpGetRequest(url, response -> {
            ApplyDetailBean bean = JSON.parseObject(response,ApplyDetailBean.class);
            mDataBean = bean;
            mUsers.clear();
            mUsers.addAll(bean.getUsers());
            mAdapter.notifyDataSetChanged();
            mHeaderView.setData(bean);
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
        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(this, volleyError);
        });
    }

    /*
     * 展开和收起列表
     * */
    @Override
    public void onClaimingStatusClick(int target) {
        if (target == 0) {
            mUsers.clear();
            //mAdapter.setFooterView(new View(mContext));
            mFooter.setVisibility(View.GONE);
        } else {
            mUsers.addAll(mDataBean.getUsers());
            mFooter.setVisibility(View.VISIBLE);
            //mAdapter.setFooterView(mFooter);
        }
        mAdapter.notifyDataSetChanged();
    }

    /*
    * 点击费用明细
    * */
    @Override
    public void onClaimingExpenseDetailClick(ApplyDetailBean.ClaimingModel item) {
        Intent intent = new Intent(mContext,ClaimingExpenseDetailActivity.class);
        Gson gson = new Gson();
        String jsonS = gson.toJson(item);
        String invoiceS = gson.toJson(mDataBean);
        intent.putExtra("data",jsonS);
        intent.putExtra("invoice",invoiceS);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            //presenter.getMineClaimingDetail(id, SpHelper.getInstance(mContext).getUserId());
            getData();
        }
    }
}
