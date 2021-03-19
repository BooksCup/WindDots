package com.wd.winddots.jpush.process;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wd.winddots.activity.stock.in.OfficeSuppliesInDetailActivity;

import java.util.Map;

/**
 * 办公用品入库单
 *
 * @author zhou
 */
public class AddOfficeSuppliesStockInProcessor implements PushNotifyProcessor {

    @Override
    public void processMessageReceived(Context context, Bundle bundle, Map<String, String> extrasMap) {

    }

    @Override
    public void processNotificationReceived(Context context, Bundle bundle, Map<String, String> extrasMap) {

    }

    @Override
    public void processNotificationOpened(Context context, Bundle bundle, Map<String, String> extrasMap) {
        Intent intent = new Intent(context, OfficeSuppliesInDetailActivity.class);
        String stockInApplyId = extrasMap.get("stockInApplyId");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("stockInApplyId", stockInApplyId);
        context.startActivity(intent);
    }
}
