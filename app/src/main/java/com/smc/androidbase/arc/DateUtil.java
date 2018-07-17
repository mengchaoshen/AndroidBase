package com.smc.androidbase.arc;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/7/17
 * @description
 */

public class DateUtil {

    public static String getHourAndMinuteStr(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(time));
    }

    public static Time getTime(long timeLong) {
        Date date = new Date(timeLong);
        Time time = new Time(date.getHours(), date.getMinutes());
        return time;
    }
}
