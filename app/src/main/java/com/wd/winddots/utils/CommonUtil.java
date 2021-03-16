package com.wd.winddots.utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.Attr;
import com.wd.winddots.entity.Goods;
import com.wd.winddots.entity.StockInApply;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CommonUtil {

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

    public static String getFirstPhotoFromJsonList(String photoListJson) {
        String photo = "";
        try {
            List<String> goodsPhotoList = JSON.parseArray(photoListJson, String.class);
            if (null != goodsPhotoList && goodsPhotoList.size() > 0) {
                photo = goodsPhotoList.get(0);
                if (!photo.startsWith(Constant.HTTP_PROTOCOL_PREFIX) ||
                        !photo.startsWith(Constant.HTTPS_PROTOCOL_PREFIX)) {
                    photo = Constant.HTTP_PROTOCOL_PREFIX + photo;
                }
            }
        } catch (Exception e) {
            photo = "";
        }
        return photo;
    }

    public static List<String> attrJsonToAttrStrList(String attrJson) {
        List<String> attrList = new ArrayList<>();
        try {
            LinkedHashMap<String, JSONArray> attrMap = JSON.parseObject(attrJson, LinkedHashMap.class);
            for (Map.Entry<String, JSONArray> entry : attrMap.entrySet()) {
                Attr attr = new Attr();
                attr.setKey(entry.getKey());
                JSONArray valueJson = entry.getValue();
                Object value = new Object();
                if (null != valueJson && valueJson.size() > 0) {
                    value = valueJson.getJSONObject(0).get("name");
                }
                attrList.add(entry.getKey() + ":" + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attrList;
    }

    public static Goods getGoodsFromStockInApply(StockInApply stockInApply) {
        Goods goods = new Goods();
        goods.setGoodsName(stockInApply.getGoodsName());
        goods.setGoodsNo(stockInApply.getGoodsNo());
        goods.setStockNum(stockInApply.getApplyNumber());
        goods.setGoodsUnit(stockInApply.getGoodsUnit());
        goods.setAttrList(stockInApply.getAttrList());
        goods.setGoodsPhotos(stockInApply.getGoodsPhotos());
        goods.setGoodsSpecList(StockUtil.getGoodsSpecListFromStockRecordList(stockInApply.getStockApplicationInRecordList()));
        goods.setX(stockInApply.getX());
        goods.setY(stockInApply.getY());
        return goods;
    }
}
