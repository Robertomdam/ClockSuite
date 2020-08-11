package com.rmm.clocksuite.view.adapters;

import android.content.Context;
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
import com.rmm.clocksuite.retrofit.CountryData;

import java.util.ArrayList;
import java.util.Locale;

public class TimezonesRecyclerAdapter extends RecyclerView.Adapter<TimezonesRecyclerAdapter.TimezonesViewHolder> {

    private Context mContext;
    private ArrayList<String> mListTimezones;
    private ArrayList<TimezonesViewHolder> mTimezonesViewHolders;

    private ArrayList<CountryData> mTimezoneCountiesData;

    public TimezonesRecyclerAdapter (@NonNull Context context) {
        mContext = context;
        mListTimezones = new ArrayList<>();
        mTimezonesViewHolders = new ArrayList<>();
    }

    public void addTimezoneItem (String timezone) {
        mListTimezones.add(timezone);
    }

    public void setListTimezones (ArrayList<String> listTimezones) { mListTimezones = listTimezones; }

    @NonNull
    @Override
    public TimezonesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.viewholder_timezone, parent, false);
        TimezonesViewHolder v = new TimezonesViewHolder (view);
        v.init();
        mTimezonesViewHolders.add (v);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull TimezonesViewHolder holder, int position) {
        mTimezonesViewHolders.get (position).setCountriesData (mTimezoneCountiesData);
        holder.updateSpinner();

        holder.spTimezones.setSelection (0);
        holder.tvTimezonesTime.setText ( String.format (Locale.US, "%02d:%02d:%02d", 0, 0, 0) );
    }

    @Override
    public int getItemCount() {
        return mListTimezones.size();
    }

    public void updateCountriesTimezones (ArrayList<CountryData> data) {
        mTimezoneCountiesData = data;

        // Updates the existing ones
        for (int i = 0; i < mTimezonesViewHolders.size(); i++) {
            mTimezonesViewHolders.get (i).setCountriesData (mTimezoneCountiesData);
        }
    }

    class TimezonesViewHolder extends RecyclerView.ViewHolder {

        private TimezonesSpinnerAdapter mSpinnerAdapter;

        private Spinner spTimezones;
        private TextView tvTimezonesTime;

        private TimezonesViewHolder(@NonNull View itemView) {
            super(itemView);

            spTimezones     = itemView.findViewById (R.id.spTimezones);
            tvTimezonesTime = itemView.findViewById (R.id.tvTimezonesTime);
        }

        private void init () {
            mSpinnerAdapter = new TimezonesSpinnerAdapter (mContext, R.layout.spinner_dropdown_item_timezones, R.id.tvSpItem);
            mSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_timezones);

            spTimezones.setAdapter (mSpinnerAdapter);
        }

        private void addSpinnerItem (CountryData countryData) {
            mSpinnerAdapter.add (countryData);
        }

        private void updateSpinner () {
            spTimezones.setAdapter (mSpinnerAdapter);
        }

        private void setCountriesData(ArrayList<CountryData> countries) {
            for (int i = 0; i < countries.size(); i++) {
                addSpinnerItem ( countries.get(i) );
            }
        }
    }

    public interface EventListener {
        void onItemDataBinding (@NonNull TimezonesViewHolder viewHolder, int position);
    }
}
