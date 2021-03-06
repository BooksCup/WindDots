package com.wd.winddots.net.msg;



import com.wd.winddots.confifg.Config;
import com.wd.winddots.net.HeaderInterceptor;
import com.wd.winddots.net.RetrofitService;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MsgRetrofitHelper {
    // 消息  baseurl带端口

//    OkHttpClient client = new OkHttpClient();

    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(30, TimeUnit.SECONDS).
            readTimeout(30, TimeUnit.SECONDS).
            writeTimeout(30, TimeUnit.SECONDS).
            addNetworkInterceptor(new HeaderInterceptor())
            .build();


    ScalarsConverterFactory mScalarsConverterFactory = ScalarsConverterFactory.create();
    private static MsgRetrofitHelper instance = null;
    private Retrofit mRetrofit = null;
    public static MsgRetrofitHelper getInstance(){
        if (instance == null){
            synchronized(MsgRetrofitHelper.class){
                if (instance == null){
                    instance = new MsgRetrofitHelper();
                }
            }
        }
        return instance;
    }
    private MsgRetrofitHelper(){
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Config.msg_base_url)
                .client(client)
                .addConverterFactory(mScalarsConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    public RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }
}
