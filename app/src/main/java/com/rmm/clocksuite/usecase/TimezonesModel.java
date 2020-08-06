package com.rmm.clocksuite.usecase;

import android.util.Log;

import com.rmm.clocksuite.presenter.timezones.ITimezonesContracts;
import com.rmm.clocksuite.retrofit.CountryData;
import com.rmm.clocksuite.retrofit.RetrofitManager;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimezonesModel implements ITimezonesContracts.ITimezonesModel, RetrofitManager.IRetrofitManagerDataCollector {

    private ITimezonesContracts.ITimezonesPresenterFromModel mPresenter;
    private RetrofitManager mRetrofitManager;

    public TimezonesModel (ITimezonesContracts.ITimezonesPresenterFromModel presenter) {
        mPresenter = presenter;
        mRetrofitManager = RetrofitManager.getInstance();
        mRetrofitManager.addDataCollector(this);
        mRetrofitManager.getAllCountries();
    }

    @Override
    public void onListCountriesReceived(ArrayList<CountryData> countries) {
        Log.d("DEBUGGING", "OK response: Num countries: " + countries.size());

        debug (countries.get(212));
        debug (countries.get(0));
        debug (countries.get(156));
    }

    @Override
    public void onListCountriesFailed() {
        Log.d("DEBUGGING", "Failed response");
    }

    void debug (CountryData country) {
        Log.d("DEBUGGING", "OK response: Country 212: " + country.toString());

        String timezone = country.getRegion()+"/"+country.getCapital();
        Calendar calendar = Calendar.getInstance (TimeZone.getTimeZone(timezone));

        Log.d("DEBUGGING", "Time: " + calendar.get(Calendar.HOUR_OF_DAY)+ ":" + calendar.get(Calendar.MINUTE));
    }
}
