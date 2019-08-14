package com.smc.androidbase.opengles;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class UserData {

    private int programObject;
    private int[] vobIds;
    private int[] vaoIds;
    private int[] fbo;
    private int[] colorTexId;

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

    public int[] getFbo() {
        return fbo;
    }

    public void setFbo(int[] fbo) {
        this.fbo = fbo;
    }

    public int[] getColorTexId() {
        return colorTexId;
    }

    public void setColorTexId(int[] colorTexId) {
        this.colorTexId = colorTexId;
    }
}
