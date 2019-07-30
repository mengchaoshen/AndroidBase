package com.smc.androidbase.arc;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/25
 * @description
 */

public class MessageBean {

    //自定义邀请对方
    public final static String MSG_TYPE_INVITE = "1000";
    //自定义回复对方
    public final static String MSG_TYPE_REPLY = "1001";
    //自定义挂断
    public final static String MSG_TYPE_HANG_UP = "1004";
    public final static String RESULT_ACCEPT = "1";
    public final static String RESULT_REJECT = "0";

    private String sessionid;
    private String id;
    private String nickname;
    private String iconurl;
    private String msgtype;
    private String result = "";//result：1代表接受，0代表拒绝

    public MessageBean(String id, String nickname, String iconurl) {
        this.id = id;
        this.nickname = nickname;
        this.iconurl = iconurl;
    }

    public MessageBean(String sessionid, String id, String nickname, String msgtype, String result) {
        this.sessionid = sessionid;
        this.id = id;
        this.nickname = nickname;
        this.msgtype = msgtype;
        this.result = result;
    }

    public MessageBean(String sessionid, String id, String nickname, String msgtype) {
        this.sessionid = sessionid;
        this.id = id;
        this.nickname = nickname;
        this.msgtype = msgtype;
    }

    public boolean isResultAccept() {
        return RESULT_ACCEPT.equals(result);
    }


    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }
}
