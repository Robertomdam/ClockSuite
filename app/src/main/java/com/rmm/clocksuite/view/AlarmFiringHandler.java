package com.rmm.clocksuite.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.entity.AlarmRepeatMode;
import com.rmm.clocksuite.presenter.AlarmsPresenter;
import com.rmm.clocksuite.view.activities.AlarmActivity;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmFiringHandler {

    static {
        instance = new AlarmFiringHandler ();
        Log.d("DEBUGGING", "static initializer AlarmFiringHandler");
    }

    private AlarmFiringHandler () {}
    private static AlarmFiringHandler instance;

    public static AlarmFiringHandler getInstance() {
        return instance;
    }

    private ArrayList<Integer> getRepeatDaysFrom (AlarmRepeatMode alarmRepeatMode) {
        ArrayList<Integer> repeatDaysList = new ArrayList<>();

        if ( alarmRepeatMode.mMonday    ) repeatDaysList.add (Calendar.MONDAY     );
        if ( alarmRepeatMode.mTuesday   ) repeatDaysList.add (Calendar.TUESDAY    );
        if ( alarmRepeatMode.mWednesday ) repeatDaysList.add (Calendar.WEDNESDAY  );
        if ( alarmRepeatMode.mThursday  ) repeatDaysList.add (Calendar.THURSDAY   );
        if ( alarmRepeatMode.mFriday    ) repeatDaysList.add (Calendar.FRIDAY     );
        if ( alarmRepeatMode.mSaturday  ) repeatDaysList.add (Calendar.SATURDAY   );
        if ( alarmRepeatMode.mSunday    ) repeatDaysList.add (Calendar.SUNDAY     );

        return repeatDaysList;
    }

    public void setScheduledAlarm (Context appContext, Alarm alarm) {
        Log.d("DEBUGGING", "AlarmFiringHandler - (" + alarm.getId() + ") Setting up alarm");

        Intent intent = new Intent (appContext, AlarmActivity.class);
        intent.putExtra ("alarmId", alarm.getId());

        // Gets a list that represents days as integers in order to set and alarm to them
        ArrayList<Integer> repeatDaysList = getRepeatDaysFrom (alarm.getAlarmRepeatMode());

        // If all the days where set to false, then the alarm will be fired every day
        if (repeatDaysList.size() == 0) for (int weekDay = 1; weekDay <= 7; weekDay++) repeatDaysList.add (weekDay);

        // Sets an alarm in every day included in the list
        for (int i = 0; i < repeatDaysList.size (); i++) {
            setAlarm (appContext, intent, alarm.getId(), alarm.getTime(), repeatDaysList.get(i));
        }
    }

    private void setAlarm (Context appContext, Intent intent, long alarmId, Calendar alarmTime, int numWeekDay) {

        // Quick and easy way to get multiple different ids for every alarm, so an Alarm object
        // that has id: 11 will have multiple AlarmManager's alarms with different unique ids
        // representing the days of the week, like: 700011 for Saturdays or 200011 for Mondays
        // This can potentially create problems when a lot of alarms have been created, so I will
        // have to look for another solution in the future.
        int pendingId = (int) alarmId + ( 100000 * numWeekDay );
        PendingIntent pendingIntent = PendingIntent.getActivity (appContext, pendingId, intent, 0);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast (appContext, 0, intent, 0);

        Log.d("DEBUGGING", "AlarmFiringHandler - (" + pendingId + ") Alarm set up");

        // Fixing day
        // Alarms time (stored as a calendar) represents only time, so here I'm adding the day of week
        // to the alarm based on the user week of day selection.
        Calendar currentTime = Calendar.getInstance ();
        int currentWeekDay = currentTime.get(Calendar.DAY_OF_WEEK);
        int differenceDays = numWeekDay - currentWeekDay;
        Log.d("DEBUGGING", "Day of week current: " + currentWeekDay + " - Diff: " + differenceDays);

        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.set (Calendar.HOUR_OF_DAY , alarmTime.get (Calendar.HOUR_OF_DAY ));
        alarmCalendar.set (Calendar.MINUTE      , alarmTime.get (Calendar.MINUTE      ));
        alarmCalendar.set (Calendar.SECOND      , alarmTime.get (Calendar.SECOND      ));
        alarmCalendar.set (Calendar.MILLISECOND , alarmTime.get (Calendar.MILLISECOND ));

        if (differenceDays != 0)
            alarmCalendar.add (Calendar.DAY_OF_WEEK, differenceDays);

        // Fix time
        // If the user has selected the same day as current, then we have to compare the calendar
        // using the time, so if the time has already past, the alarm will have to be set for the next week
        {
            if(differenceDays == 0) {
                Log.d("DEBUGGING", "AlarmFiringHandler - Same day");
                if (alarmCalendar.before(currentTime)) {
                    alarmCalendar.add(Calendar.DAY_OF_WEEK, 7);
                    Log.d("DEBUGGING", "AlarmFiringHandler - Fixed day");
                }
            }
        }

        Log.d("DEBUGGING", "AlarmFiringHandler - Alarm  : " + alarmCalendar.toString());
        Log.d("DEBUGGING", "AlarmFiringHandler - Current: " + currentTime.toString());

        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(ALARM_SERVICE);
//        alarmManager.setInexactRepeating (AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        alarmManager.setExact (AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), pendingIntent);
    }

    public void updateScheduledAlarm(Context appContext, Alarm alarm) {
        setScheduledAlarm (appContext, alarm);
    }

    public void removeScheduledAlarm (Context appContext, Alarm alarm) {

        Intent intent = new Intent (appContext, AlarmActivity.class);

        // Gets a list that represents days as integers in order to set and alarm to them
        ArrayList<Integer> repeatDaysList = getRepeatDaysFrom (alarm.getAlarmRepeatMode());

        // If all the days where set to false, then the alarm will be fired every day
        if (repeatDaysList.size() == 0) for (int weekDay = 1; weekDay <= 7; weekDay++) repeatDaysList.add (weekDay);

        // Sets an alarm in every day included in the list
        for (int i = 0; i < repeatDaysList.size (); i++) {
            removeAlarm (appContext, intent, alarm.getId(), alarm.getTime(), repeatDaysList.get(i));
        }
    }

    private void removeAlarm (Context appContext, Intent intent, long alarmId, Calendar alarmTime, int numWeekDay) {

        int pendingId = (int) alarmId + ( 100000 * numWeekDay );
        PendingIntent pendingIntent = PendingIntent.getActivity (appContext, pendingId, intent, PendingIntent.FLAG_NO_CREATE);

        if (pendingIntent != null) {
            Log.d("DEBUGGING", "Go remove an alarm");
            AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(ALARM_SERVICE);
            alarmManager.cancel (pendingIntent);
        }
    }

    public void snoozeAlarm (Context appContext, int minutes) {
        int milliseconds = 1000 * 60 * minutes;

        Intent intent = new Intent (appContext, AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(appContext, -1, intent, 0);

        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(ALARM_SERVICE);
        alarmManager.set (AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + milliseconds , pendingIntent);
    }

    public void rescheduleAlarm (Context appContext, long alarmId) {
        AlarmsPresenter presenter = AlarmsPresenter.getInstance();
        Alarm alarm = presenter.getAlarm (alarmId);

        setScheduledAlarm (appContext, alarm);
    }
}

