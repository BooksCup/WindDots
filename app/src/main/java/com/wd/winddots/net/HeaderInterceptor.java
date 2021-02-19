package com.wd.winddots.net;



import com.wd.winddots.MyApplication;

import java.io.IOException;

import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * FileName: HeaderInterceptor
 * Author: 郑
 * Date: 2020/4/29 2:45 PM
 * Description: 设置统一请求头
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!StringUtils.isNullOrEmpty(MyApplication.ENTERPRISE_ID)){
            request.newBuilder().addHeader("enterpriseId",MyApplication.ENTERPRISE_ID).
                    addHeader("Content-Type","application/json;charset=UTF-8");
        }else {
            request.newBuilder().addHeader("enterpriseId",MyApplication.ENTERPRISE_ID).
                    addHeader("Content-Type","application/json;charset=UTF-8");
        }
        return chain.proceed(request);
    }
}
