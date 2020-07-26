package com.rmm.clocksuite.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rmm.clocksuite.R;
import com.rmm.clocksuite.database.DBManager;
import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.view.activities.AlarmDetailActivity;
import com.rmm.clocksuite.view.adapters.AlarmsRecylerAdapter;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Custom Fragment class to handle Alarms by using a RecyclerView.
 */
public class AlarmsFragment extends Fragment implements AlarmsRecylerAdapter.IAlarmRecyclerEventListener {

    private AlarmsRecylerAdapter alarmsRecylerAdapter;

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    /**
     * Construct a fragment.
     */
    public AlarmsFragment() {
        // Required empty public constructor
    }

    /**
     * Inflates the view.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarms, container, false);
    }

    /**
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews (view);
        setupRecycler (view );
        setupFloatingActionButton();
    }

    // TODO Comment
    @Override
    public void onAlarmItemSelected(int position) {

        // TODO Change temp implementation for a real one when model and presenter are implemented.
        long alarmId = -1;
//        alarmId = mPresenter.getAlarms().get (position).getId ();
        alarmId = position;

        DBManager dbManager = new DBManager (getActivity().getApplicationContext());

        Calendar cal = Calendar.getInstance();
        cal.set (Calendar.HOUR_OF_DAY, 15);
        cal.set (Calendar.MINUTE, 24);

        Alarm alarm = new Alarm();
        alarm.setNote("testNote");
        alarm.setTime (cal);

        dbManager.insertAlarm (alarm);
        ArrayList<Alarm> alarms = dbManager.getAllAlarms();

        Log.d("DEBUGGING", "num alarms: " + alarms.size());
        for (int i = 0; i < alarms.size(); i++) {
            Log.d("DEBUGGING", "("+i+") Id: ): " + alarms.get(i).getId());
            Log.d("DEBUGGING", "("+i+") Time: ): " + alarms.get(i).getHour() + ":" + alarms.get(i).getMinute());

        }

//        goActivityAlarmDetail (alarmId);
//        Toast.makeText(getContext(), "Alarm " + alarmId + " was selected!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Finds all views in the fragment.
     * @param view The root view of the fragment.
     */
    private void findViews (View view)
    {
        recyclerView = view.findViewById (R.id.rvAlarms);
        fab          = view.findViewById (R.id.fabAlarms);
    }

    /**
     * Creates a new AlarmsRecyclerAdapter and sets it to the recycler view.
     * Also creates a LinearLayoutManager for it.
     * @param view The root view of the fragment.
     */
    private void setupRecycler (View view)
    {
        alarmsRecylerAdapter = new AlarmsRecylerAdapter (this);
        recyclerView.setLayoutManager( new LinearLayoutManager (view.getContext()) );
        recyclerView.setAdapter (alarmsRecylerAdapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL));
    }

    /**
     * Sets up a onCLickListener so that it calls to change to the AlarmDetailActivity.
     */
    private void setupFloatingActionButton ()
    {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivityAlarmDetail (-1);
            }
        });
    }

    /**
     * Starts the activity AlarmDetailActivity.
     */
    private void goActivityAlarmDetail (long alarmId)
    {
        Intent intent = new Intent (getContext(), AlarmDetailActivity.class);
        intent.putExtra ("alarmId", alarmId);
        startActivity (intent);
    }
}