package com.smc.androidbase.test;

import java.io.Serializable;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class User implements Serializable {

    //被static修饰的属性，是不能被序列化的
    static String userName;

    //添加了transient关键字以后，在序列化的时候会被排除掉
    transient String pwd;

    public User(String userName, String pwd) {
        this.userName = userName;
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
