package com.rmm.clocksuite.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.rmm.clocksuite.R;

public class AlarmDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);

        long alarmId = getIntent().getLongExtra("alarmId", -1);
        TextView tvTest = findViewById(R.id.tvTest);

        tvTest.setText  (String.valueOf (alarmId));
    }
}
