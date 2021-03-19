package com.wd.winddots.jpush.process;

import android.content.Context;
import android.os.Bundle;

import java.util.Map;

public interface PushNotifyProcessor {

    void processMessageReceived(Context context, Bundle bundle, Map<String, String> extrasMap);

    void processNotificationReceived(Context context, Bundle bundle, Map<String, String> extrasMap);

    void processNotificationOpened(Context context, Bundle bundle, Map<String, String> extrasMap);

}
