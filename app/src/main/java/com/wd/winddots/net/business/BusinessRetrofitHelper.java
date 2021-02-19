package com.wd.winddots.net.business;


import com.wd.winddots.confifg.Config;
import com.wd.winddots.net.HeaderInterceptor;
import com.wd.winddots.net.RetrofitService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BusinessRetrofitHelper {
    // 其他 baseurl不带端口

//    OkHttpClient client = new OkHttpClient();
    ScalarsConverterFactory mScalarsConverterFactory = ScalarsConverterFactory.create();
    private static BusinessRetrofitHelper instance = null;
    private Retrofit mRetrofit = null;
    public static BusinessRetrofitHelper getInstance(){
        if (instance == null){
            synchronized(BusinessRetrofitHelper.class){
                if (instance == null){
                    instance = new BusinessRetrofitHelper();
                }
            }
        }
        return instance;
    }
    private BusinessRetrofitHelper(){
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        OkHttpClient.Builder httpBuilder=new OkHttpClient.Builder();
        OkHttpClient client=httpBuilder.readTimeout(30000, TimeUnit.MINUTES)
                .connectTimeout(30000, TimeUnit.MINUTES).writeTimeout(30000, TimeUnit.MINUTES).//设置超时
                addNetworkInterceptor(new HeaderInterceptor())
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Config.business_base_url)
                .client(client)
                .addConverterFactory(mScalarsConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


    }

    public RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }
}
