package com.rmm.clocksuite.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.presenter.alarms.AlarmsPresenter;

import java.util.ArrayList;

/**
 * BroadcastReceiver that accomplishes the rescheduling task of all the alarms in the app.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {

    /**
     * Gets all the alarms in the system and reschedules them.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Clock Suite: Phone boot", Toast.LENGTH_LONG).show();

        AlarmFiringHandler alarmFiringHandler = AlarmFiringHandler.getInstance();

        // Query to the db (from presenter) so i cant get all the alarms in the system
        AlarmsPresenter alarmsPresenter = AlarmsPresenter.getInstance();
        ArrayList<Alarm> alarms = alarmsPresenter.getAllAlarms();

        // Once I got all of them, loop through them and setScheduledAlarm on every one
        for (Alarm alarm : alarms) {
            alarmFiringHandler.setScheduledAlarm (context, alarm);
        }
    }
}
