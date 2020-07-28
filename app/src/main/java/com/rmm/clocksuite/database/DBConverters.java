package com.rmm.clocksuite.database;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Room database helper that allow the library to handle some class conversions when interacting with the database.
 */
public class DBConverters {

    /**
     * Gets a calendar from a milliseconds variable.
     * @param timeMilliseconds The milliseconds.
     * @return The calendar representation of that milliseconds number.
     */
    @TypeConverter
    public static Calendar convertToCalendar (long timeMilliseconds) {
        Calendar calendar = Calendar.getInstance ();
        calendar.setTimeInMillis (timeMilliseconds);
        return calendar;
    }

    /**
     * Gest a number in milliseconds from a Calendar instance.
     * @param calendar The calendar object.
     * @return The corresponding time in milliseconds.
     */
    @TypeConverter
    public static long convertToMilliseconds (Calendar calendar) {
        return calendar != null ? calendar.getTimeInMillis() : null;
    }

}
