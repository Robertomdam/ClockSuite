package com.rmm.clocksuite.presenter;

import android.content.Context;

import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.usecase.AlarmsModel;

import java.util.ArrayList;

public class AlarmsPresenter implements IAlarmsContracts.IAlarmsPresenterFromView, IAlarmsContracts.IAlarmsPresenterFromModel {

    private IAlarmsContracts.IAlarmsHandlerPresenter mPresenterAlarmHandler;
    private IAlarmsContracts.IAlarmsDetailsPresenter mPresenterAlarmDetails;

    private IAlarmsContracts.IAlarmsModel mModel;

    public void init (Context appContext) {
        mModel = new AlarmsModel(appContext);
    }

    private static AlarmsPresenter mInstance;
    public  static AlarmsPresenter getInstance () {
        if (mInstance == null)
            mInstance = new AlarmsPresenter ();

        return mInstance;
    }

    @Override
    public ArrayList<Alarm> getAllAlarms() {
//        // (Testing purposes) Just to populate some database data after getting alarms
//        Alarm alarm1 = new Alarm();
//        alarm1.setNote("testNote");
//        alarm1.setTime (Calendar.getInstance());
//        alarm1.setEnabled (true);
//
//        Alarm alarm2 = new Alarm();
//        alarm2.setNote("testtesttest");
//        alarm2.setTime (Calendar.getInstance());
//        alarm2.setEnabled (false);
//
//        mModel.addAlarm (alarm1);
//        mModel.addAlarm (alarm2);

        return mModel.getAllAlarms();
    }

    @Override
    public void registerView(IAlarmsContracts.IBaseAlarmsView view) {

        if (view instanceof IAlarmsContracts.IAlarmsHandlerView)
            mPresenterAlarmHandler = new AlarmsHandlerPresenter ((IAlarmsContracts.IAlarmsHandlerView) view);
        else if (view instanceof IAlarmsContracts.IAlarmsDetailsView)
            mPresenterAlarmDetails = new AlarmsDetailsPresenter ((IAlarmsContracts.IAlarmsDetailsView) view);
    }

    @Override
    public void addAlarm (Alarm alarm) {
        mModel.addAlarm (alarm);
    }

    @Override
    public void updateAlarm(Alarm alarm) {
        mModel.updateAlarm(alarm);
    }

    @Override
    public void removeAlarm (Alarm alarm) {
        mModel.removeAlarm (alarm.getId());
    }

    @Override
    public Alarm getAlarm (long id) {
        return mModel.getAlarm(id);
    }

}
