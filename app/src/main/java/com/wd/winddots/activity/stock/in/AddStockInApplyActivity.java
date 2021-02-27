package com.wd.winddots.activity.stock.in;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.select.SelectGoodsActivity;
import com.wd.winddots.activity.select.SelectOrderActivity;
import com.wd.winddots.activity.select.SelectRelatedCompanyActivity;
import com.wd.winddots.activity.select.SelectSingleUserActivity;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

    @BindView(R.id.tv_goods_name)
    TextView mGoodsNameTv;

    @BindView(R.id.tv_related_company)
    TextView mRelatedCompanyTv;

    @BindView(R.id.tv_order)
    TextView mOrderTv;

    String mGoodsId;
    String mOrderId;
    String mRelatedCompanyId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_in_apply);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.ll_goods, R.id.ll_order, R.id.ll_related_company, R.id.ll_auditor})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_goods:
                // 物品
                intent = new Intent(AddStockInApplyActivity.this, SelectGoodsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_GOODS);
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
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GOODS:
                    // 物品
                    if (null != data) {
                        String goodsId = data.getStringExtra("goodsId");
                        String goodsName = data.getStringExtra("goodsName");
                        mGoodsId = goodsId;
                        mGoodsNameTv.setText(goodsName);
                        mGoodsNameTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
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
            }
        }
    }

}
