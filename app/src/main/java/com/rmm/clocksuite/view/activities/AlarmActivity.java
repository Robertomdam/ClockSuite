package com.rmm.clocksuite.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rmm.clocksuite.R;
import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.presenter.AlarmsPresenter;
import com.rmm.clocksuite.view.AlarmFiringHandler;

import java.io.IOException;

public class AlarmActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "0";
    private MediaPlayer mediaPlayer;

    private Button btStop;
    private Button btSnooze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        final long id = getIntent().getLongExtra ("alarmId", -1);

//        Toast.makeText(getApplicationContext(), "Alarm id: " + id, Toast.LENGTH_LONG).show();

        btStop = findViewById(R.id.btStop);
        btSnooze = findViewById(R.id.btSnooze);

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmFiringHandler.getInstance().rescheduleAlarm (getApplicationContext(), id);

                finish();
            }
        });

        btSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmFiringHandler.getInstance().snoozeAlarm (getApplicationContext(), 5);

                finish();
            }
        });

        playAlarmMusic ();

        Alarm alarm = AlarmsPresenter.getInstance().getAlarm (id);
        sendNotification (alarm);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;

        NotificationManagerCompat.from (this).cancel (0);
    }

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

    private void sendNotification (Alarm alarm) {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from (this);

        // create channel
        createNotificationChannel (notificationManager);

        // create notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder (this, CHANNEL_ID);
        notificationBuilder.setContentTitle ("Alarm " + alarm.getHour() + ":" + alarm.getMinute());
        notificationBuilder.setContentText (alarm.getNote());
        notificationBuilder.setSmallIcon (R.drawable.ic_alarm_small);

        // send notification
        Notification notification = notificationBuilder.build();
        notificationManager.notify (0, notification);
    }

    private void createNotificationChannel (NotificationManagerCompat notificationManagerCompat) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Default channel";
            String description = "Default channel for notifications";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            notificationManagerCompat.createNotificationChannel(channel);
        }
    }
}
