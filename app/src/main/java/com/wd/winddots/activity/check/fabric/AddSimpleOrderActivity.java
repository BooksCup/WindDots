package com.wd.winddots.activity.check.fabric;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.select.SelectGoodsActivity;
import com.wd.winddots.activity.select.SelectRelatedCompanyActivity;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.VolleyUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增简单订单
 *
 * @author zhou
 */
public class AddSimpleOrderActivity extends BaseActivity {

    private static final int REQUEST_CODE_RELATED_COMPANY = 1;
    private static final int REQUEST_CODE_GOODS = 2;

    @BindView(R.id.tv_related_company)
    TextView mRelatedCompanyTv;

    @BindView(R.id.tv_goods_name)
    TextView mGoodsNameTv;

    @BindView(R.id.tv_delivery_date)
    TextView mDeliveryDateTv;

    @BindView(R.id.et_theme_title)
    EditText mThemeTitleEt;

    @BindView(R.id.et_num)
    EditText mNumEt;

    @BindView(R.id.et_remark)
    EditText mRemarkEt;


    VolleyUtil mVolleyUtil;
    String mRelatedCompanyId;
    String mGoodsId;
    String mDeliveryTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_simple_order);
        ButterKnife.bind(this);
        mVolleyUtil = VolleyUtil.getInstance(this);
    }

    @OnClick({R.id.iv_back, R.id.ll_related_company, R.id.ll_goods, R.id.ll_delivery_date, R.id.ll_save})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_related_company:
                // 往来单位
                intent = new Intent(AddSimpleOrderActivity.this, SelectRelatedCompanyActivity.class);
                startActivityForResult(intent, REQUEST_CODE_RELATED_COMPANY);
                break;
            case R.id.ll_goods:
                // 物品
                intent = new Intent(AddSimpleOrderActivity.this, SelectGoodsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_GOODS);
                break;
            case R.id.ll_delivery_date:
                // 交货日期
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(AddSimpleOrderActivity.this, (datePicker, i, i1, i2) -> {
                    int year1 = datePicker.getYear();
                    int month1 = datePicker.getMonth() + 1;
                    int day1 = datePicker.getDayOfMonth();

                    String monthS;
                    String dayS;
                    if (month1 < 10) {
                        monthS = "0" + month1;
                    } else {
                        monthS = "" + month1;
                    }

                    if (day1 < 10) {
                        dayS = "0" + day1;
                    } else {
                        dayS = "" + day1;
                    }
                    String date = year1 + "-" + monthS + '-' + dayS;
                    mDeliveryDateTv.setText(date);
                    mDeliveryDateTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
                    mDeliveryTime = date;
                }, year, month, day);

                dp.show();
                break;
            case R.id.ll_save:
                if (TextUtils.isEmpty(mRelatedCompanyId)) {
                    showToast("请选择往来单位");
                    return;
                }
                if (TextUtils.isEmpty(mGoodsId)) {
                    showToast("请选择物品");
                    return;
                }
                if (TextUtils.isEmpty(mDeliveryTime)) {
                    showToast("请选择交货日期");
                    return;
                }
                String themeTitle = mThemeTitleEt.getText().toString().trim();
                String num = mNumEt.getText().toString().trim();
                String remark = mRemarkEt.getText().toString().trim();

                addSimpleOrder(themeTitle, mDeliveryTime, num, remark);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
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
            }
        }
    }

    private void addSimpleOrder(String themeTitle, String deliveryTime, String num, String remark) {
        String url = Constant.APP_BASE_URL + "order";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("goodsId", mGoodsId);
        paramMap.put("userId", SpHelper.getInstance(this).getUserId());
        paramMap.put("enterpriseId", SpHelper.getInstance(this).getEnterpriseId());
        paramMap.put("relatedCompanyId", mRelatedCompanyId);
        paramMap.put("themeTitle", themeTitle);
        paramMap.put("deliveryTime", deliveryTime);
        paramMap.put("type", "P");
        paramMap.put("num", num);
        paramMap.put("remarks", remark);

        mVolleyUtil.httpPostRequest(url, paramMap, response -> {
            showToast(getString(R.string.add_order_success));
            hideLoadingDialog();
            finish();
        }, volleyError -> {
            hideLoadingDialog();
            mVolleyUtil.handleCommonErrorResponse(AddSimpleOrderActivity.this, volleyError);
        });
    }

}
