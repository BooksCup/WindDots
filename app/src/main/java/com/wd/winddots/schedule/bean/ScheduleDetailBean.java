package com.wd.winddots.schedule.bean;

import com.wd.winddots.bean.resp.ScheduleBena;

/**
 * FileName: ScheduleDetailBean
 * Author: 郑
 * Date: 2020/6/4 2:06 PM
 * Description:
 */
public class ScheduleDetailBean {

    private int code;
    private ScheduleBena data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ScheduleBena getData() {
        return data;
    }

    public void setData(ScheduleBena data) {
        this.data = data;
    }
}
