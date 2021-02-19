package com.wd.winddots.fast.presenter;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * FileName: MineClaimingAddDetailPresenter
 * Author: 郑
 * Date: 2020/5/12 2:23 PM
 * Description:
 */
public interface MineClaimingAddDetailPresenter {

    void getCostType(String enterpeiseId);


    void upLoadImage(RequestBody body);

}
