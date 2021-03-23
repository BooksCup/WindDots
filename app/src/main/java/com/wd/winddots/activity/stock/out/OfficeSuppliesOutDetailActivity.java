package com.wd.winddots.activity.stock.out;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.client.android.CaptureActivity2;
import com.kevin.photo_browse.PhotoBrowse;
import com.kevin.photo_browse.ShowType;
import com.kevin.photo_browse.callabck.ClickCallback;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.select.SelectGoodsActivity;
import com.wd.winddots.activity.select.SelectWareHouseActivity;
import com.wd.winddots.adapter.image.ImageBrowserAdapter;
import com.wd.winddots.adapter.stock.in.StockGoodsSpecAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Goods;
import com.wd.winddots.entity.GoodsSpec;
import com.wd.winddots.entity.StockApplicationInRecord;
import com.wd.winddots.entity.StockInApply;
import com.wd.winddots.entity.WareHouse;
import com.wd.winddots.enums.StockApplyStatusEnum;
import com.wd.winddots.utils.BigDecimalUtil;
import com.wd.winddots.utils.CollectionUtil;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.dialog.ConfirmDialog;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 办公用品入库详情
 * (操作人控制台)
 *
 * @author zhou
 */
public class OfficeSuppliesOutDetailActivity extends BaseActivity implements StockGoodsSpecAdapter.TextChangeListener {

    private static final int REQUEST_CODE_GOODS = 2;
    private static final int REQUEST_CODE_WARE_HOUSE = 1;
    private static final int REQUEST_CODE_AUDITOR = 4;
    private static final int REQUEST_CODE_SCAN = 7;

    private static final String SPACE_SEPARATOR = "   ";

    @BindView(R.id.rl_goods)
    RelativeLayout mGoodsRl;

    @BindView(R.id.ll_remark)
    LinearLayout mRemarkLl;

    @BindView(R.id.et_remark)
    EditText mRemarkEt;

    @BindView(R.id.view_remark_divider)
    View mRemarkDividerView;

    @BindView(R.id.tv_auditor)
    TextView mAuditorTv;

    @BindView(R.id.rv_image)
    RecyclerView mImageRv;

    // 物品
    @BindView(R.id.ll_goods_content)
    LinearLayout mGoodsContentLl;

    @BindView(R.id.tv_goods_info)
    TextView mGoodsInfoTv;

    @BindView(R.id.tv_stock_num)
    TextView mStockNumTv;

    @BindView(R.id.tv_attr1)
    TextView mAttr1Tv;

    @BindView(R.id.tv_attr2)
    TextView mAttr2Tv;

    @BindView(R.id.sdv_goods_photo)
    SimpleDraweeView mGoodsPhotoSdv;

    @BindView(R.id.ll_goods_spec)
    LinearLayout mGoodsSpecLl;

    @BindView(R.id.iv_goods_expand)
    ImageView mGoodsExpandIv;

    @BindView(R.id.rv_goods_spec)
    RecyclerView mGoodsSpecRv;

    @BindView(R.id.tv_goods_spec_x)
    TextView mGoodsSpecXTv;

    @BindView(R.id.tv_goods_spec_y)
    TextView mGoodsSpecYTv;

    @BindView(R.id.tv_apply_num_header)
    TextView mApplyNumHeaderTv;

    @BindView(R.id.tv_place_holder)
    TextView mPlaceHolderTv;

    @BindView(R.id.ll_ware_house)
    LinearLayout mWareHouseLl;

    @BindView(R.id.tv_ware_house)
    TextView mWareHouseTv;

    @BindView(R.id.iv_ware_house)
    ImageView mWareHouseIv;

    @BindView(R.id.ll_operate)
    LinearLayout mOperateLl;

    @BindView(R.id.tv_create_user)
    TextView mCreateUserTv;

    @BindView(R.id.tv_apply_num)
    TextView mApplyNumTv;

    @BindView(R.id.tv_confirm_num)
    TextView mConfirmNumTv;

    ImageBrowserAdapter mImageBrowserAdapter;
    StockGoodsSpecAdapter mStockGoodsSpecAdapter;

    String mGoodsId;
    String mWareHouseId;
    // 审核人用户ID
    String mAuditorId;

    List<String> mImageList = new ArrayList<>();

    VolleyUtil mVolleyUtil;
    String mStockInApplyId;
    boolean mIsSkuEditable = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_supplies_out_detail);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initData();
        initListener();
    }

    @OnClick({R.id.iv_back, R.id.rl_goods, R.id.ll_goods_content,
            R.id.ll_ware_house, R.id.tv_reject, R.id.tv_scan,
            R.id.tv_confirm})
    public void onClick(View v) {
        Intent intent;
        final ConfirmDialog mConfirmDialog;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_goods:
                // 物品
                if (TextUtils.isEmpty(mGoodsId)) {
                    // 未选择物品
                    intent = new Intent(OfficeSuppliesOutDetailActivity.this, SelectGoodsActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_GOODS);
                } else {
                    // 选择物品
                    if (mGoodsContentLl.getVisibility() == View.GONE) {
                        mGoodsContentLl.setVisibility(View.VISIBLE);
                    } else {
                        mGoodsContentLl.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.ll_goods_content:
                if (mGoodsSpecLl.getVisibility() == View.GONE) {
                    // 展开
                    mGoodsExpandIv.setBackgroundResource(R.mipmap.icon_up);
                    mGoodsSpecLl.setVisibility(View.VISIBLE);
                } else {
                    // 关闭
                    mGoodsExpandIv.setBackgroundResource(R.mipmap.icon_down);
                    mGoodsSpecLl.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_ware_house:
                intent = new Intent(OfficeSuppliesOutDetailActivity.this, SelectWareHouseActivity.class);
                startActivityForResult(intent, REQUEST_CODE_WARE_HOUSE);
                break;
            case R.id.tv_reject:
                mConfirmDialog = new ConfirmDialog(this, "确认驳回",
                        "是否确认驳回入库单？",
                        "是", "否");
                mConfirmDialog.setOnDialogClickListener(new ConfirmDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        updateStockApplication(StockApplyStatusEnum.STOCK_APPLY_STATUS_REJECT.getStatus());
                    }

                    @Override
                    public void onCancelClick() {
                        mConfirmDialog.dismiss();
                    }
                });
                // 点击空白处消失
                mConfirmDialog.setCancelable(false);
                mConfirmDialog.show();

                break;
            case R.id.tv_scan:
                startScanActivity();
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(mWareHouseId)) {
                    showToast("请选择入库仓库");
                    return;
                }
                mConfirmDialog = new ConfirmDialog(this, "确认",
                        "是否确认入库单？",
                        "是", "否");
                mConfirmDialog.setOnDialogClickListener(new ConfirmDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        updateStockApplication(StockApplyStatusEnum.STOCK_APPLY_STATUS_CONFIRMED.getStatus());
                    }

                    @Override
                    public void onCancelClick() {
                        mConfirmDialog.dismiss();
                    }
                });
                // 点击空白处消失
                mConfirmDialog.setCancelable(false);
                mConfirmDialog.show();

                break;
        }
    }

    private void startScanActivity() {
        Intent intent = new Intent(OfficeSuppliesOutDetailActivity.this, CaptureActivity2.class);
        intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }


    private void initView() {
        mStockInApplyId = getIntent().getStringExtra("stockOutApplyId");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mImageRv.setLayoutManager(gridLayoutManager);
        mImageBrowserAdapter = new ImageBrowserAdapter(this);
        mImageRv.setAdapter(mImageBrowserAdapter);

        LinearLayoutManager goodsSpecLinearLayoutManager = new LinearLayoutManager(this);
        mGoodsSpecRv.setLayoutManager(goodsSpecLinearLayoutManager);
        mGoodsSpecRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mStockGoodsSpecAdapter = new StockGoodsSpecAdapter(this);
        mGoodsSpecRv.setAdapter(mStockGoodsSpecAdapter);

        getStockInApplyById(mStockInApplyId);
    }

    private void initData() {
        mImageList = new ArrayList<>();
        mImageBrowserAdapter.setList(mImageList);

        List<GoodsSpec> goodsSpecList = new ArrayList<>();
        mStockGoodsSpecAdapter.setList(goodsSpecList);
    }

    private void initListener() {
        mImageBrowserAdapter.setOnRecyclerItemClickListener(position -> {
            List<String> imageList = mImageBrowserAdapter.getList();
            if (null == imageList) {
                imageList = new ArrayList<>();
            }
            if (!CollectionUtil.isEmpty(imageList)) {
                PhotoBrowse.with(this)
                        .showType(ShowType.MULTIPLE_URL)
                        .url(imageList)
                        .title("")
                        .position(position)
                        .callback(new ClickCallback() {
                            @Override
                            public void onClick(Activity activity, String url, int position) {
                                super.onClick(activity, url, position);
                            }

                            @Override
                            public void onLongClick(Activity activity, String url, int position) {
                                super.onLongClick(activity, url, position);
                            }
                        })
                        .show();
            }
        });

        mStockGoodsSpecAdapter.setTextChangeListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GOODS:
                    // 物品
                    if (null != data) {
                        Goods goods = (Goods) data.getSerializableExtra("goods");
                        renderGoodsView(goods);
                    }
                    break;
                case REQUEST_CODE_AUDITOR:
                    // 审核人
                    if (null != data) {
                        String userId = data.getStringExtra("userId");
                        String userName = data.getStringExtra("userName");
                        mAuditorId = userId;
                        mAuditorTv.setText(userName);
                        mAuditorTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
                    }
                    break;
                case REQUEST_CODE_SCAN:
                    // 扫码
                    if (null != data) {
                        String content = data.getStringExtra("CaptureIsbn");
                        if (content.contains(Constant.QR_CODE_CONTENT_PREFIX_GOODS)) {
                            String goodsId = content.replaceAll(Constant.QR_CODE_CONTENT_PREFIX_GOODS, "");
                            getGoodsById(goodsId);
                        }
                    }
                    break;
                case REQUEST_CODE_WARE_HOUSE:
                    // 仓库
                    if (null != data) {
                        WareHouse wareHouse = (WareHouse) data.getSerializableExtra("wareHouse");
                        mWareHouseId = wareHouse.getId();
                        mWareHouseTv.setText(wareHouse.getName());
                        mWareHouseTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
                    }
                    break;
            }
        }
    }

    /**
     * 渲染物品相关页面
     *
     * @param goods 物品
     */
    private void renderGoodsView(Goods goods) {
        mGoodsId = goods.getId();

        mGoodsContentLl.setVisibility(View.VISIBLE);

        String goodsInfo = goods.getGoodsName() + "(" + goods.getGoodsNo() + ")";

        String stockInfo;
        if (!TextUtils.isEmpty(goods.getGoodsUnit())) {
            stockInfo = goods.getStockNum() + goods.getGoodsUnit();
            mApplyNumHeaderTv.setText("入库数量(" + goods.getGoodsUnit() + ")");
        } else {
            stockInfo = goods.getStockNum();
            mApplyNumHeaderTv.setText("入库数量");
        }

        mGoodsInfoTv.setText(goodsInfo);
        mStockNumTv.setText(stockInfo);

        List<String> attrList = CommonUtil.attrJsonToAttrStrList(goods.getAttrList());
        if (null != attrList) {
            if (attrList.size() == 0) {
                mAttr1Tv.setVisibility(View.INVISIBLE);
                mAttr2Tv.setVisibility(View.INVISIBLE);
            } else if (attrList.size() == 1) {
                mAttr1Tv.setVisibility(View.VISIBLE);
                mAttr2Tv.setVisibility(View.INVISIBLE);
                mAttr1Tv.setText(attrList.get(0));
            } else if (attrList.size() >= 2) {
                mAttr1Tv.setVisibility(View.VISIBLE);
                mAttr2Tv.setVisibility(View.VISIBLE);
                mAttr1Tv.setText(attrList.get(0));
                mAttr2Tv.setText(attrList.get(1));
            }
        }

        String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(goods.getGoodsPhotos());
        if (!TextUtils.isEmpty(goodsPhoto)) {
            mGoodsPhotoSdv.setImageURI(Uri.parse(goodsPhoto));
        } else {
            mGoodsPhotoSdv.setImageResource(R.mipmap.icon_default_goods);
        }

        List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
        mStockGoodsSpecAdapter.setEditable(mIsSkuEditable);
        mStockGoodsSpecAdapter.setList(goodsSpecList);

        if (TextUtils.isEmpty(goods.getY())) {
            mGoodsSpecYTv.setVisibility(View.GONE);
            mPlaceHolderTv.setVisibility(View.GONE);
            mGoodsSpecXTv.setText(goods.getX());
        } else {
            mGoodsSpecYTv.setVisibility(View.VISIBLE);
            mPlaceHolderTv.setVisibility(View.VISIBLE);
            mGoodsSpecXTv.setText(goods.getX());
            mGoodsSpecYTv.setText(goods.getY());
        }

        BigDecimal applyNum = new BigDecimal(0);
        BigDecimal confirmNum = new BigDecimal(0);
        for (GoodsSpec goodsSpec : goodsSpecList) {
            applyNum = BigDecimalUtil.add(applyNum, goodsSpec.getApplyNum());
            confirmNum = BigDecimalUtil.add(confirmNum, goodsSpec.getNum());
        }
        mApplyNumTv.setText(String.valueOf(applyNum.intValue()));
        if (!mIsSkuEditable) {
            mConfirmNumTv.setText(String.valueOf(confirmNum.intValue()));
        }

    }

    @Override
    public void stockInNumChange() {
        List<GoodsSpec> goodsSpecList = mStockGoodsSpecAdapter.getList();
        float total = 0;
        NumberFormat nf = NumberFormat.getNumberInstance();
        for (GoodsSpec goodsSpec : goodsSpecList) {
            nf.setMaximumFractionDigits(2);
            float totalStockInNum = Float.parseFloat(Utils.numberNullOrEmpty(goodsSpec.getNum()));
            total = totalStockInNum + total;
        }
        mConfirmNumTv.setText(nf.format(total));
    }

    private void updateStockApplication(String applyStatus) {

        List<GoodsSpec> goodsSpecList = mStockGoodsSpecAdapter.getList();
        List<StockApplicationInRecord> stockApplicationInRecordList = new ArrayList<>();
        if (!StockApplyStatusEnum.STOCK_APPLY_STATUS_REJECT.getStatus().equals(applyStatus)) {
            // 确认
            for (GoodsSpec goodsSpec : goodsSpecList) {
                StockApplicationInRecord in = new StockApplicationInRecord();
                in.setGoodsSpecId(goodsSpec.getId());
                in.setStockApplicationId(mStockInApplyId);
                in.setWareHouseId(mWareHouseId);
                in.setCount(goodsSpec.getApplyNum());
                in.setResidualNumber(goodsSpec.getApplyNum());
                stockApplicationInRecordList.add(in);
            }
        }

        showLoadingDialog();

        StockInApply stockInApply = new StockInApply();
        stockInApply.setApplyStatus(applyStatus);
        stockInApply.setStockApplicationInRecordList(stockApplicationInRecordList);


        String url = Constant.APP_BASE_URL + "stockApplication/" + mStockInApplyId;
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("stockApplicationJson", JSON.toJSONString(stockInApply));

        mVolleyUtil.httpPutRequest(url, paramMap, response -> {
            if (applyStatus.equals(StockApplyStatusEnum.STOCK_APPLY_STATUS_REJECT.getStatus())) {
                // 驳回
                showToast("驳回成功");
            } else {
                // 确认
                showToast("确认成功");
            }
            hideLoadingDialog();
            finish();
        }, volleyError -> {
            hideLoadingDialog();
            mVolleyUtil.handleCommonErrorResponse(OfficeSuppliesOutDetailActivity.this, volleyError);
        });
    }

    private void getGoodsById(String goodsId) {
        showLoadingDialog();
        String url = Constant.APP_BASE_URL + "goods/" + goodsId;
        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            Goods goods;
            try {
                goods = JSON.parseObject(response, Goods.class);
            } catch (Exception e) {
                goods = null;
            }
            if (null != goods) {
                renderGoodsView(goods);
            }
        }, volleyError -> {
            hideLoadingDialog();
            mVolleyUtil.handleCommonErrorResponse(OfficeSuppliesOutDetailActivity.this, volleyError);
        });
    }

    private void getStockInApplyById(String applyId) {

        showLoadingDialog();
        String url = Constant.APP_BASE_URL + "stockApplication/" + applyId;
        Log.e("console",url);
        mVolleyUtil.httpGetRequest(url, response -> {
            StockInApply stockInApply;
            try {
                stockInApply = JSON.parseObject(response, StockInApply.class);
            } catch (Exception e) {
                stockInApply = null;
            }
            Log.e("stockInApply", String.valueOf(stockInApply));
            if (StockApplyStatusEnum.STOCK_APPLY_STATUS_CONFIRMED.getStatus().equals(stockInApply.getApplyStatus())) {
                mOperateLl.setVisibility(View.GONE);
                mWareHouseLl.setClickable(false);
                mGoodsRl.setClickable(false);
                mGoodsContentLl.setClickable(false);

                List<StockApplicationInRecord> inList = stockInApply.getStockApplicationInRecordList();
                if (!CollectionUtil.isEmpty(inList)) {
                    String wareHouseName = inList.get(0).getWareHouseName();
                    mWareHouseTv.setText(wareHouseName);
                    mWareHouseTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
                    mWareHouseIv.setVisibility(View.GONE);
                }
                mIsSkuEditable = false;

            } else {
                mOperateLl.setVisibility(View.VISIBLE);
                mIsSkuEditable = true;
            }

            if (null != stockInApply) {
                renderGoodsView(CommonUtil.getGoodsFromStockInApply(stockInApply));
            }
            mGoodsId = stockInApply.getStockGoodsId();

            mRemarkEt.setEnabled(false);
            if (!TextUtils.isEmpty(stockInApply.getRemark())) {
                mRemarkEt.setText(stockInApply.getRemark());
                mRemarkEt.setTextColor(ContextCompat.getColor(this, R.color.color32));
            } else {
//                mRemarkEt.setText(" ");
                mRemarkLl.setVisibility(View.GONE);
                mRemarkDividerView.setVisibility(View.GONE);
            }
            mAuditorId = stockInApply.getAuditUserId();
            mAuditorTv.setText(stockInApply.getAuditUserName());
            mAuditorTv.setTextColor(ContextCompat.getColor(this, R.color.color32));

            mCreateUserTv.setText(stockInApply.getCreateUserName() + SPACE_SEPARATOR + stockInApply.getCreateTime());

            try {
                mImageList = JSON.parseArray(stockInApply.getStockImg(), String.class);
            } catch (Exception e) {
                mImageList = new ArrayList<>();
            }
            if (CollectionUtil.isEmpty(mImageList)) {
                mImageRv.setVisibility(View.GONE);
            } else {
                mImageRv.setVisibility(View.VISIBLE);
                mImageBrowserAdapter.setList(mImageList);
            }

            hideLoadingDialog();
        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(OfficeSuppliesOutDetailActivity.this, volleyError);

            hideLoadingDialog();
        });
    }

}
