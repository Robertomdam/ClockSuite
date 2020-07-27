package com.rmm.clocksuite.presenter;

import android.content.Context;

public class AlarmsHandlerPresenter implements IAlarmsContracts.IAlarmsHandlerPresenter {

    IAlarmsContracts.IAlarmsHandlerView mView;

    public AlarmsHandlerPresenter (IAlarmsContracts.IAlarmsHandlerView view)
    {
        mView = view;
    }
}
