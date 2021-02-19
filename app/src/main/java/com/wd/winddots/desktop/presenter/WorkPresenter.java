package com.wd.winddots.desktop.presenter;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public interface WorkPresenter {


     /*
     * 获取桌面列表数据
     * @param
     * */
     void getWorkList(String userId);


     /*
     * 删除办公图标
     * */
     void deleteItem(String userId, String applicationId, List<Map> applicationIds, String enterpriseId);

     /*
      * 排序办公图标
      * */
     void setApplicationSort(String userId, RequestBody body, String enterpriseId);

}
