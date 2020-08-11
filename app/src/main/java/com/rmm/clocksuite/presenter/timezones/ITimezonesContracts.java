package com.rmm.clocksuite.presenter.timezones;

import com.rmm.clocksuite.retrofit.CountryData;

import java.util.ArrayList;

public interface ITimezonesContracts {

    public interface ITimezonesView {
        void onListCountriesTimezonesReceived(ArrayList<CountryData> countries);
    }

    public interface ITimezonesPresenterFromView {

    }

    public interface ITimezonesPresenterFromModel {
        void onListCountriesTimezonesReceived(ArrayList<CountryData> countries);
    }

    public interface ITimezonesModel {

    }

}
