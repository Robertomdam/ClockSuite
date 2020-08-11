package com.rmm.clocksuite.view.fragments;

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
import com.rmm.clocksuite.presenter.timezones.ITimezonesContracts;
import com.rmm.clocksuite.presenter.timezones.TimezonesPresenter;
import com.rmm.clocksuite.retrofit.CountryData;
import com.rmm.clocksuite.view.adapters.TimezonesRecyclerAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimezoneFragment extends Fragment implements ITimezonesContracts.ITimezonesView {

    private ITimezonesContracts.ITimezonesPresenterFromView mPresenter;

    private TimezonesRecyclerAdapter mTimezonesRecyclerAdapter;

    private RecyclerView rvTimezones;
    private FloatingActionButton fabTimezones;

    public TimezoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_zone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new TimezonesPresenter (this);

        findViews (view);
        setupRecyclerView ();
        setupOnClickEvents ();
    }

    private void findViews (View view) {
        rvTimezones = view.findViewById(R.id.rvTimezones);
        fabTimezones= view.findViewById(R.id.fabTimezones);
    }

    private void setupOnClickEvents () {
        fabTimezones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTimezoneItem();
            }
        });
    }

    private void setupRecyclerView () {
        mTimezonesRecyclerAdapter = new TimezonesRecyclerAdapter (getContext());
        rvTimezones.setAdapter (mTimezonesRecyclerAdapter);
        rvTimezones.setLayoutManager (new LinearLayoutManager (getContext()));
    }

    private void addTimezoneItem () {
        mTimezonesRecyclerAdapter.addTimezoneItem ("Patata");
        mTimezonesRecyclerAdapter.notifyItemInserted (mTimezonesRecyclerAdapter.getItemCount());
//        mTimezonesRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onListCountriesTimezonesReceived(ArrayList<CountryData> countries) {
        mTimezonesRecyclerAdapter.updateCountriesTimezones(countries);
    }
}
