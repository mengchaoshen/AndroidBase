package com.smc.androidbase.opengles;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class UserData {

    private int programObject;
    private int[] vobIds;
    private int[] vaoIds;

    public int getProgramObject() {
        return programObject;
    }

    public void setProgramObject(int programObject) {
        this.programObject = programObject;
    }

    public int[] getVobIds() {
        return vobIds;
    }

    public void setVobIds(int[] vobIds) {
        this.vobIds = vobIds;
    }

    public int[] getVaoIds() {
        return vaoIds;
    }

    public void setVaoIds(int[] vaoIds) {
        this.vaoIds = vaoIds;
    }
}
