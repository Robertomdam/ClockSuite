package com.rmm.clocksuite.presenter.timezones;

import com.rmm.clocksuite.usecase.TimezonesModel;

public class TimezonesPresenter implements ITimezonesContracts.ITimezonesPresenterFromView, ITimezonesContracts.ITimezonesPresenterFromModel {

    private ITimezonesContracts.ITimezonesView  mView;
    private ITimezonesContracts.ITimezonesModel mModel;

    public TimezonesPresenter (ITimezonesContracts.ITimezonesView  view) {
        mView  = view;
        mModel = new TimezonesModel (this);
    }
}
