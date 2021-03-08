package com.wd.winddots.enums;

/**
 * 服务器返回信息
 *
 * @author zhou
 */
public enum ResponseMsgEnum {

    VERIFY_CODE_EXPIRE("VERIFY_CODE_EXPIRE", "验证码失效"),
    ;

    ResponseMsgEnum(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    private String responseCode;
    private String responseMessage;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public static String getValue(String responseCode) {
        ResponseMsgEnum[] responseMsgEnums = values();
        for (ResponseMsgEnum responseMsgEnum : responseMsgEnums) {
            if (responseMsgEnum.getResponseCode().equals(responseCode)) {
                return responseMsgEnum.getResponseMessage();
            }
        }
        return null;
    }

}
