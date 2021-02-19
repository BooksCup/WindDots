package com.wd.winddots.bean.param;

public class SendMessageParam {
    private String fromId;
    private String targetId;
    private String body;
    private String targetType;
    private String msgType;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "SendMessageParam{" +
                "fromId='" + fromId + '\'' +
                ", targetId='" + targetId + '\'' +
                ", body='" + body + '\'' +
                ", targetType='" + targetType + '\'' +
                ", msgType='" + msgType + '\'' +
                '}';
    }
}
