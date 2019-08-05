package com.smc.androidbase.test;

import java.io.Serializable;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ColoredCircle implements Serializable{

    int x;
    int y;

    public ColoredCircle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y;
    }
}
