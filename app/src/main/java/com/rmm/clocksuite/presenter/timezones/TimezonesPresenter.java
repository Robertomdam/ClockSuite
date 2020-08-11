package com.rmm.clocksuite.presenter.timezones;

import com.rmm.clocksuite.retrofit.CountryData;
import com.rmm.clocksuite.usecase.TimezonesModel;

import java.util.ArrayList;

public class TimezonesPresenter implements ITimezonesContracts.ITimezonesPresenterFromView, ITimezonesContracts.ITimezonesPresenterFromModel {

    private ITimezonesContracts.ITimezonesView  mView;
    private ITimezonesContracts.ITimezonesModel mModel;

    public TimezonesPresenter (ITimezonesContracts.ITimezonesView  view) {
        mView  = view;
        mModel = new TimezonesModel (this);
    }

    @Override
    public void onListCountriesTimezonesReceived(ArrayList<CountryData> countries) {
        mView.onListCountriesTimezonesReceived(countries);
    }
}
