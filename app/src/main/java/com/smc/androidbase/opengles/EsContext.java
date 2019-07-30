package com.smc.androidbase.opengles;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class EsContext {

    UserData userData;
    int width;
    int height;

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
