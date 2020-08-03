package com.rmm.clocksuite.presenter;

import android.content.Context;

import com.rmm.clocksuite.entity.Alarm;

import java.util.ArrayList;

public interface IAlarmsContracts {

    public interface IBaseAlarmsView {
        void onDataChanged  (ArrayList<Alarm> alarms);
        void onAlarmAdded   (Alarm alarm);
        void onAlarmUpdated (Alarm alarm);
        void onAlarmRemoved (Alarm alarmCopy);
    }
    public interface IAlarmsHandlerView extends IBaseAlarmsView {}
    public interface IAlarmsDetailsView extends IBaseAlarmsView {}

    public interface IBaseAlarmsPresenter {
        void onDataChanged  (ArrayList<Alarm> alarms);
        void onAlarmAdded   (Alarm alarm);
        void onAlarmUpdated (Alarm alarm);
        void onAlarmRemoved (Alarm alarmCopy);
    }
    public interface IAlarmsHandlerPresenter extends IBaseAlarmsPresenter {}
    public interface IAlarmsDetailsPresenter extends IBaseAlarmsPresenter {}

    public interface IAlarmsPresenterFromView {
        void registerView (IBaseAlarmsView view);
        void addAlarm (Alarm alarm);
        void updateAlarm (Alarm alarm);
        void removeAlarm (Alarm alarm);
        Alarm getAlarm (long id);
        ArrayList<Alarm> getAllAlarms ();
        void setCurrentView (IBaseAlarmsView currentView);
    }

    public interface IAlarmsPresenterFromModel {
        void onDataChanged  (ArrayList<Alarm> alarms);
        void onAlarmAdded   (Alarm alarm);
        void onAlarmUpdated (Alarm alarm);
        void onAlarmRemoved (Alarm alarmCopy);
    }

    public interface IAlarmsModel {
        void addAlarm (Alarm alarm);
        void updateAlarm (Alarm alarm);
        void removeAlarm (Alarm alarm);
        Alarm getAlarm (long id);
        ArrayList<Alarm> getAllAlarms ();
    }
}