package com.rmm.clocksuite.presenter;

import com.rmm.clocksuite.entity.Alarm;

import java.util.ArrayList;

/**
 * Specific presenter for the view that will handle the details of the alarms.
 */
public class AlarmsDetailsPresenter implements IAlarmsContracts.IAlarmsDetailsPresenter {

    private IAlarmsContracts.IAlarmsDetailsView mView;

    /**
     * Construct the instance of the presenter and sets its view.
     * @param view The view of the presenter.
     */
    public AlarmsDetailsPresenter (IAlarmsContracts.IAlarmsDetailsView view) {
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
