package com.rmm.clocksuite.view.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.rmm.clocksuite.R;
import com.rmm.clocksuite.retrofit.CountryData;

public class TimezonesSpinnerAdapter extends ArrayAdapter<CountryData> {

    private Context  mContext;

    private ImageView  ivFlag;
    private TextView tvCapital;

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

        ivFlag    = v.findViewById (R.id.ivSpItem);
        tvCapital = v.findViewById (R.id.tvSpItem);

        // Data filling
        CountryData countryData = getItem(position);

        String str = String.format ("%s (%s)", countryData.getCapital(), countryData.getName());

        tvCapital.setText (str);

        RequestBuilder<PictureDrawable> requestBuilder
                = GlideToVectorYou
                                .init ()
                                .with(getContext())
                                .getRequestBuilder ();

        requestBuilder
                .load (countryData.getFlagImgLink())
                .placeholder(R.drawable.ic_flag)
                .centerCrop()
                .override(24)
                .into(ivFlag);

        return v;
    }
}
