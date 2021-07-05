package com.example.stressmanagementapp.Util;

import android.os.Build;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
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
        String dateStrInMeasuredRecord = String.format("%s-%s-%sT%s%s%s%s",year,month,day,hour,minute,second,millis);
        return dateStrInMeasuredRecord;
    }
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
    public static SimpleDateFormat getSimpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }
    public static SimpleDateFormat humanReadableDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }
    public static String stringToSpecificDateFormatString(String dateStr){
        SimpleDateFormat simpleDate = getSimpleDateFormat();
        simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date=null;
        try {
            date = simpleDate.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return simpleDate.format(date);
    }
    public static String getCurrentDateWithUTC(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return String.valueOf(Clock.systemUTC().instant());
        }
        return null;
    }

    public static String getCurrentLocalDate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return String.valueOf(LocalDateTime.now());
        }
        return null;
    }

}
