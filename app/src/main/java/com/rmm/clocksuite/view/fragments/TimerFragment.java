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
public class TimerFragment extends Fragment {

    public TimerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_timer, container, false);
    }
}
