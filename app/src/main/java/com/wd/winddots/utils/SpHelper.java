package com.wd.winddots.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.entity.User;

/**
 * sharepreference工具类
 *
 * @author zhou
 */
public class SpHelper {

    public static final String USER_KEY = "user";
    public static final String userIdKey = "id";
    public static final String enterpriseIdKey = "enterpriseId";
    public static final String avatarKey = "avatar";
    public static final String nameKey = "name";

    protected static SpHelper mSpHelper;
    private Context mContext;

    private SharedPreferences sp;
    private String FILE_NAME = "sp_store";

    private SpHelper(Context context) {
        mContext = context.getApplicationContext();
        sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static SpHelper getInstance(Context context) {
        if (mSpHelper == null) {
            synchronized (SpHelper.class) {
                if (mSpHelper == null) {
                    mSpHelper = new SpHelper(context);
                }
            }
        }
        return mSpHelper;
    }


    public void setString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    public void setBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public void setInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }


    public String getUserId() {
        return sp.getString(userIdKey, "");
    }

    public String getEnterpriseId() {
        return sp.getString(enterpriseIdKey, "");
    }

    public String getAvatar() {
        return sp.getString(avatarKey, "");
    }

    public String getName() {
        return sp.getString(nameKey, "");
    }

    public boolean isFabricCheckAdmin() {
        String isFabricCheckAdminKey = sp.getString("isFabricCheckAdmin", "");
        if (Constant.USER_IS_FABRIC_CHECK_ADMIN.equals(isFabricCheckAdminKey)) {
            return true;
        } else {
            return false;
        }
    }

    public void setUser(User user) {
        sp.edit().putString(USER_KEY, JSON.toJSONString(user)).apply();
    }

    public User getUser() {
        User user;
        try {
            user = JSON.parseObject(sp.getString(USER_KEY, ""), User.class);
            if (null == user) {
                user = new User();
            }
        } catch (Exception e) {
            user = new User();
        }
        return user;
    }

}
