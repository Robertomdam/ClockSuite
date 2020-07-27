package com.rmm.clocksuite.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.rmm.clocksuite.R;
import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.presenter.AlarmsPresenter;
import com.rmm.clocksuite.presenter.IAlarmsContracts;
import com.rmm.clocksuite.view.views.CustomToggleTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Activity to manage the details of specific alarms.
 */
public class AlarmDetailsActivity extends AppCompatActivity implements IAlarmsContracts.IAlarmsDetailsView {

    private IAlarmsContracts.IAlarmsPresenterFromView mPresenter;

    private long   currentAlarmId;
    private Alarm    currentAlarm;

    private NumberPicker   npHour;
    private NumberPicker npMinute;
    private EditText       etNote;
    private ImageView    ivDelete;
    private ImageView      ivSave;

    private ArrayList<CustomToggleTextView> cttvDays = new ArrayList<>();

    /**
     * Finds all views and handles all the initialization and configuration needs of the
     * activity, such as on click event management, number pickers formatting or retrieving
     * bundle data.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);

        findViews ();
        setupNumberPickers ();
        setupOnClickEvents ();

        mPresenter = AlarmsPresenter.getInstance();
        mPresenter.registerView (this);

        currentAlarmId = getIntent().getLongExtra("alarmId", -1);

        if (currentAlarmId < 0) {
            ivDelete.setVisibility(View.INVISIBLE);
        }
        else {
            currentAlarm = mPresenter.getAlarm(currentAlarmId);
            loadData (currentAlarm);
        }
    }

    /**
     * Finds all views of the activity.
     */
    private void findViews ()
    {
        npHour   = findViewById (R.id.npHour  );
        npMinute = findViewById (R.id.npMinute);
          etNote = findViewById (R.id.etNote  );
        ivDelete = findViewById (R.id.ivDelete);
          ivSave = findViewById (R.id.ivSave  );

        LinearLayout v1 = findViewById(R.id.repeatWeekDaysLayout);
        LinearLayout v2 = findViewById(R.id.repeatWeekEndDaysLayout);

        for (int i = 0; i < v1.getChildCount(); i++) {
            View v = v1.getChildAt(i);
            if (v instanceof CustomToggleTextView)
                cttvDays.add ((CustomToggleTextView) v);
        }

        for (int i = 0; i < v2.getChildCount(); i++) {
            View v = v2.getChildAt(i);
            if (v instanceof CustomToggleTextView)
                cttvDays.add ((CustomToggleTextView) v);
        }
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
                saveAlarm ();
            }
        });
    }

    /**
     * Loads the data of an alarm in the corresponding fields.
     * @param alarm The alarm to take the data from.
     */
    private void loadData (Alarm alarm) {
        npHour.setValue (alarm.getHour ());
        npMinute.setValue (alarm.getMinute ());
        etNote.setText (alarm.getNote ());

        cttvDays.get(0).setIsSelected ( alarm.getRepeatMode ().mMonday );
        cttvDays.get(1).setIsSelected ( alarm.getRepeatMode ().mTuesday );
        cttvDays.get(2).setIsSelected ( alarm.getRepeatMode ().mWednesday );
        cttvDays.get(3).setIsSelected ( alarm.getRepeatMode ().mThursday );
        cttvDays.get(4).setIsSelected ( alarm.getRepeatMode ().mFriday );
        cttvDays.get(5).setIsSelected ( alarm.getRepeatMode ().mSaturday );
        cttvDays.get(6).setIsSelected ( alarm.getRepeatMode ().mSunday );

        Log.d("DEBUGGING", "Load Monday: " + alarm.getRepeatMode().mMonday);
    }

    /**
     * Gathers all the data in the layout views to create and return an alarm.
     * @return
     */
    private Alarm createAlarmFromViews ()
    {
        Alarm alarm = new Alarm();
        alarm.setNote (etNote.getText().toString());

        Calendar c = Calendar.getInstance();
        c.set (Calendar.HOUR_OF_DAY, npHour.getValue());
        c.set (Calendar.MINUTE, npMinute.getValue());
        alarm.setTime (c);

        alarm.getRepeatMode ().mMonday    = cttvDays.get(0).getIsSelected();
        alarm.getRepeatMode ().mTuesday   = cttvDays.get(1).getIsSelected();
        alarm.getRepeatMode ().mWednesday = cttvDays.get(2).getIsSelected();
        alarm.getRepeatMode ().mThursday  = cttvDays.get(3).getIsSelected();
        alarm.getRepeatMode ().mFriday    = cttvDays.get(4).getIsSelected();
        alarm.getRepeatMode ().mSaturday  = cttvDays.get(5).getIsSelected();
        alarm.getRepeatMode ().mSunday    = cttvDays.get(6).getIsSelected();

        return alarm;
    }

    /**
     * Calls the presenter to delete the instance of the alarm and then go back to the tabbed activity.
     */
    private void delete () {
        mPresenter.removeAlarm (currentAlarm);
        goToTabbedActivity();
    }

    /**
     * Checks whether the alarm already exists or not in order to call the presenter's method save or update.
     */
    private void saveAlarm () {
        if (currentAlarmId < 0)
            save ();
        else
            update ();
    }

    /**
     * Calls the presenter to save the instance of the alarm and the go back to the tabbed activity.
     */
    private void save () {
        Alarm alarm = createAlarmFromViews();
        mPresenter.addAlarm ( alarm );
        goToTabbedActivity();
    }

    private void update () {
        Alarm alarm = createAlarmFromViews();
        alarm.mId = currentAlarmId;
        mPresenter.updateAlarm ( alarm );
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
