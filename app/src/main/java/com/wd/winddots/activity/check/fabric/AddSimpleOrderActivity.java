package com.wd.winddots.activity.check.fabric;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.activity.base.BaseActivity;
import com.wd.winddots.activity.select.SelectGoodsActivity;
import com.wd.winddots.activity.select.SelectRelatedCompanyActivity;

import java.util.Calendar;

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

    @BindView(R.id.tv_delivery_date)
    TextView mDeliveryDateTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_simple_order);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.iv_back, R.id.ll_related_company, R.id.ll_goods, R.id.ll_delivery_date})
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
//                        mData.setTime(date);
                }, year, month, day);

                dp.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_RELATED_COMPANY:
                    if (null != data) {
                        String relatedCompanyName = data.getStringExtra("relatedCompanyName");
                        mRelatedCompanyTv.setText(relatedCompanyName);
                        mRelatedCompanyTv.setTextColor(ContextCompat.getColor(this, R.color.color32));
                    }
                    break;
            }
        }
    }
}
