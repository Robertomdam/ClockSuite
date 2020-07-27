package com.rmm.clocksuite.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity (tableName = "table_alarms")
public class Alarm {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "alarmId") public long mId;
    @ColumnInfo (name = "enabled") public boolean mEnabled;

    @ColumnInfo (name = "note") public String mNote;
    @ColumnInfo (name = "time") public Calendar mTime;

    @Embedded (prefix = "repeatMode_") public AlarmRepeatMode alarmRepeatMode;

    public Alarm ()
    {
        mId = 0;        // This has to be zero in order to Room (@insert) to not be inserted in the query
        mEnabled = false;
        mNote = "";
        alarmRepeatMode = new AlarmRepeatMode ();
    }

    public long getId ()
    {
        return mId;
    }

    public boolean getEnabled ()
    {
        return mEnabled;
    }

    public void setEnabled (boolean enabled)
    {
        mEnabled = enabled;
    }

    public String getNote ()
    {
        return mNote;
    }

    public void setNote (String note)
    {
        mNote = note;
    }

    public Calendar getTime ()
    {
        return mTime;
    }

    public void setTime (Calendar time)
    {
        mTime = time;
    }

    public int getHour ()
    {
        return mTime.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute ()
    {
        return mTime.get(Calendar.MINUTE);
    }
}
