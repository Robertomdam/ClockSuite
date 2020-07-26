package com.rmm.clocksuite.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rmm.clocksuite.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeZoneFragment extends Fragment {

    public TimeZoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_zone, container, false);
    }
}
