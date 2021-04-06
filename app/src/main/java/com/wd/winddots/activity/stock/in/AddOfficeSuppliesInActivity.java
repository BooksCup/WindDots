package com.wd.winddots.activity.stock.in;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.client.android.CaptureActivity2;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.select.SelectGoodsActivity;
import com.wd.winddots.activity.select.SelectSingleUserActivity;
import com.wd.winddots.activity.work.GlideEngine;
import com.wd.winddots.adapter.check.fabric.FabricCheckProblemAdapter;
import com.wd.winddots.adapter.image.ImagePickerAdapter;
import com.wd.winddots.adapter.stock.in.StockApplyGoodsSpecAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Goods;
import com.wd.winddots.entity.GoodsSpec;
import com.wd.winddots.entity.ImageEntity;
import com.wd.winddots.entity.SystemRoleConfig;
import com.wd.winddots.entity.User;
import com.wd.winddots.enums.ModuleEnum;
import com.wd.winddots.enums.StockApplyStatusEnum;
import com.wd.winddots.enums.StockBizTypeEnum;
import com.wd.winddots.utils.CollectionUtil;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.OSSUploadHelper;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.dialog.ConfirmDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * 提交入库单(办公用品)
 *
 * @author zhou
 */
public class AddOfficeSuppliesInActivity extends BaseActivity implements StockApplyGoodsSpecAdapter.TextChangeListener {

    private static final int REQUEST_CODE_GOODS = 2;
    private static final int REQUEST_CODE_AUDITOR = 4;
    private static final int REQUEST_CODE_COPY = 5;
    private static final int REQUEST_CODE_IMAGE_PICKER = 6;
    private static final int REQUEST_CODE_SCAN = 7;


    @BindView(R.id.tv_goods_name)
    TextView mGoodsNameTv;

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

    @BindView(R.id.tv_total_num)
    TextView mTotalNumTv;

    @BindView(R.id.ll_operate)
    LinearLayout mOperateLl;

    ImagePickerAdapter mImagePickerAdapter;
    StockApplyGoodsSpecAdapter mStockApplyGoodsSpecAdapter;



    String mGoodsId;
    // 审核人用户ID
    String mAuditorId;
    // 抄送人
    String mCopyId;

    List<ImageEntity> mImageEntityList = new ArrayList<>();

    VolleyUtil mVolleyUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_office_supplies_in_apply);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
        initView();
        initData();
        initListener();
    }

    @OnClick({R.id.iv_back, R.id.rl_goods, R.id.ll_auditor, R.id.ll_copy,
            R.id.ll_goods_content, R.id.iv_delete_goods, R.id.tv_draft, R.id.tv_scan,
            R.id.tv_submit})
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
                    intent = new Intent(AddOfficeSuppliesInActivity.this, SelectGoodsActivity.class);
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
            case R.id.ll_auditor:
                // 审核人
                intent = new Intent(AddOfficeSuppliesInActivity.this, SelectSingleUserActivity.class);
                startActivityForResult(intent, REQUEST_CODE_AUDITOR);
                break;
            case R.id.ll_copy:
                // 抄送人
                intent = new Intent(AddOfficeSuppliesInActivity.this, SelectSingleUserActivity.class);
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
                        mGoodsNameTv.setTextColor(ContextCompat.getColor(AddOfficeSuppliesInActivity.this, R.color.colorB2));

                        // 重置
                        mGoodsSpecRv.setAdapter(null);
                        mGoodsSpecRv.setAdapter(mStockApplyGoodsSpecAdapter);
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

            case R.id.tv_draft:

                if (TextUtils.isEmpty(mGoodsId)) {
                    showToast("请选择入库物品");
                    return;
                }

                addStockInApply(StockApplyStatusEnum.STOCK_APPLY_STATUS_DRAFT.getStatus());
                break;
            case R.id.tv_scan:
                startScanActivity();
                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(mGoodsId)) {
                    showToast("请选择入库物品");
                    return;
                }

                if (TextUtils.isEmpty(mAuditorId)) {
                    showToast("请选择审核人");
                    return;
                }

                if (TextUtils.isEmpty(mCopyId)) {
                    showToast("请选择抄送人");
                    return;
                }
                mConfirmDialog = new ConfirmDialog(this, "确认提交",
                        "是否确认提交入库单？",
                        "是", "否");
                mConfirmDialog.setOnDialogClickListener(new ConfirmDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        addStockInApply(StockApplyStatusEnum.STOCK_APPLY_STATUS_UNCONFIRMED.getStatus());
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
        Intent intent = new Intent(AddOfficeSuppliesInActivity.this, CaptureActivity2.class);
        intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }


    private void initView() {
        mOperateLl.setVisibility(View.VISIBLE);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mImageRv.setLayoutManager(gridLayoutManager);
        mImagePickerAdapter = new ImagePickerAdapter(this);
        mImageRv.setAdapter(mImagePickerAdapter);

        LinearLayoutManager goodsSpecLinearLayoutManager = new LinearLayoutManager(this);
        mGoodsSpecRv.setLayoutManager(goodsSpecLinearLayoutManager);
        mGoodsSpecRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mStockApplyGoodsSpecAdapter = new StockApplyGoodsSpecAdapter(this);
        mGoodsSpecRv.setAdapter(mStockApplyGoodsSpecAdapter);

        getSystemRoleConfig();
    }

    private void initData() {
        mImageEntityList = new ArrayList<>();
        ImageEntity imageEntity = new ImageEntity();
        mImageEntityList.add(imageEntity);
        mImagePickerAdapter.setList(mImageEntityList);

        List<GoodsSpec> goodsSpecList = new ArrayList<>();
        mStockApplyGoodsSpecAdapter.setList(goodsSpecList);
    }

    private void initListener() {
        mImagePickerAdapter.setOnRecyclerItemClickListener(position -> {
            List<ImageEntity> imageEntityList = mImagePickerAdapter.getList();
            if (null == imageEntityList) {
                imageEntityList = new ArrayList<>();
            }
            if (position == imageEntityList.size() - 1) {
                try {
                    EasyPhotos.createAlbum(AddOfficeSuppliesInActivity.this, true, GlideEngine.getInstance())
                            .setFileProviderAuthority("com.wd.winddots.fileprovider")
                            .start(REQUEST_CODE_IMAGE_PICKER);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mStockApplyGoodsSpecAdapter.setTextChangeListener(this);
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
                        List<ImageEntity> picBeans = new ArrayList<>();
                        for (int i = 0; i < resultPhotos.size(); i++) {
                            ImageEntity imageEntity = new ImageEntity();
                            imageEntity.setPath(resultPhotos.get(i).path);
                            imageEntity.setId(i + "");
                            mImageEntityList.add(0, imageEntity);
                            picBeans.add(imageEntity);
                        }

                        mImagePickerAdapter.setList(mImageEntityList);
                        //picsAdapter.setList(picBeans);

                        showLoadingDialog();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int t = 0; t < picBeans.size(); t++) {
                                    ImageEntity imageEntity = picBeans.get(t);
                                    if (!StringUtils.isNullOrEmpty(imageEntity.getPath())) {
                                        String imagePath = imageEntity.getPath();//Utils.uri2File(this, imageEntity.getUrl());
                                        String imageUrl = OSSUploadHelper.uploadImage(imagePath);
                                        if (imageUrl != null) {
                                            Log.e("net666", imageUrl);
                                            imageEntity.setUrl(imageUrl);
                                        } else {
                                            Message msg = new Message();
                                            msg.what = 10087;
                                            mhandler.sendMessage(msg);
                                            return;
                                        }
                                    }
                                }
                                Message msg = new Message();
                                msg.what = 10086;
                                mhandler.sendMessage(msg);
                            }
                        }).start();
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
        mStockApplyGoodsSpecAdapter.setList(goodsSpecList);

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

    @Override
    public void stockInNumChange() {
        List<GoodsSpec> goodsSpecList = mStockApplyGoodsSpecAdapter.getList();
        float total = 0;
        NumberFormat nf = NumberFormat.getNumberInstance();
        for (GoodsSpec goodsSpec : goodsSpecList) {
            nf.setMaximumFractionDigits(2);
            float totalStockInNum = Float.parseFloat(Utils.numberNullOrEmpty(goodsSpec.getApplyNum()));
            total = totalStockInNum + total;
        }
        mTotalNumTv.setText(nf.format(total));
    }

    private void addStockInApply(String applyStatus) {

        showLoadingDialog();
        List<GoodsSpec> goodsSpecList = mStockApplyGoodsSpecAdapter.getList();
        String specNums = JSON.toJSONString(goodsSpecList);
        String remark = mRemarkEt.getText().toString().trim();

        String url = Constant.APP_BASE_URL + "stockApply";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("goodsId", mGoodsId);
        paramMap.put("createUserId", SpHelper.getInstance(this).getUserId());
        paramMap.put("enterpriseId", SpHelper.getInstance(this).getEnterpriseId());
        paramMap.put("specNums", specNums);
        paramMap.put("stockType", Constant.STOCK_TYPE_IN);
        paramMap.put("bizType", StockBizTypeEnum.STOCK_BIZ_TYPE_OFFICE_SUPPLIES_IN.getType());
        paramMap.put("remark", remark);
        paramMap.put("applyStatus", applyStatus);
//        paramMap.put("images", "[\"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/329b0751292445df8500aa98a1180936.png\"]");
        paramMap.put("images", JSON.toJSONString(mImageEntityList));
        paramMap.put("auditorId", mAuditorId == null ? "" : mAuditorId);
        paramMap.put("copyId", mCopyId == null ? "" : mCopyId);

        mVolleyUtil.httpPostRequest(url, paramMap, response -> {
            if (applyStatus.equals(StockApplyStatusEnum.STOCK_APPLY_STATUS_DRAFT.getStatus())) {
                // 保存至草稿
                showToast("成功保存至草稿");
            } else {
                // 提交入库单
                showToast("入库单提交成功");
            }
            hideLoadingDialog();
            finish();
        }, volleyError -> {
            hideLoadingDialog();
            mVolleyUtil.handleCommonErrorResponse(AddOfficeSuppliesInActivity.this, volleyError);
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
            mVolleyUtil.handleCommonErrorResponse(AddOfficeSuppliesInActivity.this, volleyError);
        });
    }

    /**
     * 获取系统角色配置
     */
    private void getSystemRoleConfig() {
        showLoadingDialog();
        String url = Constant.APP_BASE_URL
                + "systemRoleConfig?enterpriseId=" + SpHelper.getInstance(this).getEnterpriseId()
                + "&module=" + ModuleEnum.STOCK_IN.getCode();
        mVolleyUtil.httpGetRequest(url, response -> {
            hideLoadingDialog();
            SystemRoleConfig systemRoleConfig;
            try {
                systemRoleConfig = JSON.parseObject(response, SystemRoleConfig.class);
            } catch (Exception e) {
                systemRoleConfig = null;
            }
            if (null != systemRoleConfig) {
                List<User> auditorList = systemRoleConfig.getAuditorList();
                List<User> copyList = systemRoleConfig.getCopyList();
                if (!CollectionUtil.isEmpty(auditorList)) {
                    User auditor = auditorList.get(0);
                    mAuditorId = auditor.getId();
                    mAuditorTv.setText(auditor.getName());
                    mAuditorTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
                }

                if (!CollectionUtil.isEmpty(copyList)) {
                    User copy = copyList.get(0);
                    mCopyId = copy.getId();
                    mCopyTv.setText(copy.getName());
                    mCopyTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
                }

            }
        }, volleyError -> {
            hideLoadingDialog();
        });
    }





    // 监听上传是否成功
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10086) {
                hideLoadingDialog();
            } else if (msg.what == 10087) {
                hideLoadingDialog();
                showToast("上传图片失败,请稍后重试");
                return;
            }
        }
    };

}
