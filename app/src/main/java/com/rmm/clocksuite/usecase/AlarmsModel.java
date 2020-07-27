package com.rmm.clocksuite.usecase;

import android.content.Context;

import com.rmm.clocksuite.database.DBManager;
import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.presenter.IAlarmsContracts;

import java.util.ArrayList;

public class AlarmsModel implements IAlarmsContracts.IAlarmsModel {

    DBManager dbManager;

    public AlarmsModel(Context appContext) {
        dbManager = new DBManager (appContext);
    }

    @Override
    public ArrayList<Alarm> getAllAlarms() {
        return dbManager.getAllAlarms();
    }

    @Override
    public void addAlarm (Alarm alarm) {
        dbManager.insertAlarm (alarm);
    }

    @Override
    public void updateAlarm(Alarm alarm) { dbManager.updateAlarm (alarm); }

    @Override
    public void removeAlarm (long alarmId) {
        dbManager.removeAlarm (alarmId);
    }

    @Override
    public Alarm getAlarm (long id) {
        return dbManager.getAlarmsById (id);
    }
}
