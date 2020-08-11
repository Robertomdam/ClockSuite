package com.rmm.clocksuite.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rmm.clocksuite.R;
import com.rmm.clocksuite.retrofit.CountryData;

public class TimezonesSpinnerAdapter extends ArrayAdapter<CountryData> {

    private Context mContext;

    public TimezonesSpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);

        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        return getCustomView (position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getDropDownView(position, convertView, parent);
        return getCustomView (position, convertView, parent);
    }

    private View getCustomView (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from (mContext).inflate(R.layout.spinner_dropdown_item_timezones, null);

        TextView tv = v.findViewById (R.id.tvSpItem);
        tv.setText (getItem(position).getCapital());

        return v;
    }
}
