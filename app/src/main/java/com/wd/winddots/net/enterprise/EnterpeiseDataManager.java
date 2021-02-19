package com.wd.winddots.net.enterprise;


import com.wd.winddots.net.RetrofitService;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

public class EnterpeiseDataManager {
    private RetrofitService mRetrofitService;

    public EnterpeiseDataManager() {
        this.mRetrofitService = EnterpriseRetrofitHelper.getInstance().getServer();
    }



    /**
     * 企业列表
     *
     * @param
     * @return
     */
    public Observable<String> enterpriseSearchList(String word) {
        return mRetrofitService.enterpriseSearchList(word);
    }

    /**
     * 企业详情
     *
     * @param
     * @return
     */
    public Observable<String> enterpriseDetail(String id) {
        return mRetrofitService.enterpriseDetail(id);
    }






}
