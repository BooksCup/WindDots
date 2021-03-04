package com.wd.winddots.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String formatTimeToDate(String time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String result;
        try {
            Date date = timeFormat.parse(time);
            result = dateFormat.format(date);
        } catch (Exception e) {
            result = "";
        }
        return result;
    }
}
