package com.rmm.clocksuite.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.rmm.clocksuite.R;

import java.util.Locale;

/**
 * Activity to manage the details of specific alarms.
 */
public class AlarmDetailsActivity extends AppCompatActivity {

    private NumberPicker   npHour;
    private NumberPicker npMinute;
    private ImageView    ivDelete;
    private ImageView      ivSave;

    /**
     * Finds all views and handles all the initialization and configuration needs of the
     * activity, such as on click event management, number pickers formatting or retrieving
     * bundle data.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);

        findViews ();
        setupNumberPickers ();
        setupOnClickEvents ();

        long alarmId = getIntent().getLongExtra("alarmId", -1);

        if (alarmId < 0)
            ivDelete.setVisibility(View.INVISIBLE);
    }

    /**
     * Finds all views of the activity.
     */
    private void findViews ()
    {
        npHour   = findViewById (R.id.npHour  );
        npMinute = findViewById (R.id.npMinute);
        ivDelete = findViewById (R.id.ivDelete);
          ivSave = findViewById (R.id.ivSave  );
    }

    /**
     * Sets a range of vales for both hours and minutes number pickers. Also creates a formatter
     * in order to show the time with two digits (like 08:05).
     */
    private void setupNumberPickers () {

          npHour.setMinValue (0);
          npHour.setMaxValue (23);
        npMinute.setMinValue (0);
        npMinute.setMaxValue (59);

        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {

            @Override
            public String format(int i) {
                return String.format(Locale.US, "%02d", i);
            }
        };

          npHour.setFormatter(formatter);
        npMinute.setFormatter(formatter);
    }

    /**
     * Sets up the on click events fot he necessary views.
     */
    private void setupOnClickEvents ()
    {
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    /**
     * Calls the presenter to delete the instance of the alarm and then go back to the tabbed activity.
     */
    private void delete () {
        // TODO Call the presenter in order to remove the current alarm
        goToTabbedActivity();
    }

    /**
     * Calls the presenter to save the instance of the alarm and the go back to the tabbed activity.
     */
    private void save () {
        // TODO Call the presenter to save the current alarm
        goToTabbedActivity();
    }

    /**
     * Navigates to TabbedActivity.
     */
    private void goToTabbedActivity () {
        Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
        startActivity (intent);
    }
}
