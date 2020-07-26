package com.rmm.clocksuite.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rmm.clocksuite.entity.Alarm;

import java.util.List;

@Dao
public interface IAlarmsDao {

    @Insert
    void insert (Alarm alarm);

    @Query("DELETE FROM table_alarms WHERE alarmId LIKE :id")
    void remove (int id);

    @Query("DELETE FROM table_alarms")
    void clear ();

    @Query("SELECT * FROM table_alarms WHERE alarmId LIKE :id")
    Alarm get (int id);

    @Query("SELECT * FROM table_alarms")
    List<Alarm> getAll ();

}
