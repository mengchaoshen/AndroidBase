package com.smc.androidbase.im;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/25
 * @description
 */

public class MessageBean {

    private String sessionid;
    private String id;
    private String nickname;

    public MessageBean(String sessionid, String id, String nickname) {
        this.sessionid = sessionid;
        this.id = id;
        this.nickname = nickname;
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
}
