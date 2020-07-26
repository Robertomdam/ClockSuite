package com.rmm.clocksuite.database;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rmm.clocksuite.entity.Alarm;

import java.util.ArrayList;

public class DBManager {

    CustomDatabase mDatabase;
    IAlarmsDao mDAOAlarms;

    public DBManager (Context appContext) {
        mDatabase = CustomDatabase.getInstance (appContext);
        mDAOAlarms = mDatabase.daoAlarms();
    }

    public void insertAlarm (Alarm alarm) {
        mDAOAlarms.insert (alarm);
    }

    public Alarm getAlarmsById (int id) {
        return mDAOAlarms.get (id);
    }

    public ArrayList<Alarm> getAllAlarms () {
        return new ArrayList<Alarm> (mDAOAlarms.getAll ());
    }

    public void removeAlarm (int id) {
        mDAOAlarms.remove (id);
    }

    public void clearAlarms () {
        mDAOAlarms.clear ();
    }
}

