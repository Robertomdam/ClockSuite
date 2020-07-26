package com.rmm.clocksuite.entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class AlarmRepeatMode
{
    public long           mId;
    public boolean    mMonday;
    public boolean   mTuesday;
    public boolean mWednesday;
    public boolean  mThursday;
    public boolean    mFriday;
    public boolean  mSaturday;
    public boolean    mSunday;

    public AlarmRepeatMode ()
    {
        mMonday     = false;
        mTuesday    = false;
        mWednesday  = false;
        mThursday   = false;
        mFriday     = false;
        mSaturday   = false;
        mSunday     = false;
    }
}

//@Entity (
//        tableName = "table_alarm_repeat_mode",
//        foreignKeys = @ForeignKey(
//                entity = Alarm.class,
//                parentColumns = "alarmId",
//                childColumns = "alarmId",
//                onDelete = ForeignKey.CASCADE
//        )
//)
//class AlarmRepeatMode
//{
//    @ColumnInfo (name = "alarmId")   public long      mAlarmId;
//    @ColumnInfo (name = "monday")    public boolean    mMonday;
//    @ColumnInfo (name = "Tuesday")   public boolean   mTuesday;
//    @ColumnInfo (name = "Wednesday") public boolean mWednesday;
//    @ColumnInfo (name = "Thursday")  public boolean  mThursday;
//    @ColumnInfo (name = "Friday")    public boolean    mFriday;
//    @ColumnInfo (name = "Saturday")  public boolean  mSaturday;
//    @ColumnInfo (name = "Sunday")    public boolean    mSunday;
//
//    public AlarmRepeatMode ()
//    {
//        mMonday     = false;
//        mTuesday    = false;
//        mWednesday  = false;
//        mThursday   = false;
//        mFriday     = false;
//        mSaturday   = false;
//        mSunday     = false;
//    }
//}
