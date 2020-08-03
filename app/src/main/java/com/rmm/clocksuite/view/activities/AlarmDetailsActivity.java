package com.rmm.clocksuite.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.rmm.clocksuite.R;
import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.entity.AlarmRepeatMode;
import com.rmm.clocksuite.presenter.AlarmsPresenter;
import com.rmm.clocksuite.presenter.IAlarmsContracts;
import com.rmm.clocksuite.view.AlarmFiringHandler;
import com.rmm.clocksuite.view.views.CustomToggleTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Activity to manage the details of specific alarms.
 */
public class AlarmDetailsActivity extends AppCompatActivity implements IAlarmsContracts.IAlarmsDetailsView {

    private IAlarmsContracts.IAlarmsPresenterFromView mPresenter;

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

        // Handles some information about the current alarm that the activity will be managing
        long currentAlarmId = getIntent().getLongExtra("alarmId", -1);

        if (currentAlarmId < 0) { // New alarm
            ivDelete.setVisibility (View.INVISIBLE);
        }
        else {  // Already existing alarm
            currentAlarm = mPresenter.getAlarm (currentAlarmId);
            loadFieldsDataFromAlarm(currentAlarm);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.setCurrentView(this);

        if (currentAlarm != null)
            return;

        // Sets the number pickers values to the current time
        Calendar calendar = Calendar.getInstance();

          npHour.setValue (calendar.get(Calendar.HOUR_OF_DAY));
        npMinute.setValue (calendar.get(Calendar.MINUTE));
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

        // Find all Toggle TextView views
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
                onSaveAlarm();
            }
        });
    }

    /**
     * Loads the data of an alarm in the corresponding fields.
     * @param alarm The alarm to take the data from.
     */
    private void loadFieldsDataFromAlarm (Alarm alarm) {

        // Number pickers that represent the time
          npHour.setValue (alarm.getHour   ());
        npMinute.setValue (alarm.getMinute ());

        // Edit text that represent the note
          etNote.setText  (alarm.getNote   ());

        // Toggle Text views that represents the repeat mode
        cttvDays.get(0).setIsSelected ( alarm.getAlarmRepeatMode().mMonday    );
        cttvDays.get(1).setIsSelected ( alarm.getAlarmRepeatMode().mTuesday   );
        cttvDays.get(2).setIsSelected ( alarm.getAlarmRepeatMode().mWednesday );
        cttvDays.get(3).setIsSelected ( alarm.getAlarmRepeatMode().mThursday  );
        cttvDays.get(4).setIsSelected ( alarm.getAlarmRepeatMode().mFriday    );
        cttvDays.get(5).setIsSelected ( alarm.getAlarmRepeatMode().mSaturday  );
        cttvDays.get(6).setIsSelected ( alarm.getAlarmRepeatMode().mSunday    );

//        Log.d("DEBUGGING", "Load Monday: " + alarm.getRepeatMode().mMonday);
    }

    /**
     * Gathers all the data in the layout views to create and return an alarm.
     * @return The recently created alarm.
     */
    private Alarm createAlarmFromViews ()
    {
        // Note
        String note = etNote.getText().toString();

        // Time
        Calendar time = Calendar.getInstance();
        time.set (Calendar.HOUR_OF_DAY, npHour.getValue());
        time.set (Calendar.MINUTE, npMinute.getValue());
        time.set (Calendar.SECOND, 0);

        // Repeat mode
        AlarmRepeatMode repeatMode = new AlarmRepeatMode();
        repeatMode.mMonday    = cttvDays.get(0).getIsSelected();
        repeatMode.mTuesday   = cttvDays.get(1).getIsSelected();
        repeatMode.mWednesday = cttvDays.get(2).getIsSelected();
        repeatMode.mThursday  = cttvDays.get(3).getIsSelected();
        repeatMode.mFriday    = cttvDays.get(4).getIsSelected();
        repeatMode.mSaturday  = cttvDays.get(5).getIsSelected();
        repeatMode.mSunday    = cttvDays.get(6).getIsSelected();

        return new Alarm (note, time, repeatMode);
    }

    /**
     * Calls the presenter to delete the instance of the alarm and then go back to the tabbed activity.
     */
    private void delete () {
        if (currentAlarm != null)
            mPresenter.removeAlarm(currentAlarm);

        goToTabbedActivity();
    }

    /**
     * Checks whether the alarm already exists or not in order to call the presenter's method save or update.
     */
    private void onSaveAlarm() {
        if (currentAlarm != null)
            updateAlarm ();
        else
            saveAlarm ();
    }

    /**
     * Calls the presenter to save the instance of the alarm and the go back to the tabbed activity.
     */
    private void saveAlarm () {
        Alarm alarm = createAlarmFromViews();

        mPresenter.addAlarm ( alarm );

        goToTabbedActivity();
    }

    /**
     * Calls the presenter to update the existing alarm with the new values.
     */
    private void updateAlarm () {
        Alarm alarm = createAlarmFromViews();
        alarm.setId (currentAlarm.getId());
        alarm.setEnabled (true);

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

    @Override
    public void onDataChanged(ArrayList<Alarm> alarms) {

    }

    @Override
    public void onAlarmAdded(Alarm alarm) {
        Log.d ("DEBUGGING", "added: " + alarm.getId());

        AlarmFiringHandler alarmFiringHandler = AlarmFiringHandler.getInstance();
        alarmFiringHandler.setScheduledAlarm (getApplicationContext(), alarm);
    }

    @Override
    public void onAlarmUpdated(Alarm alarm) {
        Log.d ("DEBUGGING", "update: " + alarm.getId() + " - enabled: " + alarm.getEnabled() );

        AlarmFiringHandler alarmFiringHandler = AlarmFiringHandler.getInstance();
        if (alarm.getEnabled())
            alarmFiringHandler.updateScheduledAlarm (getApplicationContext(), alarm);
        else
            alarmFiringHandler.removeScheduledAlarm (getApplicationContext(), alarm);
    }

    @Override
    public void onAlarmRemoved(Alarm alarmCopy) {
        Log.d ("DEBUGGING", "remove: " + alarmCopy.getId());

        if (currentAlarm != null) {
            AlarmFiringHandler alarmFiringHandler = AlarmFiringHandler.getInstance();
            alarmFiringHandler.removeScheduledAlarm(getApplicationContext(), currentAlarm);
        }
    }
}
