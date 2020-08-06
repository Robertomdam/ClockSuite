package com.rmm.clocksuite.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.rmm.clocksuite.R;
import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.presenter.alarms.AlarmsPresenter;
import com.rmm.clocksuite.view.AlarmFiringHandler;
import com.rmm.clocksuite.view.NotificationHandler;

import java.io.IOException;

/**
 * This activity represents a visual alarm, so the user can interact with it by stopping or snoozing it.
 */
public class AlarmActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private Button btStop;
    private Button btSnooze;

    private Alarm alarm;

    /**
     * Gathers the triggered alarm data and setups the functionality of the buttons.
     * Also plays the ringtone for the alarm and sends a notification.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // Gets the data of the triggered alarm
        final long id = getIntent().getLongExtra ("alarmId", -1);
        alarm = AlarmsPresenter.getInstance().getAlarm (id);

        // Setting up the views and buttons events
        findViews();
        setupOnClickEvents();

        // Playing the ringtone
        playAlarmMusic ();

        // Sending the notification
        NotificationHandler.getInstance().sendNotification (
                (int) alarm.getId(),
                "Alarm " + alarm.getHour() + ":" + alarm.getMinute(),
                alarm.getNote()
        );
    }

    /**
     * Stops the ringtone and releases the media player. Also cancels the notification of the alarm.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;

        NotificationHandler.getInstance().cancelNotification ((int) alarm.getId());
    }

    /**
     * Finds all views in the activity.
     */
    private void findViews () {
        btStop   = findViewById(R.id.btStop);
        btSnooze = findViewById(R.id.btSnooze);
    }

    /**
     * Sets up the on click events for the two buttons of the activity.
     */
    private void setupOnClickEvents () {
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmFiringHandler.getInstance().rescheduleAlarm (getApplicationContext(), alarm);
                finish();
            }
        });

        btSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmFiringHandler.getInstance().snoozeAlarm (getApplicationContext(), alarm,  5);
                finish();
            }
        });
    }

    /**
     * Creates a MediaPlayer and plays the default alert alarm ringtone.
     */
    private void playAlarmMusic () {

        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource (getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
