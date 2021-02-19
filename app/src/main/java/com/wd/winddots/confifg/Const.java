package com.wd.winddots.confifg;

/**
 * FileName: Const
 * Author: 郑
 * Date: 2020/5/6 4:46 PM
 * Description: 常量
 */
public interface Const {

    int SINGLE_USER_SELECT_TARGRT = 100;//抄送人页面标记
    int MULTIPLE_USER_SELECT_TARGRT = 101;//审核人页面标记

      /*
      * 发票类型
      * */
    String INVOICE_TYPE_NO_INVOICE = "1"; //不开票
    String INVOICE_TYPE_ALREADY = "0"; //开票已到
    String INVOICE_TYPE_NO_ALREADY = "2";//开票未到

    /*
    * 审批类型
    * */
    String APPROVA_INVOICE_4 = "4";//个人报销
    String APPROVA_INVOICE_5 = "5";//单位报销
    String APPROVA_INVOICE_6 = "6";//个人借款
    String APPROVA_INVOICE_7 = "7";//单位借款
    String APPROVA_LEAVE_0 = "0";//请假
    String APPROVA_OVERTIME_1 = "1";//加班
    String APPROVA_TRIP_11 = "11";//公出


    String COLOR_BLUE = "#33B3EA";
    String COLOR_ACCENT = "#D22B34";

    /*
    * 网页链接 intent 传值
    * */
    String WEB_ACTIVITY_URL_INTENT = "WEB_ACTIVITY_URL_INTENT";


}
