package com.wd.winddots.net.enterprise;


import com.wd.winddots.confifg.Config;
import com.wd.winddots.net.HeaderInterceptor;
import com.wd.winddots.net.RetrofitService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EnterpriseRetrofitHelper {
    // 消息  baseurl带端口

//    OkHttpClient client = new OkHttpClient();

    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(30, TimeUnit.SECONDS).
            readTimeout(30, TimeUnit.SECONDS).
            writeTimeout(30, TimeUnit.SECONDS).
            addNetworkInterceptor(new HeaderInterceptor())
            .build();


    ScalarsConverterFactory mScalarsConverterFactory = ScalarsConverterFactory.create();
    private static EnterpriseRetrofitHelper instance = null;
    private Retrofit mRetrofit = null;
    public static EnterpriseRetrofitHelper getInstance(){
        if (instance == null){
            synchronized(EnterpriseRetrofitHelper.class){
                if (instance == null){
                    instance = new EnterpriseRetrofitHelper();
                }
            }
        }
        return instance;
    }
    private EnterpriseRetrofitHelper(){
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Config.enterprise_base_url)
                .client(client)
                .addConverterFactory(mScalarsConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    public RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }
}
