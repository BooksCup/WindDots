package com.wd.winddots.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CommonUtils {

    //dp转px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //px转dp
    public static int px2dip(Context context, int pxValue) {
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, context.getResources().getDisplayMetrics()));
    }


    /**
     * 获取屏幕宽度 px
     *
     * @param activity
     * @return
     */
    public static int getScreenWidthPixls(Activity activity) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        return widthPixels;
    }

    /**
     * 获取屏幕高度 px
     *
     * @param activity
     * @return
     */
    public static int getScreenHeightPixls(Activity activity) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        return heightPixels;
    }


    /**
     * 将时间戳转换成描述性时间（昨天、今天、明天）
     *
     * @param timestamp 时间戳
     * @return 描述性日期
     */
    public static String descriptiveData(long timestamp) {
        String descriptiveText = null;
        String format = "yyyy-MM-dd HH:mm";
        //当前时间
        Calendar currentTime = Calendar.getInstance();
        //要转换的时间
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(timestamp);
        //年相同
        if (currentTime.get(Calendar.YEAR) == time.get(Calendar.YEAR)) {
            //获取一年中的第几天并相减，取差值
            switch (currentTime.get(Calendar.DAY_OF_YEAR) - time.get(Calendar.DAY_OF_YEAR)) {
                case 2://当前比目标多两天，那么目标就是前天
                    descriptiveText = "前天";
                    format = "HH:mm";
                    break;
                case 1://当前比目标多一天，那么目标就是昨天
                    descriptiveText = "昨天";
                    format = "HH:mm";
                    break;
                case 0://当前和目标是同一天，就是今天
//                    descriptiveText = "今天";
                    descriptiveText = "";
                    format = "HH:mm";
                    break;
                case -1://当前比目标少一天，就是明天
                    descriptiveText = "明天";
                    format = "HH:mm";
                    break;
                default:
                    descriptiveText = null;
                    format = "yyyy-MM-dd HH:mm";
                    break;
            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        String formatDate = simpleDateFormat.format(time.getTime());
        if (!TextUtils.isEmpty(descriptiveText)) {
            return descriptiveText + " " + formatDate;
        }
        return formatDate;
    }


    // 截取时间到日期
    public static String subTime2Date(String time) {
        String subTime = time.substring(0, time.indexOf(" "));
        return subTime;
    }

    // 截取时间到分钟
    public static String subTime2Min(String time) {
        String subTime = time.substring(0, time.lastIndexOf(":"));
        return subTime;
    }

    public static String getTel(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tel = tm.getLine1Number();
        return tel;
    }
}
