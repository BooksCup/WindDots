package com.wd.winddots.activity.stock.in;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.client.android.CaptureActivity2;
import com.kevin.photo_browse.PhotoBrowse;
import com.kevin.photo_browse.ShowType;
import com.kevin.photo_browse.callabck.ClickCallback;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.select.SelectOrderActivity;
import com.wd.winddots.activity.select.SelectWareHouseActivity;
import com.wd.winddots.adapter.image.ImageBrowserAdapter;
import com.wd.winddots.adapter.stock.in.StockGoodsSpecAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.GoodsSpec;
import com.wd.winddots.entity.Order;
import com.wd.winddots.entity.StockInApply;
import com.wd.winddots.entity.WareHouse;
import com.wd.winddots.utils.BigDecimalUtil;
import com.wd.winddots.utils.CollectionUtil;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.StockUtil;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wd.winddots.activity.select.SelectOrderActivity.REQUEST_ADD_STOCK_IN_APPLY;

/**
 * 入库详情
 *
 * @author zhou
 */
public class StockInDetailActivity extends BaseActivity implements StockGoodsSpecAdapter.TextChangeListener {

    private static final int REQUEST_CODE_ORDER = 3;
    private static final int REQUEST_CODE_WARE_HOUSE = 1;
    private static final int REQUEST_CODE_SCAN = 7;

    private static final String SPACE_SEPARATOR = "   ";

    @BindView(R.id.tv_related_company)
    TextView mRelatedCompanyTv;

    @BindView(R.id.tv_order)
    TextView mOrderTv;

    @BindView(R.id.tv_remark)
    TextView mRemarkTv;

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

    @BindView(R.id.tv_place_holder)
    TextView mPlaceHolderTv;

    // 订单
    @BindView(R.id.ll_order_content)
    LinearLayout mOrderContentLl;

    @BindView(R.id.iv_select_order)
    ImageView mSelectOrderIv;

    @BindView(R.id.iv_delete_order)
    ImageView mDeleteOrderIv;

    @BindView(R.id.tv_order_related_company)
    TextView mOrderRelatedCompanyTv;

    @BindView(R.id.tv_order_goods_info)
    TextView mOrderGoodsInfoTv;

    @BindView(R.id.tv_order_no)
    TextView mOrderNoTv;

    @BindView(R.id.tv_order_theme)
    TextView mOrderThemeTv;

    @BindView(R.id.sdv_order_goods_photo)
    SimpleDraweeView mOrderGoodsPhotoSdv;

    @BindView(R.id.ll_order_spec)
    LinearLayout mOrderSpecLl;

    @BindView(R.id.iv_order_expand)
    ImageView mOrderExpandIv;

    @BindView(R.id.tv_create_user)
    TextView mCreateUserTv;

    @BindView(R.id.tv_auditor)
    TextView mAuditorTv;

    @BindView(R.id.tv_apply_num_header)
    TextView mApplyNumHeaderTv;

    @BindView(R.id.tv_confirm_num_header)
    TextView mConfirmNumHeaderTv;

    @BindView(R.id.tv_apply_num)
    TextView mApplyNumTv;

    @BindView(R.id.tv_confirm_num)
    TextView mConfirmNumTv;

    @BindView(R.id.ll_ware_house)
    LinearLayout mWareHouseLl;

    @BindView(R.id.tv_ware_house)
    TextView mWareHouseTv;

    ImageBrowserAdapter mImageBrowserAdapter;
    StockGoodsSpecAdapter mStockGoodsSpecAdapter;

    String mGoodsId;
    String mOrderId;
    String mRelatedCompanyId;
    String mWareHouseId;

    List<String> mImageList = new ArrayList<>();

    VolleyUtil mVolleyUtil;
    String mStockInApplyId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in_detail);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initData();
        initListener();
    }

    @OnClick({R.id.iv_back, R.id.rl_order, R.id.ll_goods_content,
            R.id.ll_order_content, R.id.ll_ware_house, R.id.tv_draft, R.id.tv_scan,
            R.id.tv_submit})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_order:
                // 订单
                if (TextUtils.isEmpty(mOrderId)) {
                    // 未选择订单
                    intent = new Intent(StockInDetailActivity.this, SelectOrderActivity.class);
                    intent.putExtra("request", REQUEST_ADD_STOCK_IN_APPLY);
                    if (!TextUtils.isEmpty(mGoodsId)) {
                        intent.putExtra("goodsId", mGoodsId);
                    }
                    startActivityForResult(intent, REQUEST_CODE_ORDER);
                } else {
                    // 选择订单
                    if (mOrderContentLl.getVisibility() == View.GONE) {
                        mOrderContentLl.setVisibility(View.VISIBLE);
                        mSelectOrderIv.setVisibility(View.GONE);
                        mDeleteOrderIv.setVisibility(View.VISIBLE);
                    } else {
                        mOrderContentLl.setVisibility(View.GONE);
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
                intent = new Intent(StockInDetailActivity.this, SelectWareHouseActivity.class);
                startActivityForResult(intent, REQUEST_CODE_WARE_HOUSE);
                break;
            case R.id.tv_draft:
                break;
            case R.id.tv_scan:
                startScanActivity();
                break;
            case R.id.tv_submit:
                break;
        }
    }

    private void startScanActivity() {
        Intent intent = new Intent(StockInDetailActivity.this, CaptureActivity2.class);
        intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }


    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mImageRv.setLayoutManager(gridLayoutManager);
        mImageBrowserAdapter = new ImageBrowserAdapter(this);
        mImageRv.setAdapter(mImageBrowserAdapter);

        LinearLayoutManager goodsSpecLinearLayoutManager = new LinearLayoutManager(this);
        mGoodsSpecRv.setLayoutManager(goodsSpecLinearLayoutManager);
        mGoodsSpecRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mStockGoodsSpecAdapter = new StockGoodsSpecAdapter(this);
        mGoodsSpecRv.setAdapter(mStockGoodsSpecAdapter);
    }

    private void initData() {

        mStockInApplyId = getIntent().getStringExtra("stockInApplyId");

        mImageList = new ArrayList<>();
        mImageBrowserAdapter.setList(mImageList);

        List<GoodsSpec> goodsSpecList = new ArrayList<>();
        mStockGoodsSpecAdapter.setList(goodsSpecList);

        getStockInApplyById(mStockInApplyId);
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
                case REQUEST_CODE_ORDER:
                    // 订单
                    if (null != data) {
                        Order order = (Order) data.getSerializableExtra("order");
                        renderOrderView(order);
                    }
                    break;
                case REQUEST_CODE_SCAN:
                    // 扫码
                    if (null != data) {
                        String content = data.getStringExtra("CaptureIsbn");
                        if (content.contains(Constant.QR_CODE_CONTENT_PREFIX_GOODS)) {
                            String goodsId = content.replaceAll(Constant.QR_CODE_CONTENT_PREFIX_GOODS, "");
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
     * @param stockInApply 入库申请
     */
    private void renderGoodsView(StockInApply stockInApply) {
        mGoodsContentLl.setVisibility(View.VISIBLE);

        String goodsInfo = stockInApply.getGoodsName() + "(" + stockInApply.getGoodsNo() + ")";

        String stockInfo;
        if (!TextUtils.isEmpty(stockInApply.getGoodsUnit())) {
            stockInfo = stockInApply.getApplyNumber() + stockInApply.getGoodsUnit();
            mApplyNumHeaderTv.setText("入库数量(" + stockInApply.getGoodsUnit() + ")");
            mConfirmNumHeaderTv.setText("确认数量(" + stockInApply.getGoodsUnit() + ")");
        } else {
            stockInfo = stockInApply.getApplyNumber();
            mApplyNumHeaderTv.setText("入库数量");
            mConfirmNumHeaderTv.setText("确认数量");
        }

        mGoodsInfoTv.setText(goodsInfo);
        mStockNumTv.setText(stockInfo);

        List<String> attrList = CommonUtil.attrJsonToAttrStrList(stockInApply.getAttrList());
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

        String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(stockInApply.getGoodsPhotos());
        if (!TextUtils.isEmpty(goodsPhoto)) {
            mGoodsPhotoSdv.setImageURI(Uri.parse(goodsPhoto));
        } else {
            mGoodsPhotoSdv.setImageResource(R.mipmap.icon_default_goods);
        }

        List<GoodsSpec> goodsSpecList = StockUtil.getGoodsSpecListFromStockRecordList(stockInApply.getStockApplicationInRecordList());
        mStockGoodsSpecAdapter.setList(goodsSpecList);

        BigDecimal applyNum = new BigDecimal(0);
        for (GoodsSpec goodsSpec : goodsSpecList) {
            applyNum = BigDecimalUtil.add(applyNum, goodsSpec.getApplyNum());
        }
        mApplyNumTv.setText(String.valueOf(applyNum.intValue()));

        if (TextUtils.isEmpty(stockInApply.getY())) {
            mGoodsSpecYTv.setVisibility(View.GONE);
            mPlaceHolderTv.setVisibility(View.GONE);
            mGoodsSpecXTv.setText(stockInApply.getX());
        } else {
            mGoodsSpecYTv.setVisibility(View.VISIBLE);
            mPlaceHolderTv.setVisibility(View.VISIBLE);
            mGoodsSpecXTv.setText(stockInApply.getX());
            mGoodsSpecYTv.setText(stockInApply.getY());
        }

    }

    private void renderOrderView(Order order) {
        mOrderId = order.getOrderId();
        mOrderTv.setText("#" + order.getOrderNo());
        mOrderTv.setTextColor(ContextCompat.getColor(this, R.color.color32));

        mOrderContentLl.setVisibility(View.VISIBLE);
        mSelectOrderIv.setVisibility(View.GONE);
        mDeleteOrderIv.setVisibility(View.VISIBLE);

        mOrderRelatedCompanyTv.setText(order.getRelatedCompanyShortName());

        String goodsInfo = order.getGoodsName() + "(" + order.getGoodsNo() + ")";
        mOrderGoodsInfoTv.setText(goodsInfo);
        mOrderNoTv.setText(order.getOrderNo());
        mOrderThemeTv.setText(order.getOrderTheme());
        String goodsPhoto = CommonUtil.getFirstPhotoFromJsonList(order.getGoodsPhotos());
        if (!TextUtils.isEmpty(goodsPhoto)) {
            mOrderGoodsPhotoSdv.setImageURI(Uri.parse(goodsPhoto));
        } else {
            mOrderGoodsPhotoSdv.setImageResource(R.mipmap.icon_default_goods);
        }

        // 同时渲染往来单位
        mRelatedCompanyId = order.getRelatedCompanyId();
        mRelatedCompanyTv.setText(order.getRelatedCompanyName());
        mRelatedCompanyTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
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

    private void getStockInApplyById(String applyId) {
        String url = Constant.APP_BASE_URL + "stockApplication/" + applyId;
        mVolleyUtil.httpGetRequest(url, response -> {
            StockInApply stockInApply;
            try {
                stockInApply = JSON.parseObject(response, StockInApply.class);
            } catch (Exception e) {
                stockInApply = null;
            }
            if (null != stockInApply) {
                renderGoodsView(stockInApply);
            }

            mRelatedCompanyTv.setText(stockInApply.getExchangeEnterpriseName());
            mRemarkTv.setText(stockInApply.getRemark());
            mCreateUserTv.setText(stockInApply.getCreateUserName() + SPACE_SEPARATOR + stockInApply.getCreateTime());
            mAuditorTv.setText(stockInApply.getAuditUserName());

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

        }, volleyError -> {
            mVolleyUtil.handleCommonErrorResponse(StockInDetailActivity.this, volleyError);
        });
    }

}
