package com.wd.winddots.cons;

/**
 * 常量类
 */
public interface Constant {
    //public static final String APP_BASE_URL = "http://192.168.8.123:8085/";


    //172.168.0.93:8086
    //http://115.159.201.120:8085/
    String APP_BASE_URL = "http://115.159.201.120:8085/";
//    public static final String APP_BASE_URL =  "http://172.168.0.145:8085/";

    //        public static final String APP_BASE_URL = "http://115.159.201.120:8085/";
    String APP_BASE_URL_ELSE = "http://42.192.134.170/";

    String WEB_VIEW_BASE_URL = "http://42.192.167.215/";

    /**
     * 网页链接intent传值
     */
    String WEB_ACTIVITY_URL_INTENT = "WEB_ACTIVITY_URL_INTENT";

    // 企业状态
    /**
     * 注销
     */
    String ENTERPRISE_STATUS_CANCEL = "0";

    /**
     * 在业
     */
    String ENTERPRISE_STATUS_AT_WORK = "1";

    /**
     * 存续
     */
    String ENTERPRISE_STATUS_EXISTENCE = "2";

    /**
     * 操作状态-同意
     */
    String OPERATE_STATUS_AGREE = "1";

    /**
     * 操作状态-拒绝
     */
    String OPERATE_STATUS_REFUSE = "2";

    String HTTP_PROTOCOL_PREFIX = "http:";
    String HTTPS_PROTOCOL_PREFIX = "https:";

    String STOCK_TYPE_IN = "1";
    String STOCK_TYPE_OUT = "2";

    /**
     * 采购入库
     */
    String STOCK_BIZ_TYPE_PURCHASE_IN = "3";

    /**
     * 二维码内容物品前缀
     */
    String QR_CODE_CONTENT_PREFIX_GOODS = "goodsId=";

    int USER_IS_SUPER_ADMIN = 1;
    String USER_IS_FABRIC_CHECK_ADMIN = "1";

    /**
     * 已安装
     */
    String IS_INSTALL = "1";

    /**
     * 角色-仓管
     */
    String ROLE_WAREHOUSE_KEEPER = "1";

    /**
     * 内部仓库
     */
    String WAREHOUSE_IS_OWN = "1";

    String REQUEST_ROLE_APPLY = "0";
    String REQUEST_ROLE_CONFIRM = "1";

    String REAL_NAME_CERT_SUCCESS = "1";

}