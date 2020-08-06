package com.rmm.clocksuite.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rmm.clocksuite.R;
import com.rmm.clocksuite.entity.Alarm;
import com.rmm.clocksuite.presenter.alarms.AlarmsPresenter;
import com.rmm.clocksuite.presenter.alarms.IAlarmsContracts;
import com.rmm.clocksuite.view.activities.AlarmDetailsActivity;
import com.rmm.clocksuite.view.adapters.AlarmsRecylerAdapter;

import java.util.ArrayList;


/**
 * Custom Fragment class to handle Alarms by using a RecyclerView.
 */
public class AlarmsFragment extends Fragment implements IAlarmsContracts.IAlarmsHandlerView, AlarmsRecylerAdapter.IAlarmRecyclerEventListener {

    private IAlarmsContracts.IAlarmsPresenterFromView mPresenter;

    private AlarmsRecylerAdapter alarmsRecylerAdapter;
    private ArrayList<Alarm> listAlarms;

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
     * Tells the presenter that this is the current view.
     */
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setCurrentView(this);
    }

    /**
     * Registers the view on the presenter and gets the list of alarms.
     * Configures the recycler view and the floating action button.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = AlarmsPresenter.getInstance();
        mPresenter.registerView (this);
        listAlarms = mPresenter.getAllAlarms();

        findViews (view);
        setupRecycler (view, listAlarms);
        setupFloatingActionButton();
    }

    /**
     * Goes to the alarm details activity.
     * @param position
     */
    @Override
    public void onAlarmItemSelected (int position) {
        goActivityAlarmDetail (listAlarms.get(position).getId());
    }

    /**
     * Calls the presenter to save the new state of the alarm
     * @param position
     * @param state
     */
    @Override
    public void onAlarmSwitchStateChanged(int position, boolean state) {

        Alarm alarm = listAlarms.get(position);
        alarm.setEnabled (state);
        mPresenter.updateAlarm (alarm);

//        Toast.makeText (
//                getContext(),
//                "Alarm " + position + " changed state to " + (state ? "true" : "false"),
//                Toast.LENGTH_SHORT
//        ).show();
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
    private void setupRecycler (View view, ArrayList<Alarm> alarms)
    {
        alarmsRecylerAdapter = new AlarmsRecylerAdapter (this);
        alarmsRecylerAdapter.setData (alarms);
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
        Intent intent = new Intent (getContext(), AlarmDetailsActivity.class);
        intent.putExtra ("alarmId", alarmId);
        startActivity (intent);
    }

    /**
     * Refreshes the data of the recycler view.
     * @param alarms
     */
    @Override
    public void onDataChanged(ArrayList<Alarm> alarms) {
        listAlarms = alarms;
        alarmsRecylerAdapter.setData (listAlarms);
        alarmsRecylerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAlarmAdded(Alarm alarm) {

    }

    @Override
    public void onAlarmUpdated(Alarm alarm) {

    }

    @Override
    public void onAlarmRemoved(Alarm alarmCopy) {

    }
}
