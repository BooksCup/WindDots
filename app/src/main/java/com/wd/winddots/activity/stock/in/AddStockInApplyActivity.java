package com.wd.winddots.activity.stock.in;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.wd.winddots.activity.work.GoodsInfoAdapter;
import com.wd.winddots.adapter.image.ImagePickerAdapter;
import com.wd.winddots.adapter.stock.in.GoodsSpecAdapter;
import com.wd.winddots.entity.Goods;
import com.wd.winddots.entity.GoodsSpec;
import com.wd.winddots.entity.ImageEntity;
import com.wd.winddots.utils.CommonUtil;

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
 * 提交入库申请(入库单)
 *
 * @author zhou
 */
public class AddStockInApplyActivity extends BaseActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_in_apply);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    @OnClick({R.id.iv_back, R.id.rl_goods, R.id.ll_order, R.id.ll_related_company,
            R.id.ll_auditor, R.id.ll_copy, R.id.ll_goods_content, R.id.iv_delete_goods})
    public void onClick(View v) {
        Intent intent;
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
            case R.id.ll_order:
                // 订单
                intent = new Intent(AddStockInApplyActivity.this, SelectOrderActivity.class);
                intent.putExtra("request", REQUEST_ADD_STOCK_IN_APPLY);
                startActivityForResult(intent, REQUEST_CODE_ORDER);
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
                mSelectGoodsIv.setVisibility(View.VISIBLE);
                mDeleteGoodsIv.setVisibility(View.GONE);
                mGoodsContentLl.setVisibility(View.GONE);
                mGoodsId = "";
                mGoodsNameTv.setText("请选择入库物品");
                mGoodsNameTv.setTextColor(ContextCompat.getColor(this, R.color.colorB2));
                break;
        }
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mImageRv.setLayoutManager(gridLayoutManager);
        mImagePickerAdapter = new ImagePickerAdapter(this);
        mImageRv.setAdapter(mImagePickerAdapter);

        LinearLayoutManager goodsSpecLinearLayoutManager = new  LinearLayoutManager(this);
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
        goodsSpecList.add(new GoodsSpec());
        goodsSpecList.add(new GoodsSpec());
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
                        String orderId = data.getStringExtra("orderId");
                        String orderNo = data.getStringExtra("orderNo");
                        mOrderId = orderId;
                        mOrderTv.setText("#" + orderNo);
                        mOrderTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
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
    }

}
