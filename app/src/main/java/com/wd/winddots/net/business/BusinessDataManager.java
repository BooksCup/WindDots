package com.wd.winddots.net.business;



import com.wd.winddots.net.RetrofitService;

import okhttp3.RequestBody;
import rx.Observable;

public class BusinessDataManager {
    private RetrofitService mRetrofitService;
    public BusinessDataManager(){
        this.mRetrofitService = BusinessRetrofitHelper.getInstance().getServer();
    }


    /**
     * 获取商圈列表
     * @return
     */
    public Observable<String> getBusinessList(){
        return mRetrofitService.getBusinessList();
    }

    /**
     * 获取原材料列表
     * @return
     */
    public Observable<String> getMaterialsPrice(String date,String type,String name){
        return mRetrofitService.getMaterialsPrice(date,type,name);
    }

    /**
     * 获取汇率类型
     * @return
     */
    public Observable<String> getRateCurrency(RequestBody body){
        return mRetrofitService.getRateCurrency(body);
    }


    /**
     * 获取原材料类型
     * @return
     */
    public Observable<String> getPriceType(String userId,String momentsType){
        return mRetrofitService.getPriceType(userId,momentsType);
    }

    /**
     * 自定义查询类型
     * @return
     */
    public Observable<String> addBusinessTypeList(String userId,String momentsType, RequestBody body){
        return mRetrofitService.addBusinessTypeList(userId,momentsType,body);
    }

    /**
     * 获取原材料历史数据
     * @return
     */
    public Observable<String> getMaterialsHistoryPrice(String date, RequestBody body){
        return mRetrofitService.getMaterialsHistoryPrice(date,body);
    }

    /**
     * 获取商圈列表
     * @return
     */
    public Observable<String> getRateHistoryPrice(String date, RequestBody body){
        return mRetrofitService.getRateHistoryPrice(date,body);
    }







}
