package com.chxt.fantasticmonkey.util;

import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class DayFormatUtils {

    private final static String[] weekEnums = new String[] {"", "周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private final static String[] weekEnumsEn = new String[] {"", "Sun.", "Mon.", "Tues.", "Wed.", "Thur.", "Fri.", "Sat."};

    @SneakyThrows
    public static String getDayOfWeek(String dateStr){
        Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
        return getDayOfWeek(date);
    }

    @SneakyThrows
    public static String getDayOfWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        return weekEnums[i];
    }

    @SneakyThrows
    public static String getDayOfWeekEn(String dateStr){
        Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
        return getDayOfWeekEn(date);
    }

    @SneakyThrows
    public static String getDayOfWeekEn(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        return weekEnumsEn[i];
    }

    @SneakyThrows
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }


}
