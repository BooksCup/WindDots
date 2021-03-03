package com.wd.winddots.cons;

/**
 * 常量类
 */
public class Constant {
        public static final String APP_BASE_URL = "http://115.159.201.120:8085/";
//    public static final String APP_BASE_URL = "http://172.168.0.145:8085/";
    public static final String APP_BASE_URL_ELSE = "http://42.192.134.170/";

    //测试
    //public static final String APP_BASE_URL_CHECK = "http://115.159.201.120:8085/";
    // 企业状态
    /**
     * 注销
     */
    public static final String ENTERPRISE_STATUS_CANCEL = "0";

    /**
     * 在业
     */
    public static final String ENTERPRISE_STATUS_AT_WORK = "1";

    /**
     * 存续
     */
    public static final String ENTERPRISE_STATUS_EXISTENCE = "2";

    /**
     * 操作状态-同意
     */
    public static final String OPERATE_STATUS_AGREE = "1";

    /**
     * 操作状态-拒绝
     */
    public static final String OPERATE_STATUS_REFUSE = "2";

    public static final String HTTP_PROTOCOL_PREFIX = "http:";
    public static final String HTTPS_PROTOCOL_PREFIX = "https:";

    public static final String STOCK_TYPE_IN = "1";
    public static final String STOCK_TYPE_OUT = "2";

    /**
     * 采购入库
     */
    public static final String STOCK_BIZ_TYPE_PURCHASE_IN = "3";

}
