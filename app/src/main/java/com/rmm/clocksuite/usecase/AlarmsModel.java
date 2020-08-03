package com.rmm.clocksuite.usecase;

import android.content.Context;

import com.rmm.clocksuite.database.DBManager;
import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.presenter.IAlarmsContracts;

import java.util.ArrayList;

/**
 * Model part of the MVP for alarms management.
 */
public class AlarmsModel implements IAlarmsContracts.IAlarmsModel, DBManager.IStatusHandler {

    private IAlarmsContracts.IAlarmsPresenterFromModel mPresenter;
    private DBManager dbManager;

    /**
     * Construct and instance of the class and creates a database manager.
     * @param appContext The application context.
     * @param presenter A reference to the presenter.
     */
    public AlarmsModel(Context appContext, IAlarmsContracts.IAlarmsPresenterFromModel presenter) {
        dbManager = new DBManager (appContext, this);
        mPresenter = presenter;
    }

    /**
     * Calls the database manager to insert a new alarm.
     * @param alarm The new alarm.
     */
    @Override
    public void addAlarm (Alarm alarm) {
        dbManager.insertAlarmAsync (alarm);
    }

    /**
     * Calls the database manager to update an alarm.
     * @param alarm The alarm to be updated.
     */
    @Override
    public void updateAlarm(Alarm alarm) {
        dbManager.updateAlarmAsync(alarm);
    }

    /**
     * Calls the database manager to retrieve one alarm.
     * @param id The id of the alarm to return.
     * @return The alarm that matches with the id.
     */
    @Override
    public Alarm getAlarm (long id) {
        return dbManager.getAlarmsByIdAsync (id);
    }

    /**
     * Calls the database manager to retrieve all the alarms.
     * @return The list of alarms.
     */
    @Override
    public ArrayList<Alarm> getAllAlarms() {
        return dbManager.getAllAlarmsAsync();
    }

    /**
     * Calls the database manager to remove an alarm.
     * @param alarmCopy The alarm to remove.
     */
    @Override
    public void removeAlarm (Alarm alarmCopy) {
        dbManager.removeAlarmAsync(alarmCopy);
    }



    // Database callbacks

    @Override
    public void onDataInserted(Alarm alarm) {
        mPresenter.onAlarmAdded(alarm);
    }

    @Override
    public void onDataUpdated(Alarm alarm) {
        mPresenter.onAlarmUpdated(alarm);
    }

    @Override
    public void onDataRemoved (Alarm alarmCopy) {
        mPresenter.onAlarmRemoved(alarmCopy);
    }

    /**
     * Callback triggered when the database data has changed. It will notify it to the presenter.
     * @param alarmsList The new list of the alarms.
     */
    @Override
    public void onDatabaseUpdate(ArrayList<Alarm> alarmsList) {
        mPresenter.onDataChanged (alarmsList);
    }
}
