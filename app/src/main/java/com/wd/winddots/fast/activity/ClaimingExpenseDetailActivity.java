package com.wd.winddots.fast.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.fast.adapter.ClaimingExpenseDetailAdapter;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.fast.presenter.impl.ClaimingExpenseDetailPresenterImpl;
import com.wd.winddots.fast.presenter.view.ClaimingExpenseDetailView;
import com.wd.winddots.fast.view.ClaimingExpenseDetailHeader;
import com.wd.winddots.utils.OSSUploadHelper;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * FileName: ClaimingExpenseDetail
 * Author: 郑
 * Date: 2020/5/19 4:04 PM
 * Description: 费用明细
 */
public class ClaimingExpenseDetailActivity extends CommonActivity<ClaimingExpenseDetailView, ClaimingExpenseDetailPresenterImpl>
        implements ClaimingExpenseDetailView, ClaimingExpenseDetailAdapter.ClaimingExpenseDetailAdapterActionListener {

    private RecyclerView mRecyclerView;
    private ClaimingExpenseDetailAdapter mAdapter;
    private List<ApplyDetailBean.ClaimingDetail> mDataSource = new ArrayList<>();

    private ApplyDetailBean mClaimingDetailBean;

    private List<Uri> mPathList;
    private List<ApplyDetailBean.Invoice> invoiceList = new ArrayList<>();
    private BaseViewHolder mSelectHolder;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10086) {
                int itemPosition = mSelectHolder.getAdapterPosition() - 1;
                ApplyDetailBean.ClaimingDetail detail = mDataSource.get(itemPosition);
                RecyclerView recyclerView = mSelectHolder.getView(R.id.item_claiming_expense_rlist);
                detail.getInvoices().addAll(detail.getInvoices().size() - 1, invoiceList);
                int rowCount = ((detail.getInvoices().size() - 1) / 3) + 1;
                recyclerView.getLayoutParams().height = rowCount * getScreenWidth() / 3;
                recyclerView.getAdapter().notifyDataSetChanged();
                List<String> invoiceImageList = new ArrayList<>();
                for (int i = 0; i < detail.getInvoices().size() - 1; i++) {
                    ApplyDetailBean.Invoice invoice1 = detail.getInvoices().get(i);
                    if (!StringUtils.isNullOrEmpty(invoice1.getInvoiceImage())) {
                        invoiceImageList.add(invoice1.getInvoiceImage());
                    }
                }
                Map data = new HashMap();
                data.put("applyAddressId", detail.getId());
                data.put("invoices", invoiceImageList);
                Gson gson = new Gson();
                String bodyString = gson.toJson(data);
                Log.e("net666",bodyString);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
                presenter.updateApplyInvoiceImage(mClaimingDetailBean.getId(), requestBody);
            } else if (msg.what == 10087) {
                hideLoading();
                showToast("上传图片失败,请稍后重试");
                return;
            }
        }
    };


    @Override
    public ClaimingExpenseDetailPresenterImpl initPresenter() {
        return new ClaimingExpenseDetailPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();

        addBadyView(R.layout.activity_claiming_expense_detail);

        Intent intent = getIntent();
        String s = intent.getStringExtra("data");
        String invoiceS = intent.getStringExtra("invoice");
        if (StringUtils.isNullOrEmpty(s)) {
            return;
        }

        Gson gson = new Gson();
        ApplyDetailBean.ClaimingModel item = gson.fromJson(s, ApplyDetailBean.ClaimingModel.class);
        mClaimingDetailBean = gson.fromJson(invoiceS, ApplyDetailBean.class);


        setTitleText(item.getCostName());
        mDataSource.addAll(item.getAddresses());

        String userId = SpHelper.getInstance(mContext).getUserId();
        String createdBy = mClaimingDetailBean.getCreatedBy();

        mRecyclerView = findViewById(R.id.activity_claiming_expense_detail_rlist);
        mAdapter = new ClaimingExpenseDetailAdapter(R.layout.item_claiming_expense_detail, mDataSource);
        mAdapter.setClaimingDetailBean(mClaimingDetailBean);
        mAdapter.setmItemS(getScreenWidth() / 3);
        mAdapter.setUserId(userId);
        mAdapter.setClaimingExpenseDetailAdapterActionListener(this);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        ClaimingExpenseDetailHeader header = new ClaimingExpenseDetailHeader(mContext);
        mAdapter.setHeaderView(header);
        header.setRemark(item.getRemark());
        header.setDepartment(Utils.nullOrEmpty(item.getDeptName()));
        header.setAmount(item.getAmount() + mClaimingDetailBean.getCurrency());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 1000) {
            List<MediaEntity> result = Phoenix.result(data);
            final List<Uri> pathList = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                MediaEntity mediaEntity = result.get(i);
                pathList.add(Utils.path2uri(mContext, mediaEntity.getFinalPath()));
            }

            mPathList = pathList;
            showLoading();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < pathList.size(); i++) {
                        Uri uri = pathList.get(i);
//                RequestBody body = Utils.uri2requestBody(mContext, uri);
//                presenter.uploadImage(body, i);
                        String imagePath = Utils.uri2File(mContext, uri).getPath();
                        String imageUrl = OSSUploadHelper.uploadImage(imagePath);
                        if (imageUrl != null) {
                            ApplyDetailBean.Invoice invoice = new ApplyDetailBean.Invoice();
                            invoice.setInvoiceImage(imageUrl);
                            invoiceList.add(invoice);
                        } else {
                            Message msg = new Message();
                            msg.what = 10087;
                            msg.arg1 = i;
                            mhandler.sendMessage(msg);
                            return;
                        }
                    }
                    Message msg = new Message();
                    msg.what = 10086;
                    mhandler.sendMessage(msg);
                }
            }).start();

        }
    }

    /*
     * 更改开票状态
     * */
    @Override
    public void updateApplyInvoiceType(ApplyDetailBean.ClaimingDetail item, String invoice) {
        presenter.updateApplyInvoiceType(mClaimingDetailBean.getId(), item.getId(), invoice);
    }

    @Override
    public void updateApplyInvoiceTypeSuccess() {
        //showToast("更改成功");
    }

    @Override
    public void updateApplyInvoiceTypeError() {
    }

    @Override
    public void updateApplyInvoiceTypeCompleted() {
    }


    /*
     * 更改发票照片
     * */
    @Override
    public void updateApplyInvoiceImage(BaseViewHolder holder) {

        int position = holder.getAdapterPosition() - 1;
        List<String> invoiceImageList = new ArrayList<>();
        ApplyDetailBean.ClaimingDetail item = mDataSource.get(position);
        for (int i = 0; i < item.getInvoices().size() - 1; i++) {
            ApplyDetailBean.Invoice invoice = item.getInvoices().get(i);
            if (!StringUtils.isNullOrEmpty(invoice.getInvoiceImage())) {
                invoiceImageList.add(invoice.getInvoiceImage());
            }
        }

        Map data = new HashMap();
        data.put("applyAddressId", item.getId());
        data.put("invoices", invoiceImageList);
        Gson gson = new Gson();
        String bodyString = gson.toJson(data);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);

        showLoading();

        presenter.updateApplyInvoiceImage(mClaimingDetailBean.getId(), requestBody);

    }

    @Override
    public void addApplyInvoiceImage(BaseViewHolder holder) {
        mSelectHolder = holder;
        invoiceList.clear();
        Phoenix.with()
                .theme(PhoenixOption.THEME_DEFAULT)// 主题
                .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(9)// 最大选择数量
                .minPickNumber(0)// 最小选择数量
                .spanCount(4)// 每行显示个数
                .enablePreview(true)// 是否开启预览
                .enableCamera(true)// 是否开启拍照
                .enableAnimation(true)// 选择界面图片点击效果
                .enableCompress(true)// 是否开启压缩
                .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                .compressVideoFilterSize(2018)//多少kb以下的视频不压缩
                .thumbnailHeight(160)// 选择界面图片高度
                .thumbnailWidth(160)// 选择界面图片宽度
                .enableClickSound(false)// 是否开启点击声音
                .videoFilterTime(0)//显示多少秒以内的视频
                .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
                .start(ClaimingExpenseDetailActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 1000);
    }

    @Override
    public void updateApplyInvoiceImageSuccess() {
        hideLoading();
        try {
            Thread.sleep(1000);
            showToast("修改发票成功");
        } catch (Exception e) {
        }
    }

    @Override
    public void updateApplyInvoiceImageError() {
        hideLoading();
    }

    @Override
    public void updateApplyInvoiceImageCompleted() {
        hideLoading();
    }


    @Override
    public void uploadImageSuccess(UploadImageBean bean, int position) {
        ApplyDetailBean.Invoice invoice = new ApplyDetailBean.Invoice();
        invoice.setInvoiceImage("http:" + bean.getList().get(0));
        invoiceList.add(invoice);
        //Log.e("22222222222", String.valueOf(mSelectItem.getInvoices().size()) + "++++" + String.valueOf(position));
        if (invoiceList.size() == mPathList.size()) {

            int itemPosition = mSelectHolder.getAdapterPosition() - 1;
            ApplyDetailBean.ClaimingDetail detail = mDataSource.get(itemPosition);
            RecyclerView recyclerView = mSelectHolder.getView(R.id.item_claiming_expense_rlist);
            detail.getInvoices().addAll(detail.getInvoices().size() - 1, invoiceList);
            int rowCount = ((detail.getInvoices().size() - 1) / 3) + 1;
            recyclerView.getLayoutParams().height = rowCount * getScreenWidth() / 3;
            recyclerView.getAdapter().notifyDataSetChanged();

            List<String> invoiceImageList = new ArrayList<>();

            for (int i = 0; i < detail.getInvoices().size() - 1; i++) {
                ApplyDetailBean.Invoice invoice1 = detail.getInvoices().get(i);
                if (!StringUtils.isNullOrEmpty(invoice1.getInvoiceImage())) {
                    invoiceImageList.add(invoice1.getInvoiceImage());
                }
            }

            Map data = new HashMap();
            data.put("applyAddressId", detail.getId());
            data.put("invoices", invoiceImageList);
            Gson gson = new Gson();
            String bodyString = gson.toJson(data);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);

            presenter.updateApplyInvoiceImage(mClaimingDetailBean.getId(), requestBody);
        }
    }

    @Override
    public void uploadImageError() {
        hideLoading();
    }

    @Override
    public void uploadImageCompleted() {
        hideLoading();
    }


}
