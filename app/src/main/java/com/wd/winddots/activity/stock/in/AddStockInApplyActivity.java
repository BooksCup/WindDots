package com.wd.winddots.activity.stock.in;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.select.SelectGoodsActivity;
import com.wd.winddots.activity.select.SelectOrderActivity;
import com.wd.winddots.activity.select.SelectRelatedCompanyActivity;
import com.wd.winddots.activity.select.SelectSingleUserActivity;
import com.wd.winddots.activity.work.GlideEngine;
import com.wd.winddots.adapter.image.ImagePickerAdapter;
import com.wd.winddots.adapter.stock.in.GoodsSpecAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Goods;
import com.wd.winddots.entity.GoodsSpec;
import com.wd.winddots.entity.ImageEntity;
import com.wd.winddots.entity.Order;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.dialog.ConfirmDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 提交入库申请(入库单)
 *
 * @author zhou
 */
public class AddStockInApplyActivity extends BaseActivity implements GoodsSpecAdapter.TextChangeListener {

    private static final int REQUEST_CODE_RELATED_COMPANY = 1;
    private static final int REQUEST_CODE_GOODS = 2;
    private static final int REQUEST_CODE_ORDER = 3;
    private static final int REQUEST_CODE_AUDITOR = 4;
    private static final int REQUEST_CODE_COPY = 5;
    private static final int REQUEST_CODE_IMAGE_PICKER = 6;

    @BindView(R.id.tv_goods_name)
    TextView mGoodsNameTv;

    @BindView(R.id.tv_related_company)
    TextView mRelatedCompanyTv;

    @BindView(R.id.tv_order)
    TextView mOrderTv;

    @BindView(R.id.et_remark)
    EditText mRemarkEt;

    @BindView(R.id.tv_auditor)
    TextView mAuditorTv;

    @BindView(R.id.tv_copy)
    TextView mCopyTv;

    @BindView(R.id.rv_image)
    RecyclerView mImageRv;

    // 物品
    @BindView(R.id.ll_goods_content)
    LinearLayout mGoodsContentLl;

    @BindView(R.id.iv_select_goods)
    ImageView mSelectGoodsIv;

    @BindView(R.id.iv_delete_goods)
    ImageView mDeleteGoodsIv;

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

    @BindView(R.id.tv_total_num)
    TextView mTotalNumTv;

    ImagePickerAdapter mImagePickerAdapter;
    GoodsSpecAdapter mGoodsSpecAdapter;

    String mGoodsId;
    String mOrderId;
    String mRelatedCompanyId;
    // 审核人用户ID
    String mAuditorId;
    // 抄送人
    String mCopyId;

    List<ImageEntity> mImageEntityList = new ArrayList<>();

    VolleyUtil mVolleyUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_in_apply);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initData();
        initListener();
    }

    @OnClick({R.id.iv_back, R.id.rl_goods, R.id.rl_order, R.id.ll_related_company,
            R.id.ll_auditor, R.id.ll_copy, R.id.ll_goods_content, R.id.iv_delete_goods,
            R.id.ll_order_content, R.id.iv_delete_order, R.id.ll_save})
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
                    intent = new Intent(AddStockInApplyActivity.this, SelectGoodsActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_GOODS);
                } else {
                    // 选择物品
                    if (mGoodsContentLl.getVisibility() == View.GONE) {
                        mGoodsContentLl.setVisibility(View.VISIBLE);
                        mSelectGoodsIv.setVisibility(View.GONE);
                        mDeleteGoodsIv.setVisibility(View.VISIBLE);
                    } else {
                        mGoodsContentLl.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.rl_order:
                // 订单
                if (TextUtils.isEmpty(mOrderId)) {
                    // 未选择订单
                    intent = new Intent(AddStockInApplyActivity.this, SelectOrderActivity.class);
                    intent.putExtra("request", REQUEST_ADD_STOCK_IN_APPLY);
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
            case R.id.ll_related_company:
                // 往来单位
                intent = new Intent(AddStockInApplyActivity.this, SelectRelatedCompanyActivity.class);
                startActivityForResult(intent, REQUEST_CODE_RELATED_COMPANY);
                break;
            case R.id.ll_auditor:
                // 审核人
                intent = new Intent(AddStockInApplyActivity.this, SelectSingleUserActivity.class);
                startActivityForResult(intent, REQUEST_CODE_AUDITOR);
                break;
            case R.id.ll_copy:
                // 抄送人
                intent = new Intent(AddStockInApplyActivity.this, SelectSingleUserActivity.class);
                startActivityForResult(intent, REQUEST_CODE_COPY);
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
            case R.id.iv_delete_goods:
                mConfirmDialog = new ConfirmDialog(this, "确认取消",
                        "是否取消已选物品",
                        "是", "否");
                mConfirmDialog.setOnDialogClickListener(new ConfirmDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        mSelectGoodsIv.setVisibility(View.VISIBLE);
                        mDeleteGoodsIv.setVisibility(View.GONE);
                        mGoodsContentLl.setVisibility(View.GONE);
                        mGoodsId = "";
                        mGoodsNameTv.setText("请选择入库物品");
                        mGoodsNameTv.setTextColor(ContextCompat.getColor(AddStockInApplyActivity.this, R.color.colorB2));

                        // 重置
                        mGoodsSpecRv.setAdapter(null);
                        mGoodsSpecRv.setAdapter(mGoodsSpecAdapter);
                        mTotalNumTv.setText("0");
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
            case R.id.iv_delete_order:
                mConfirmDialog = new ConfirmDialog(this, "确认取消",
                        "是否取消已选订单",
                        "是", "否");
                mConfirmDialog.setOnDialogClickListener(new ConfirmDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        mSelectOrderIv.setVisibility(View.VISIBLE);
                        mDeleteOrderIv.setVisibility(View.GONE);
                        mOrderContentLl.setVisibility(View.GONE);
                        mOrderId = "";
                        mOrderTv.setText("请选择相关订单");
                        mOrderTv.setTextColor(ContextCompat.getColor(AddStockInApplyActivity.this, R.color.colorB2));
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
            case R.id.ll_save:
                if (TextUtils.isEmpty(mGoodsId)) {
                    showToast("请选择入库物品");
                    return;
                }

                if (TextUtils.isEmpty(mOrderId)) {
                    showToast("请选择相关订单");
                    return;
                }

                if (TextUtils.isEmpty(mRelatedCompanyId)) {
                    showToast("请选择往来单位");
                }

                if (TextUtils.isEmpty(mAuditorId)) {
                    showToast("请选择审核人");
                }

                if (TextUtils.isEmpty(mCopyId)) {
                    showToast("请选择抄送人");
                }

                List<GoodsSpec> goodsSpecList = mGoodsSpecAdapter.getList();
                String specNums = JSON.toJSONString(goodsSpecList);
                String remark = mRemarkEt.getText().toString().trim();
                addStockInApply(specNums, remark);
                break;
        }
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mImageRv.setLayoutManager(gridLayoutManager);
        mImagePickerAdapter = new ImagePickerAdapter(this);
        mImageRv.setAdapter(mImagePickerAdapter);

        LinearLayoutManager goodsSpecLinearLayoutManager = new LinearLayoutManager(this);
        mGoodsSpecRv.setLayoutManager(goodsSpecLinearLayoutManager);
        mGoodsSpecRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mGoodsSpecAdapter = new GoodsSpecAdapter(this);
        mGoodsSpecRv.setAdapter(mGoodsSpecAdapter);
    }

    private void initData() {
        mImageEntityList = new ArrayList<>();
        ImageEntity imageEntity = new ImageEntity();
        mImageEntityList.add(imageEntity);
        mImagePickerAdapter.setList(mImageEntityList);

        List<GoodsSpec> goodsSpecList = new ArrayList<>();
        mGoodsSpecAdapter.setList(goodsSpecList);
    }

    private void initListener() {
        mImagePickerAdapter.setOnRecyclerItemClickListener(position -> {
            List<ImageEntity> imageEntityList = mImagePickerAdapter.getList();
            if (null == imageEntityList) {
                imageEntityList = new ArrayList<>();
            }
            if (position == imageEntityList.size() - 1) {
                try {
                    EasyPhotos.createAlbum(AddStockInApplyActivity.this, true, GlideEngine.getInstance())
                            .setFileProviderAuthority("com.wd.winddots.fileprovider")
                            .start(REQUEST_CODE_IMAGE_PICKER);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mGoodsSpecAdapter.setTextChangeListener(this);
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
                case REQUEST_CODE_ORDER:
                    // 订单
                    if (null != data) {
                        Order order = (Order) data.getSerializableExtra("order");
                        renderOrderView(order);
                    }
                    break;
                case REQUEST_CODE_RELATED_COMPANY:
                    // 往来单位
                    if (null != data) {
                        String relatedCompanyId = data.getStringExtra("relatedCompanyId");
                        String relatedCompanyName = data.getStringExtra("relatedCompanyName");
                        mRelatedCompanyId = relatedCompanyId;
                        mRelatedCompanyTv.setText(relatedCompanyName);
                        mRelatedCompanyTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
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
                case REQUEST_CODE_COPY:
                    // 抄送人
                    if (null != data) {
                        String userId = data.getStringExtra("userId");
                        String userName = data.getStringExtra("userName");
                        mCopyId = userId;
                        mCopyTv.setText(userName);
                        mCopyTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
                    }
                    break;
                case REQUEST_CODE_IMAGE_PICKER:
                    // 图片选择
                    ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                    if (null != resultPhotos) {
                        for (int i = 0; i < resultPhotos.size(); i++) {
                            ImageEntity imageEntity = new ImageEntity();
                            imageEntity.setUrl(resultPhotos.get(i).path);
                            imageEntity.setId(String.valueOf(i));
                            mImageEntityList.add(0, imageEntity);
                        }
                        mImagePickerAdapter.setList(mImageEntityList);
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
        mGoodsNameTv.setText(goods.getGoodsName());
        mGoodsNameTv.setTextColor(ContextCompat.getColor(this, R.color.color32));

        mGoodsContentLl.setVisibility(View.VISIBLE);
        mSelectGoodsIv.setVisibility(View.GONE);
        mDeleteGoodsIv.setVisibility(View.VISIBLE);

        String goodsInfo = goods.getGoodsName() + "(" + goods.getGoodsNo() + ")";
        String stockInfo = goods.getStockNum() + goods.getGoodsUnit();
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
        mGoodsSpecAdapter.setList(goodsSpecList);

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
    }

    @Override
    public void stockInNumChange() {
        List<GoodsSpec> goodsSpecList = mGoodsSpecAdapter.getList();
        float total = 0;
        NumberFormat nf = NumberFormat.getNumberInstance();
        for (GoodsSpec goodsSpec : goodsSpecList) {
            nf.setMaximumFractionDigits(2);
            float totalStockInNum = Float.parseFloat(Utils.numberNullOrEmpty(goodsSpec.getNum()));
            total = totalStockInNum + total;
        }
        mTotalNumTv.setText(nf.format(total));
    }

    private void addStockInApply(String specNums, String remark) {
        String url = Constant.APP_BASE_URL + "stockInApply";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("goodsId", mGoodsId);
        paramMap.put("createUserId", SpHelper.getInstance(this).getUserId());
        paramMap.put("enterpriseId", SpHelper.getInstance(this).getEnterpriseId());
        paramMap.put("specNums", specNums);
        paramMap.put("orderId", mOrderId);
        paramMap.put("stockType", Constant.STOCK_TYPE_IN);
        paramMap.put("bizType", Constant.STOCK_BIZ_TYPE_PURCHASE_IN);
        paramMap.put("relatedCompanyId", mRelatedCompanyId);
        paramMap.put("remark", remark);
        paramMap.put("images", "[\"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/329b0751292445df8500aa98a1180936.png\"]");
        paramMap.put("auditorId", mAuditorId);
        paramMap.put("copyId", mCopyId);

        mVolleyUtil.httpPostRequest(url, paramMap, response -> {
            showToast(getString(R.string.add_order_success));
            hideLoadingDialog();
            finish();
        }, volleyError -> {
            hideLoadingDialog();
            mVolleyUtil.handleCommonErrorResponse(AddStockInApplyActivity.this, volleyError);
        });
    }
}
