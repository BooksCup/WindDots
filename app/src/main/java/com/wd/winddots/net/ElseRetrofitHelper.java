package com.wd.winddots.net;


import android.util.Log;

import com.wd.winddots.confifg.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;



public class ElseRetrofitHelper {
    // 其他 baseurl不带端口

//    OkHttpClient client = new OkHttpClient();
    ScalarsConverterFactory mScalarsConverterFactory = ScalarsConverterFactory.create();
    private static ElseRetrofitHelper instance = null;
    private Retrofit mRetrofit = null;
    public static ElseRetrofitHelper getInstance(){
        if (instance == null){
            synchronized(ElseRetrofitHelper.class){
                if (instance == null){
                    instance = new ElseRetrofitHelper();
                }
            }
        }
        return instance;
    }
    private ElseRetrofitHelper(){
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
                .baseUrl(Config.else_base_url)
                .client(client)
                .addConverterFactory(mScalarsConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }
}
