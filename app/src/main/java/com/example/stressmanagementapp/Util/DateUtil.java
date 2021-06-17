package com.example.stressmanagementapp.Util;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static String getDateStringInMeasuredRecord(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        int millis = now.get(Calendar.MILLISECOND);
        String dateStrInMeasuredRecord = String.format("%s%s%sT%s%s%s%s",year,month,day,hour,minute,second,millis);
        return dateStrInMeasuredRecord;
    }

}
