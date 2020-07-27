package com.rmm.clocksuite.presenter;

import android.content.Context;

public class AlarmsDetailsPresenter implements IAlarmsContracts.IAlarmsDetailsPresenter {

    IAlarmsContracts.IAlarmsDetailsView mView;

    public AlarmsDetailsPresenter (IAlarmsContracts.IAlarmsDetailsView view)
    {
        mView = view;
    }
}
