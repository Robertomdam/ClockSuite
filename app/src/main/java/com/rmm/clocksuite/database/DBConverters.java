package com.rmm.clocksuite.database;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DBConverters {

    @TypeConverter
    public static Calendar convertToCalendar (long timeMilliseconds) {
        Calendar calendar = Calendar.getInstance ();
        calendar.setTimeInMillis (timeMilliseconds);
        return calendar;
    }

    @TypeConverter
    public static long convertToMilliseconds (Calendar calendar) {
        return calendar != null ? calendar.getTimeInMillis() : null;
    }

}
