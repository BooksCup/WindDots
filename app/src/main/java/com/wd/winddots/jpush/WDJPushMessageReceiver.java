package com.wd.winddots.jpush;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.wd.winddots.utils.SpHelper;

import androidx.core.app.NotificationCompat;
import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * FileName: WDJPushMessageReceiver
 * Author: 郑
 * Date: 2020/8/11 5:02 PM
 * Description:
 */
public class WDJPushMessageReceiver extends JPushMessageReceiver {
    private static final String TAG = "noti666";


    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        Log.e(TAG, "[onMessage] " + customMessage);
        processCustomMessage(context, customMessage);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {

        // ShortcutBadger.applyCount(context, 16);
        Log.e("noti666", "[onNotifyMessageOpened] " + message);
        try {
            //打开自定义的Activity
//            Intent i = new Intent(context, TestActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE,message.notificationTitle);
//            bundle.putString(JPushInterface.EXTRA_ALERT,message.notificationContent);
//            i.putExtras(bundle);
//            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//            context.startActivity(i);
        } catch (Throwable throwable) {

        }
    }

    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        //  ShortcutBadger.applyCount(context, 15);
        Log.e("noti666", "[onMultiActionClicked] 用户点击了通知栏按钮");
        String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);

        //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
        if (nActionExtra == null) {
            Log.d("noti666", "ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
            return;
        }
        if (nActionExtra.equals("my_extra1")) {
            Log.e("noti666", "[onMultiActionClicked] 用户点击通知栏按钮一");
        } else if (nActionExtra.equals("my_extra2")) {
            Log.e("noti666", "[onMultiActionClicked] 用户点击通知栏按钮二");
        } else if (nActionExtra.equals("my_extra3")) {
            Log.e("noti666", "[onMultiActionClicked] 用户点击通知栏按钮三");
        } else {
            Log.e("noti666", "[onMultiActionClicked] 用户点击通知栏按钮未定义");
        }
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e("noti666", "[onNotifyMessageArrived] ");
        Log.e("noti666", "[收到推送了啊!!!!!!!] ");
        Log.e("noti666", String.valueOf(message));

        int badgeCount = SpHelper.getInstance(context).getInt("badge");
        badgeCount += 1;
        SpHelper.getInstance(context).setInt("badge", badgeCount);
        Log.e("noti666", badgeCount + "badgeCount  ");

        String brand = Build.BRAND;
        if (brand.equals("samsung")) {
            setSamsungBadge(context, badgeCount);
        } else {
            ShortcutBadger.applyCount(context,badgeCount);
//            String launcherClassName = "com.wd.winddots.mvp.widget.MainActivity";
//            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
//            intent.putExtra("badge_count", badgeCount);
//            intent.putExtra("badge_count_package_name", context.getPackageName());
//            intent.putExtra("badge_count_class_name", launcherClassName);
//            context.sendBroadcast(intent);
        }


    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        // ShortcutBadger.applyCount(context, 13);

        Log.e(TAG, "[onNotifyMessageDismiss] " + message);
        SpHelper.getInstance(context).setInt("badge", 0);
        ShortcutBadger.applyCount(context, 0);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        // ShortcutBadger.applyCount(context, 14);
        Log.e(TAG, "[onRegister] " + registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        // ShortcutBadger.applyCount(context, 24);
        Log.e(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        //  ShortcutBadger.applyCount(context, 23);
        Log.e(TAG, "[onCommandResult] " + cmdMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        //  ShortcutBadger.applyCount(context, 22);
        //TagAliasOperatorHelper.getInstance().onTagOperatorResult(context,jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {

        //  ShortcutBadger.applyCount(context, 21);
        // TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context,jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        //  ShortcutBadger.applyCount(context, 20);
        //TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context,jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        //  ShortcutBadger.applyCount(context, 19);
        //TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context,jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, CustomMessage customMessage) {
        // ShortcutBadger.applyCount(context, 18);
//        if (MainActivity.isForeground) {
//            String message = customMessage.message;
//            String extras = customMessage.extra;
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//        }
    }

    @Override
    public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
        // ShortcutBadger.applyCount(context, 17);
        super.onNotificationSettingsCheck(context, isOn, source);
        Log.e(TAG, "[onNotificationSettingsCheck] isOn:" + isOn + ",source:" + source);
    }

    private void setSamsungBadge(Context context, int badge) {
        String launcherClassName = "com.wd.winddots.mvp.widget.MainActivity";
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", badge);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }


}
