package com.rmm.clocksuite.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

/**
 * Data class that represents an alarm.
 */
@Entity (tableName = "table_alarms")
public class Alarm {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "alarmId") private long mId;
    @ColumnInfo (name = "enabled") private boolean mEnabled;

    @ColumnInfo (name = "note") private String mNote;
    @ColumnInfo (name = "time") private Calendar mTime;

    @Embedded (prefix = "repeatMode_") private AlarmRepeatMode mAlarmRepeatMode;

    /**
     * Construct the instance of the class.
     * @param note The note of the alarm.
     * @param time The time of the alarm should activate.
     * @param alarmRepeatMode The repeat mode of the alarm.
     */
    public Alarm (String note, Calendar time, AlarmRepeatMode alarmRepeatMode)
    {
        mId = 0;        // This has to be zero in order to Room (@insert) to not be inserted in the query
        mEnabled = true;

        mNote = note;
        mTime = time;
        mAlarmRepeatMode = alarmRepeatMode;
    }

    /**
     * Getter for the alarm's id.
     * @return The alarm's id.
     */
    public long getId ()
    {
        return mId;
    }

    /**
     * Setter for the alarm's id.
     * @param id The alarm's id.
     */
    public void setId (long id) { mId = id; }

    /**
     * Getter for the alarm's enabled state.
     * @return Whether the alarm is enabled or not.
     */
    public boolean getEnabled ()
    {
        return mEnabled;
    }

    /**
     * Setter for the alarm's enabled state.
     * @param enabled The value of the alarm's enabled state.
     */
    public void setEnabled (boolean enabled)
    {
        mEnabled = enabled;
    }

    /**
     * Getter for the alarm's note.
     * @return The note of the alarm.
     */
    public String getNote ()
    {
        return mNote;
    }

    /**
     * Setter for the alarm's note.
     * @param note The note of the alarm.
     */
    public void setNote (String note)
    {
        mNote = note;
    }

    /**
     * Getter for the alarm's time.
     * @return The time of the alarm as a Calendar object.
     */
    public Calendar getTime ()
    {
        return mTime;
    }

    /**
     * Setter for the alarm's calendar.
     * @param time The calendar that represents the time.
     */
    public void setTime (Calendar time)
    {
        mTime = time;
    }

    /**
     * Getter for the hour part of the time.
     * @return The hour part of the time.
     */
    public int getHour ()
    {
        return mTime.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Getter for the minute part of the time.
     * @return The minute part of the time.
     */
    public int getMinute ()
    {
        return mTime.get(Calendar.MINUTE);
    }

    /**
     * Getter for the alarm repeat mode.
     * @return The alarm's repeat mode.
     */
    public AlarmRepeatMode getAlarmRepeatMode()
    {
        return mAlarmRepeatMode;
    }
    public void setAlarmRepeatMode(AlarmRepeatMode alarmRepeatMode) { mAlarmRepeatMode = alarmRepeatMode; }
}
