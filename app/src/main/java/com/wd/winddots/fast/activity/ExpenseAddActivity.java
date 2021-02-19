package com.wd.winddots.fast.activity;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.users.MultipleUserSelectActivity;
import com.wd.winddots.components.users.SingleUserSelectActivity;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.User;
import com.wd.winddots.fast.adapter.MineClaimingAddAdapter;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.fast.bean.RelationEnterpriseBean;
import com.wd.winddots.fast.presenter.impl.MineClaimingAddPresenterImpl;
import com.wd.winddots.fast.presenter.view.MineClaimingAddView;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.TitleTextView;
import com.wd.winddots.view.selector.SelectBean;
import com.wd.winddots.view.selector.SelectView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 新建报销
 *
 * @author zhou
 */
public class ExpenseAddActivity extends CommonActivity<MineClaimingAddView, MineClaimingAddPresenterImpl>
        implements MineClaimingAddView,
        SelectView.SelectViewOnselectListener, BaseQuickAdapter.OnItemClickListener,
        MineClaimingAddAdapter.MineClaimingAddAdapterSelectListener {


    @BindView(R.id.et_title)
    EditText mTitleEt;

    @BindView(R.id.activity_calmingadd_typeselector)
    SelectView mTypeSelector;

    @BindView(R.id.view_reviewer)
    TitleTextView mReviewerCell;

    @BindView(R.id.view_copyUser)
    TitleTextView mCopyCell;

    @BindView(R.id.activity_calmingadd_currencyselector)
    SelectView mCurrencySelector;

    @BindView(R.id.activity_calmingadd_rlist)
    RecyclerView mRecyclerView;
    MineClaimingAddAdapter mAdapter;

    @BindView(R.id.activity_calmingadd_remark)
    EditText mRemarkEditText;

    @BindView(R.id.view_amount)
    TitleTextView mAmountTextView;

    @BindView(R.id.activity_calmingadd_paytypecell)
    LinearLayout mPayTypeCell;

    @BindView(R.id.activity_calmingadd_paytypeselector)
    SelectView mPayTypeSelectView;

    @BindView(R.id.view_enterprise)
    TitleTextView mEnterpriseCell;

    @BindView(R.id.activity_calmingadd_line1)
    View mLine1;

    @BindView(R.id.activity_calmingadd_line2)
    View mLine2;

    @BindView(R.id.activity_calmingadd_detailicon)
    ImageView mDetailImageView;

    private boolean isAllSelect = true;

    /**
     * 审核人
     */
    private User mReviewerUser;
    // 抄送人
    private List<User> mCopyList = new ArrayList<>();
    private List<ApplyDetailBean.ClaimingModel> claimingModels = new ArrayList<>();
    private String mTotalAmount = "0";
    private RelationEnterpriseBean.Enterprise mEnterprise; //往来单位
    private ApplyDetailBean mData;
    private String mToEnterpriseName;
    private String mToEnterpriseId;
    private String mToEnterpriseAccountId;
    private int mSelectPosition = 0;//当前编辑的费用明细条目


    private VolleyUtil mVolleyUtil;


    @Override
    public MineClaimingAddPresenterImpl initPresenter() {
        return new MineClaimingAddPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();

        addBadyView(R.layout.activity_add_expense);
        mVolleyUtil = VolleyUtil.getInstance(this);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if (StringUtils.isNullOrEmpty(data)) {
            setTitleText("新建报销");
        } else {
            setTitleText("编辑报销");
            Gson gson = new Gson();
            ApplyDetailBean bean = gson.fromJson(data, ApplyDetailBean.class);
            mData = bean;
        }

        mDetailImageView.setVisibility(View.GONE);

        mAdapter = new MineClaimingAddAdapter(R.layout.item_claiming_add, claimingModels);
        mAdapter.setMineClaimingAddAdapterSelectListener(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager1);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayout addDetailBtn = findViewById(R.id.ll_add_detail);
        addDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MineClaimingAddDetailActivity.class);
                startActivityForResult(intent, 0);
            }
        });


        LinearLayout saveBtn = findViewById(R.id.activity_calmingadd_saveBtn);
        saveBtn.setOnClickListener(new SaveBtnDidClickListener());
    }


    @Override
    public void initListener() {
        super.initListener();
        mTypeSelector.setSelectViewOnselectListener(this);
        mAdapter.setOnItemClickListener(this);
        mDetailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllSelect) {
                    isAllSelect = false;
                    mDetailImageView.setImageResource(R.mipmap.unselect);
                } else {
                    isAllSelect = true;
                    mDetailImageView.setImageResource(R.mipmap.select);
                }
                for (int i = 0; i < claimingModels.size(); i++) {
                    ApplyDetailBean.ClaimingModel model = claimingModels.get(i);
                    model.setSelect(isAllSelect);
                }
                mAdapter.notifyDataSetChanged();

            }
        });

        mReviewerCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> disableIds = new ArrayList<>();
                if (mCopyList.size() > 0) {
                    for (int i = 0; i < mCopyList.size(); i++) {
                        disableIds.add(mCopyList.get(i).getId());
                    }
                }
                disableIds.add(SpHelper.getInstance(mContext).getUserId());
                Gson gson = new Gson();
                Intent intent = new Intent(mContext, SingleUserSelectActivity.class);
                intent.putExtra("disableIds", gson.toJson(disableIds));
                startActivityForResult(intent, 0);
            }
        });

        mCopyCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> ids = new ArrayList<>();
                List<String> disableIds = new ArrayList<>();
                if (mCopyList.size() > 0) {
                    for (int i = 0; i < mCopyList.size(); i++) {
                        ids.add(mCopyList.get(i).getId());
                    }
                }
                disableIds.add(SpHelper.getInstance(mContext).getUserId());
                Gson gson = new Gson();
                Intent intent = new Intent(mContext, MultipleUserSelectActivity.class);
                intent.putExtra("disableIds", gson.toJson(disableIds));
                intent.putExtra("selectedIds", gson.toJson(ids));
                startActivityForResult(intent, 0);
            }
        });

        mEnterpriseCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RelationEnterprise.class);
                intent.putExtra("accountSelect", true);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        List<SelectBean> typeList = new ArrayList<>();
        SelectBean bean1 = new SelectBean("个人报销", "4");
        SelectBean bean2 = new SelectBean("单位报销", "5");
        SelectBean bean3 = new SelectBean("个人借款", "6");
        SelectBean bean4 = new SelectBean("单位借款", "7");
        typeList.add(bean1);
        typeList.add(bean2);
        typeList.add(bean3);
        typeList.add(bean4);
        mTypeSelector.setSelectList(typeList);

        List<SelectBean> payTypeList = new ArrayList<>();
        SelectBean payType1 = new SelectBean("收款", "0");
        SelectBean payType2 = new SelectBean("付款", "1");
        payTypeList.add(payType1);
        payTypeList.add(payType2);
        mPayTypeSelectView.setSelectList(payTypeList);

        if (mData != null) {
            mTitleEt.setText(mData.getTitle());
            ApplyDetailBean.User user = mData.getUsers().get(mData.getUsers().size() - 1);
            mReviewerCell.setContent(user.getUserName());
            mRemarkEditText.setText(mData.getRemark());
            mAmountTextView.setContent(mData.getAmount());
            mTotalAmount = mData.getAmount();

            User reviewer = new User();
            reviewer.setName(user.getUserName());
            reviewer.setId(user.getUserId());
            mReviewerUser = reviewer;

            List<ApplyDetailBean.User> copyUsers = mData.getCopyUsers();
            List<User> list = new ArrayList<>();
            if (copyUsers == null) {
                copyUsers = new ArrayList<>();
            }
            String copyNames = "";
            for (int i = 0; i < copyUsers.size(); i++) {
                User userBean = new User();
                ApplyDetailBean.User user1 = copyUsers.get(i);
                copyNames = copyNames + " " + user1.getUserName();
                userBean.setId(user1.getUserId());
                userBean.setName(user1.getUserName());
                list.add(userBean);
            }
            mCopyCell.setContent(copyNames);
            mCopyList = list;

            switch (mData.getType()) {
                case Const.APPROVA_INVOICE_4:
                    mTypeSelector.setDefaultPosition(0);
                    mEnterpriseCell.setVisibility(View.GONE);
                    mPayTypeCell.setVisibility(View.GONE);
                    break;
                case Const.APPROVA_INVOICE_5:
                    mTypeSelector.setDefaultPosition(1);
                    mEnterpriseCell.setVisibility(View.VISIBLE);
                    mEnterpriseCell.setVisibility(View.GONE);
                    mEnterpriseCell.setContent(mData.getToEnterpriseName());
                    mToEnterpriseName = mData.getToEnterpriseName();
                    mToEnterpriseId = mData.getToEnterpriseId();
                    mToEnterpriseAccountId = mData.getToEnterpriseAccountId();
                    break;
                case Const.APPROVA_INVOICE_6:
                    mTypeSelector.setDefaultPosition(2);
                    mEnterpriseCell.setVisibility(View.GONE);
                    mEnterpriseCell.setVisibility(View.GONE);
                    break;
                case Const.APPROVA_INVOICE_7:
                    mTypeSelector.setDefaultPosition(3);
                    mEnterpriseCell.setVisibility(View.VISIBLE);
                    mEnterpriseCell.setVisibility(View.VISIBLE);
                    mEnterpriseCell.setContent(mData.getToEnterpriseName());
                    mToEnterpriseName = mData.getToEnterpriseName();
                    mToEnterpriseId = mData.getToEnterpriseId();
                    mToEnterpriseAccountId = mData.getToEnterpriseAccountId();
                    if ("0".equals(mData.getPayType())) {
                        mPayTypeSelectView.setDefaultPosition(0);
                    } else {
                        mPayTypeSelectView.setDefaultPosition(1);
                    }
                    break;
            }

            claimingModels.addAll(mData.getDetailList());
            if (claimingModels.size() > 0) {
                mDetailImageView.setVisibility(View.VISIBLE);
                mDetailImageView.setImageResource(R.mipmap.select);
            }
            mAdapter.notifyDataSetChanged();


        }
        presenter.getCurrencyList(SpHelper.getInstance(mContext).getEnterpriseId());
    }

    /*
     * 获取类型
     * */
    @Override
    public void getCurrencyListSuccess(List<SelectBean> currencyList) {
        mCurrencySelector.setSelectList(currencyList);
        if (mData != null) {
            for (int i = 0; i < currencyList.size(); i++) {
                SelectBean bean = currencyList.get(i);
                if (bean.getName().equals(mData.getCurrency())) {
                    mCurrencySelector.setDefaultPosition(i);
                    break;
                }
            }
        }
    }

    @Override
    public void getCurrencyListerror() {
    }

    @Override
    public void getCurrencyListCompleted() {
    }


    /*
     * 费用明细改变
     * */
    private void listVieDataChange() {
        if (claimingModels.size() > 0) {
            mDetailImageView.setVisibility(View.VISIBLE);
            mDetailImageView.setImageResource(R.mipmap.select);
        }
        int dp = 50 * claimingModels.size();
        int height = dip2px(mContext, dp);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.getLayoutParams().height = height;
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }

        if (resultCode == Const.SINGLE_USER_SELECT_TARGRT) {
            // 选择审核人
            User user = (User) data.getSerializableExtra("user");
            mReviewerCell.setContent(user.getName());
            mReviewerUser = user;
        } else if (resultCode == Const.MULTIPLE_USER_SELECT_TARGRT) {
            // 选择抄送人
            List<User> list = (List<User>) data.getSerializableExtra("list");
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                User item = list.get(i);
                name.append(" ").append(item.getName());
            }
            mCopyCell.setContent(name.toString());
            mCopyList.clear();
            mCopyList.addAll(list);
        } else if (resultCode == 250) {//费用详情
            Gson gson = new Gson();
            String itemS = data.getStringExtra("data");

            Log.e("88888", itemS);
            ApplyDetailBean.ClaimingModel item = gson.fromJson(itemS, ApplyDetailBean.ClaimingModel.class);
            item.setSelect(true);

            float addressAmount = 0;
            List<ApplyDetailBean.ClaimingDetail> list = item.getAddresses();
            for (int i = 0; i < list.size(); i++) {
                ApplyDetailBean.ClaimingDetail detail = list.get(i);
                Float amount = Float.parseFloat(detail.getAmount());
                addressAmount = addressAmount + amount;
            }

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            Float totalAmount = Float.parseFloat(mTotalAmount);
            totalAmount = addressAmount + totalAmount;
            String amountS = String.valueOf(nf.format(addressAmount));
            item.setAmount(amountS);
            if (requestCode == 200) {
                ApplyDetailBean.ClaimingModel deleteItem = claimingModels.get(mSelectPosition);
                Float deleteAmount = Float.parseFloat(deleteItem.getAmount());
                totalAmount = totalAmount - deleteAmount;
                claimingModels.remove(mSelectPosition);
            }
            claimingModels.add(item);
            mTotalAmount = String.valueOf(totalAmount);
            mAmountTextView.setContent(mTotalAmount);
            listVieDataChange();
        } else if (resultCode == 300) {//往来单位
            Gson gson = new Gson();
            String itemS = data.getStringExtra("data");
            RelationEnterpriseBean.Enterprise enterprise = gson.fromJson(itemS, RelationEnterpriseBean.Enterprise.class);
            mToEnterpriseId = enterprise.getId();
            mToEnterpriseName = enterprise.getName();
            mToEnterpriseAccountId = enterprise.getExchangeEnterpriseAccountList().get(enterprise.getAccountPosition()).getId();
            mEnterpriseCell.setContent(enterprise.getName());
            mEnterprise = enterprise;
        }
    }

    /*
     * 点击费用明细条目
     * */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mSelectPosition = position;
        Intent intent = new Intent(mContext, MineClaimingAddDetailActivity.class);
        ApplyDetailBean.ClaimingModel model = claimingModels.get(position);
        Gson gson = new Gson();
        String dataS = gson.toJson(model);
        intent.putExtra("data", dataS);
        startActivityForResult(intent, 200);
    }

    /*
     * 选择报销类型
     * */
    @Override
    public void onselect(int position, SelectView view) {
        if (position == 0 || position == 2) {
            mEnterpriseCell.setVisibility(View.GONE);
            mPayTypeCell.setVisibility(View.GONE);
            mLine1.setVisibility(View.GONE);
            mLine2.setVisibility(View.GONE);
        } else if (position == 1) {
            mPayTypeCell.setVisibility(View.GONE);
            mEnterpriseCell.setVisibility(View.VISIBLE);
            mLine1.setVisibility(View.VISIBLE);
            mLine2.setVisibility(View.GONE);
        } else {
            mPayTypeCell.setVisibility(View.VISIBLE);
            mEnterpriseCell.setVisibility(View.VISIBLE);
            mLine1.setVisibility(View.VISIBLE);
            mLine2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void selectIconDidClick() {
        for (int i = 0; i < claimingModels.size(); i++) {
            ApplyDetailBean.ClaimingModel model = claimingModels.get(i);
            if (!model.isSelect()) {
                mDetailImageView.setImageResource(R.mipmap.unselect);
                return;
            }
        }
        mDetailImageView.setImageResource(R.mipmap.select);
    }

    /*
     * 点击保存按钮
     * */
    private class SaveBtnDidClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            Map data = new HashMap<>();
            String title = mTitleEt.getText().toString().trim();
            String remark = mRemarkEditText.getText().toString().trim();
            String type = mTypeSelector.getItem().getValue();
            String currency = mCurrencySelector.getItem().getName();
            if (StringUtils.isNullOrEmpty(title)) {
                showToast("请先输入事件说明");
                return;
            }

            if (StringUtils.isNullOrEmpty(remark)) {
                remark = "";
            }

            if (mCopyList.size() == 0) {
                showToast("请先选择抄送人");
                return;
            }

            if (mReviewerUser == null) {
                showToast("请先选择审核人");
                return;
            }

            if (claimingModels.size() == 0) {
                showToast("请先添加报销明细");
                return;
            }

            if (Const.APPROVA_INVOICE_5.equals(type) || Const.APPROVA_INVOICE_7.equals(type)) {

                if (StringUtils.isNullOrEmpty(mToEnterpriseId) || StringUtils.isNullOrEmpty(mToEnterpriseAccountId)) {
                    showToast("请先选择往来单位");
                    return;
                }
                data.put("toEnterpriseId", mToEnterpriseId);
                data.put("toEnterpriseAccountId", mToEnterpriseAccountId);

                if (Const.APPROVA_INVOICE_7.equals(type)) {
                    String paytype = mPayTypeSelectView.getItem().getValue();
                    data.put("payType", paytype);
                }
            }

            Gson gson = new Gson();
            List<Map> users = new ArrayList<>();
            for (int i = 0; i < mCopyList.size(); i++) {
                User userBean = mCopyList.get(i);
                Map<String, String> map = new HashMap<>();
                map.put("userId", userBean.getId());
                map.put("type", "1");
                users.add(map);
            }
            Map<String, String> reviewerMap = new HashMap<>();
            reviewerMap.put("userId", mReviewerUser.getId());
            reviewerMap.put("type", "0");
            users.add(reviewerMap);

            List<Map> detailList = new ArrayList<>();
            for (int i = 0; i < claimingModels.size(); i++) {
                ApplyDetailBean.ClaimingModel model = claimingModels.get(i);
                if (!model.isSelect()) {
                    continue;
                }
                Map modelMap = new HashMap();
                modelMap.put("costName", model.getCostName());
                modelMap.put("remark", model.getRemark());
                modelMap.put("deptId", model.getDeptId());
                List<Map> addresses = new ArrayList<>();
                for (int m = 0; m < model.getAddresses().size(); m++) {
                    ApplyDetailBean.ClaimingDetail detail = model.getAddresses().get(m);
                    List<Map> invoices = new ArrayList<>();
                    if (detail.getInvoices().size() > 0) {
                        for (int n = 0; n < detail.getInvoices().size(); n++) {
                            ApplyDetailBean.Invoice invoice = detail.getInvoices().get(n);
                            if (!StringUtils.isNullOrEmpty(invoice.getInvoiceImage())) {
                                Map invoiceMap = new HashMap();
                                invoiceMap.put("invoiceImage", invoice.getInvoiceImage());
                                invoices.add(invoiceMap);
                            }
                        }
                    }
                    Map map = new HashMap();
                    map.put("invoice", detail.getInvoice());
                    map.put("amount", detail.getAmount());
                    map.put("projectTitle", detail.getProjectTitle());
                    map.put("time", detail.getTime());
                    if (Const.INVOICE_TYPE_NO_ALREADY.equals(detail.getInvoice())) {
                        map.put("reachTime", detail.getReachTime());
                    }
                    map.put("invoices", invoices);
                    addresses.add(map);
                }
                modelMap.put("addresses", addresses);
                detailList.add(modelMap);
            }

            if (detailList.size() == 0) {
                showToast("请先添加报销明细");
                return;
            }
            data.put("title", title);
            data.put("users", users);
            data.put("remark", remark);
            data.put("detailList", detailList);
            data.put("type", type);
            data.put("currency", currency);
            data.put("createdBy", SpHelper.getInstance(mContext).getUserId());
            if (mData != null) {
                data.put("id", mData.getId());
            } else {

            }
            String bodyString = gson.toJson(data);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
            Log.e("body", bodyString);
            showLoading();
            String url = Constant.APP_BASE_URL_ELSE;
            if (mData != null) {//修改
                presenter.updateClaiming(requestBody, SpHelper.getInstance(mContext).getEnterpriseId());
            } else {//新增
                presenter.addClaiming(requestBody, SpHelper.getInstance(mContext).getEnterpriseId());
            }
        }
    }


    @Override
    public void addClaimingtSuccess() {
//        try {
//            Thread.sleep(1000);
//        } catch (Exception e) {
//        }
        hideLoading();
        showToast("添加报销成功");
        Intent intent = new Intent();
        intent.putExtra("data", "data");
        setResult(250, intent);
        finish();
    }

    @Override
    public void addClaimingError() {
//        try {
//            Thread.sleep(1000);
//        } catch (Exception e) {
//        }
        showToast("添加报销失败");
        hideLoading();
    }

    @Override
    public void addClaimingCompleted() {
    }

    @Override
    public void updateClaimingtSuccess() {
//        try {
//            Thread.sleep(1000);
//        } catch (Exception e) {
//        }
        hideLoading();
        showToast("编辑报销成功");
        hideLoading();
        Intent intent = new Intent();
        intent.putExtra("data", "data");
        setResult(250, intent);
        finish();
    }

    @Override
    public void updateClaimingError() {
//        try {
//            Thread.sleep(1000);
//        } catch (Exception e) {
//        }
        hideLoading();
        showToast("编辑报销失败");
    }

    @Override
    public void updateClaimingCompleted() {
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }


        return super.dispatchTouchEvent(me);
    }


    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
