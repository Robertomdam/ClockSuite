package com.rmm.clocksuite.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.rmm.clocksuite.entity.Alarm;

/**
 * Room database wrapper as followed by the official Android documentation.
 * Its a singleton class that requires to access to the database asynchronously.
 */
@Database(entities = {Alarm.class}, version = DBConst.DATABASE_VERSION)
@TypeConverters({DBConverters.class})
public abstract class RoomDatabaseWrapper extends RoomDatabase {

    abstract IAlarmsDao daoAlarms ();

    private static RoomDatabaseWrapper instance;

    public static RoomDatabaseWrapper getInstance (Context appContext)
    {
        if (instance == null)
            synchronized (RoomDatabaseWrapper.class) {
                instance = Room.databaseBuilder (appContext, RoomDatabaseWrapper.class, DBConst.DATABASE_NAME).build();
//                instance = Room.databaseBuilder (appContext, CustomDatabase.class, DBConst.DATABASE_NAME).allowMainThreadQueries().build();
            }
        return instance;
    }
}
