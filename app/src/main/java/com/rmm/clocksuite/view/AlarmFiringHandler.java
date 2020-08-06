package com.rmm.clocksuite.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.entity.AlarmRepeatMode;
import com.rmm.clocksuite.view.activities.AlarmActivity;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Helper class that manages the Android alarms services.
 */
public class AlarmFiringHandler {

    static {
        instance = new AlarmFiringHandler ();
    }

    private AlarmFiringHandler () {}
    private static AlarmFiringHandler instance;

    public static AlarmFiringHandler getInstance() {
        return instance;
    }

    /**
     * Retrieves a list that represents the number of the days of week that are activated for an specific alarm mode.
     * @param alarmRepeatMode The alarm mode.
     * @return The list that represents the number of the days of week.
     */
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

    /**
     * Schedules an alarm that will trigger an activity. This creates one Android alarm per week day.
     * @param appContext The application context.
     * @param alarm The alarm to be scheduled.
     */
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

    /**
     * Sets an Android alarm. It fixes the time and day, so when an introduced alarm date is previous
     * to the current date, the introduced alarm will be converted to the corresponding time for
     * the next week.
     * The id of the Android alarm will correspond with the alarm id but, in order to get 7 different
     * and unique ids for the same alarm in every day of the week, I added 100000 * numWeekDay, so
     * every day of the week for an specific alarm. Example: Alarm with id 22 that has to be
     * triggered on Monday(2) and Tuesday(3) will have 200022 for Monday and 300022 for Tuesday.
     * This will potentially cause problems in the future if the id of the alarms reach a number
     * bigger than 99999.
     * @param appContext The application context.
     * @param intent The intent that will be used for the Android alarm.
     * @param alarmId The id of the alarm.
     * @param alarmTime The time of the alarm.
     * @param numWeekDay The number of the week this Android alarm will be set to.
     */
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
        Log.d("DEBUGGING", "Day of week: " + "Selected: " + numWeekDay + " Current: " + currentWeekDay  + " - Diff: " + differenceDays);

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
        if(differenceDays <= 0) {
            Log.d("DEBUGGING", "AlarmFiringHandler - Same day");
            if (alarmCalendar.before(currentTime)) {
                alarmCalendar.add(Calendar.DAY_OF_WEEK, 7);
                Log.d("DEBUGGING", "AlarmFiringHandler - Fixed day");
            }
        }

        Log.d("DEBUGGING", "AlarmFiringHandler - Alarm  : " + alarmCalendar.toString());
        Log.d("DEBUGGING", "AlarmFiringHandler - Current: " + currentTime.toString());

        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(ALARM_SERVICE);
        alarmManager.setExact (AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), pendingIntent);
    }

    /**
     * Schedules an existing alarm.
     * @param appContext The application context.
     * @param alarm The updated alarm.
     */
    public void updateScheduledAlarm(Context appContext, Alarm alarm) {
        setScheduledAlarm (appContext, alarm);
    }

    /**
     * Removes an existing alarm, that removes all the android alarms set by every day of the week.
     * @param appContext The application context.
     * @param alarm The alarm to remove.
     */
    public void removeScheduledAlarm (Context appContext, Alarm alarm) {

        Intent intent = new Intent (appContext, AlarmActivity.class);

        // Gets a list that represents days as integers in order to set and alarm to them
        ArrayList<Integer> repeatDaysList = getRepeatDaysFrom (alarm.getAlarmRepeatMode());

        // If all the days where set to false, then the alarm will be fired every day
        if (repeatDaysList.size() == 0) for (int weekDay = 1; weekDay <= 7; weekDay++) repeatDaysList.add (weekDay);

        // Sets an alarm in every day included in the list
        for (int i = 0; i < repeatDaysList.size (); i++) {
            removeAlarm (appContext, intent, alarm.getId(), repeatDaysList.get(i));
        }
    }

    /**
     * Removes an existing android alarm.
     * @param appContext The application context.
     * @param intent The intent to use for the Android alarm.
     * @param alarmId The id of the alarm.
     * @param numWeekDay The number of the day in the week.
     */
    private void removeAlarm (Context appContext, Intent intent, long alarmId, int numWeekDay) {

        int pendingId = (int) alarmId + ( 100000 * numWeekDay );
        PendingIntent pendingIntent = PendingIntent.getActivity (appContext, pendingId, intent, PendingIntent.FLAG_NO_CREATE);

        if (pendingIntent != null) {
            Log.d("DEBUGGING", "Go remove an alarm");
            AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(ALARM_SERVICE);
            alarmManager.cancel (pendingIntent);
        }
    }

    /**
     * Creates a new temporal alarm that will be trigger after the specified time in minutes.
     * @param appContext The application context.
     * @param alarm The alarm to snooze.
     * @param minutes The time to wait until the alarm triggers.
     */
    public void snoozeAlarm (Context appContext, Alarm alarm, int minutes) {
        int milliseconds = 1000 * 60 * minutes;

        Intent intent = new Intent (appContext, AlarmActivity.class);
        intent.putExtra ("alarmId", alarm.getId());

        PendingIntent pendingIntent = PendingIntent.getActivity(appContext, -1, intent, 0);

        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(ALARM_SERVICE);
        alarmManager.set (AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + milliseconds , pendingIntent);
    }

    /**
     * Schedules an alarm.
     * @param appContext The application context.
     * @param alarm The alarm to reschedule.
     */
    public void rescheduleAlarm (Context appContext, Alarm alarm) {
        setScheduledAlarm (appContext, alarm);
    }
}

