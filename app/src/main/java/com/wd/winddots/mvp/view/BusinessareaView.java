package com.wd.winddots.mvp.view;

import com.wd.winddots.bean.resp.BusinessBean;

import java.util.List;

public interface BusinessareaView {
    void onLoadBusinessAreaDataSuccess(BusinessBean bean);
    void onLoadBusinessAreaDataError(String msg);
    void onLoadBusinessAreaDataComplete();
}
