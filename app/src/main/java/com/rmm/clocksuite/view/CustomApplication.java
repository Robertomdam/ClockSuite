package com.rmm.clocksuite.view;

import android.app.Application;

import com.rmm.clocksuite.presenter.alarms.AlarmsPresenter;

/**
 * Custom class that inherits from Application.
 */
public class CustomApplication extends Application {

    /**
     * Initializes the general presenter of the alarms and the notification handler.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        AlarmsPresenter.getInstance().init(this);
        NotificationHandler.getInstance().init(this);
    }
}
