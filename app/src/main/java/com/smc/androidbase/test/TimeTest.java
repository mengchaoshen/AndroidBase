package com.smc.androidbase.test;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class TimeTest {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {

        //clock 得到的是标准时间跟北京时间相差8小时
        Clock clock = Clock.systemUTC();
        System.out.println("" + clock.millis());
        System.out.println("" + clock.instant());

        System.out.println("---");

        //LocalDate 得到的是当地日期
        LocalDate localDate = LocalDate.now();
        LocalDate dateFromClock = LocalDate.now(clock);

        System.out.println("localDate:" + localDate);
        System.out.println("dateFromClock:" + dateFromClock);

        System.out.println("---");

        //LocalTime 得到的是当地时间
        LocalTime time = LocalTime.now();
        LocalTime timeFromClock = LocalTime.now(clock);
        System.out.println("time:" + time);
        System.out.println("timeFromClock:" + timeFromClock);

        //LocalDateTime 得到的是当地的日期和时间
        System.out.println("---");
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime dateTimeFromClock = LocalDateTime.now(clock);
        System.out.println("dateTime:" + dateTime);
        System.out.println("dateTimeFromClock:" + dateTimeFromClock);

        System.out.println("---");
        //得到的是带时区的时间，还可以根据时区来获取时间
        ZonedDateTime zonedDateTime = ZonedDateTime.now();//获取的是当地时间
        ZonedDateTime zonedDateTimeFromClock = ZonedDateTime.now(clock);
        ZonedDateTime zonedDateTimeFromZone = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
        System.out.println("zonedDateTime:" + zonedDateTime);
        System.out.println("zonedDateTimeFromClock:" + zonedDateTimeFromClock);
        System.out.println("zonedDateTimeFromZone:" + zonedDateTimeFromZone);

        System.out.println("---");
        //使用Duration来计算两个LocalDateTime之间的时间差
        LocalDateTime from = LocalDateTime.of(2014, Month.APRIL, 16, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2015, Month.APRIL, 16, 0, 0, 0);
        Duration duration = Duration.between(from, to);
        System.out.println("Duration in days:" + duration.toDays());
        System.out.println("Duration in hours:" + duration.toHours());

    }
}
