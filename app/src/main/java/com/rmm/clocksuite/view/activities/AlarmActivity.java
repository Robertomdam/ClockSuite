package com.rmm.clocksuite.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rmm.clocksuite.R;
import com.rmm.clocksuite.view.AlarmFiringHandler;

import java.io.IOException;

public class AlarmActivity extends AppCompatActivity {

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

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource (getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
