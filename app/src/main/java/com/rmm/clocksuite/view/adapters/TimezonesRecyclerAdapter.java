package com.rmm.clocksuite.view.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmm.clocksuite.R;

import java.util.ArrayList;
import java.util.Locale;

public class TimezonesRecyclerAdapter extends RecyclerView.Adapter<TimezonesRecyclerAdapter.TimezonesViewHolder> {

    private ArrayList<String> mListTimezones;

    public TimezonesRecyclerAdapter () {
        mListTimezones = new ArrayList<>();
    }

    public void addTimezoneItem (String timezone) {
        mListTimezones.add(timezone);
        Log.d ("DEBUGGING", "Timezones list: " + mListTimezones.size());
    }

    public void setListTimezones (ArrayList<String> listTimezones) { mListTimezones = listTimezones; }

    @NonNull
    @Override
    public TimezonesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.viewholder_timezone, parent, false);
        return new TimezonesViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimezonesViewHolder holder, int position) {
        holder.spTimezones.setSelection (0);
        holder.tvTimezonesTime.setText ( String.format (Locale.US, "%02d:%02d:%02d", 0, 0, 0) );
    }

    @Override
    public int getItemCount() {
        return mListTimezones.size();
    }

    class TimezonesViewHolder extends RecyclerView.ViewHolder {

        private Spinner spTimezones;
        private TextView tvTimezonesTime;

        private TimezonesViewHolder(@NonNull View itemView) {
            super(itemView);

            spTimezones     = itemView.findViewById (R.id.spTimezones);
            tvTimezonesTime = itemView.findViewById (R.id.tvTimezonesTime);

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<> (itemView.getContext(), android.R.layout.simple_spinner_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerAdapter.add ("England");
            spinnerAdapter.add ("Spain");
            spinnerAdapter.add ("Italy");

            spTimezones.setAdapter (spinnerAdapter);
        }
    }
}
