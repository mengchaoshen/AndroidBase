package com.smc.androidbase.arc;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/17
 * @description
 */

public class Time {

    private int hour;
    private int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return hour + " : " + minute;
    }
}
