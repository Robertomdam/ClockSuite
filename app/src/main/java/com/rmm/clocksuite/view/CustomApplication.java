package com.rmm.clocksuite.view;

import android.app.Application;

import com.rmm.clocksuite.presenter.AlarmsPresenter;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AlarmsPresenter.getInstance().init(this);
    }
}
