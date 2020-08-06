package com.rmm.clocksuite.presenter.alarms;

import android.content.Context;

import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.usecase.AlarmsModel;

import java.util.ArrayList;

/**
 * General presenter that handles the alarms. Its a singleton class.
 */
public class AlarmsPresenter implements IAlarmsContracts.IAlarmsPresenterFromView, IAlarmsContracts.IAlarmsPresenterFromModel {

    private IAlarmsContracts.IAlarmsHandlerPresenter mPresenterAlarmHandler;
    private IAlarmsContracts.IAlarmsDetailsPresenter mPresenterAlarmDetails;

    private IAlarmsContracts.IBaseAlarmsPresenter mCurrentPresenter;

    private IAlarmsContracts.IAlarmsModel mModel;

    /**
     * Initializes the model.
     * This method is required to be executed right after creating and instance of
     * the class (In this case, after the first time we call getInstance).
     * @param appContext The application context.
     */
    public void init (Context appContext) {
        mModel = new AlarmsModel(appContext, this);
    }

    private static AlarmsPresenter mInstance;

    /**
     * Retrieves the instance of the class and creates it if needed.
     * @return The instance of the class.
     */
    public  static AlarmsPresenter getInstance () {
        if (mInstance == null)
            mInstance = new AlarmsPresenter ();

        return mInstance;
    }

    /**
     * This method binds the views with its corresponding presenter.
     * This method must be call once per view in order the view to be notifies properly.
     * @param view The view to be registered.
     */
    @Override
    public void registerView(IAlarmsContracts.IBaseAlarmsView view) {

        if (view instanceof IAlarmsContracts.IAlarmsHandlerView)
            mPresenterAlarmHandler = new AlarmsHandlerPresenter ((IAlarmsContracts.IAlarmsHandlerView) view);
        else if (view instanceof IAlarmsContracts.IAlarmsDetailsView)
            mPresenterAlarmDetails = new AlarmsDetailsPresenter ((IAlarmsContracts.IAlarmsDetailsView) view);
    }

    /**
     * Calls the model to add a new alarm.
     * @param alarm The new alarm to be added.
     */
    @Override
    public void addAlarm (Alarm alarm) {
        mModel.addAlarm (alarm);
    }

    /**
     * Calls the model to update the alarm.
     * @param alarm The alarm to update.
     */
    @Override
    public void updateAlarm(Alarm alarm) {
        mModel.updateAlarm(alarm);
    }

    /**
     * Calls the model to remove an alarm.
     * @param alarm The alarm to be removed.
     */
    @Override
    public void removeAlarm (Alarm alarm) {
        mModel.removeAlarm (alarm);
    }

    /**
     * Retrieves an alarm.
     * @param id The id of the alarm to return.
     * @return The alarm that matches with the id.
     */
    @Override
    public Alarm getAlarm (long id) {
        return mModel.getAlarm(id);
    }

    /**
     * Retrieves the list of alarms.
     * @return The total amount of alarms.
     */
    @Override
    public ArrayList<Alarm> getAllAlarms() {
        return mModel.getAllAlarms();
    }

    /**
     * Sets the current presenter of the view that is active at the moment.
     * @param currentView The current view that is on the foreground.
     */
    @Override
    public void setCurrentView (IAlarmsContracts.IBaseAlarmsView currentView) {

        if (currentView instanceof IAlarmsContracts.IAlarmsHandlerView)
            mCurrentPresenter = mPresenterAlarmHandler;
        else if (currentView instanceof IAlarmsContracts.IAlarmsDetailsView)
            mCurrentPresenter = mPresenterAlarmDetails;
    }

    /**
     * Notifies to the views that the data of the alarms has changed.
     * @param alarmsList The new list of alarms.
     */
    @Override
    public void onDataChanged (ArrayList<Alarm> alarmsList) {

        if (mPresenterAlarmHandler != null)
            mPresenterAlarmHandler.onDataChanged(alarmsList);

        if (mPresenterAlarmDetails != null)
            mPresenterAlarmDetails.onDataChanged(alarmsList);
    }

    @Override
    public void onAlarmAdded(Alarm alarm) {
        mPresenterAlarmHandler.onAlarmAdded(alarm);
        mPresenterAlarmDetails.onAlarmAdded(alarm);
    }

    @Override
    public void onAlarmUpdated(Alarm alarm) {
        mPresenterAlarmHandler.onAlarmUpdated(alarm);
        mPresenterAlarmDetails.onAlarmUpdated(alarm);
    }

    @Override
    public void onAlarmRemoved(Alarm alarmCopy) {
        mPresenterAlarmHandler.onAlarmRemoved(alarmCopy);
        mPresenterAlarmDetails.onAlarmRemoved(alarmCopy);
    }
}
