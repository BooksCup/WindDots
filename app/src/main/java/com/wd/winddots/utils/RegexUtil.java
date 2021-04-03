package com.wd.winddots.utils;

import java.util.regex.Pattern;

/**
 * 正则工具类
 *
 * @author zhou
 */
public class RegexUtil {

    /**
     * 判断手机号格式是否正确
     *
     * @param phone 手机号
     * @return true:正确 false:错误
     */
    public static boolean validateMobilePhone(String phone) {
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        return pattern.matcher(phone).matches();
    }

}
