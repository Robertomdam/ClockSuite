package com.rmm.clocksuite.presenter;

import com.rmm.clocksuite.entity.Alarm;

import java.util.ArrayList;

/**
 * Specific presenter for the view that handles the group of alarms.
 */
public class AlarmsHandlerPresenter implements IAlarmsContracts.IAlarmsHandlerPresenter {

    private IAlarmsContracts.IAlarmsHandlerView mView;

    /**
     * Construct the instance of the presenter and sets its view.
     * @param view The view of the presenter.
     */
    public AlarmsHandlerPresenter (IAlarmsContracts.IAlarmsHandlerView view) {
        mView = view;
    }

    /**
     * Calls the view to refresh its data.
     * @param alarms The list of alarms that has been changed.
     */
    @Override
    public void onDataChanged(ArrayList<Alarm> alarms) {
        mView.onDataChanged (alarms);
    }
}
