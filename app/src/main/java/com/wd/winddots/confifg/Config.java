package com.wd.winddots.confifg;

public interface Config {

    // 4baddb121f914d0eb608d5c217b5a6f3
    // a2c3b2f6d32345b2818be757f5adb54f
    //http://54.223.179.143:9085/ 测试环境

//    String msg_base_url = "http://54.223.179.143:8091/";  //消息baseurl
    String msg_base_url = "http://42.192.134.170:8091/";  //消息baseurl
    String else_base_url = "http://42.192.134.170";// 其他baseurl
    String business_base_url = "http://115.159.201.120:8080";// 办公baseurl
    String enterprise_base_url = "http://42.192.171.217:8086";


    // a2c3b2f6d32345b2818be757f5adb54f   userId

    int TAB_BUSINESSAREA = 0;//商圈
    int TAB_DESKTOP = 1;//桌面
    int TAB_MESSAGE = 2;//消息
    int TAB_SCHEDULE = 3;//日程
    int TAB_SHORTCUT = 4;//快捷


}
