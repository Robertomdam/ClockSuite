package com.rmm.clocksuite.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.rmm.clocksuite.entity.Alarm;

@Database(entities = {Alarm.class}, version = DBConst.DATABASE_VERSION)
@TypeConverters({DBConverters.class})
public abstract class CustomDatabase extends RoomDatabase {

    abstract IAlarmsDao daoAlarms ();

    private static CustomDatabase instance;

    public static CustomDatabase getInstance (Context appContext)
    {
        if (instance == null)
            synchronized (CustomDatabase.class) {
                instance = Room.databaseBuilder (appContext, CustomDatabase.class, DBConst.DATABASE_NAME).build();
//                instance = Room.databaseBuilder (appContext, CustomDatabase.class, DBConst.DATABASE_NAME).allowMainThreadQueries().build();
            }
        return instance;
    }
}
