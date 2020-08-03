package com.rmm.clocksuite.database;

import android.content.Context;
import android.os.AsyncTask;

import com.rmm.clocksuite.entity.Alarm;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Class that helps managing the access to the Room database.
 */
public class DBManager {

    private IStatusHandler mStatusHandler;

    private RoomDatabaseWrapper mRoomDatabaseWrapper;
    private static IAlarmsDao mDAOAlarms;

    /**
     * Creates the instance of the DBManager an initializes its members.
     * @param appContext
     * @param statusHandler
     */
    public DBManager (Context appContext, IStatusHandler statusHandler) {
        mRoomDatabaseWrapper = RoomDatabaseWrapper.getInstance (appContext);
        mDAOAlarms = mRoomDatabaseWrapper.daoAlarms();

        mStatusHandler = statusHandler;
    }

    /**
     * Inserts a new alarm into the database.
     * @param alarm The new alarm to insert.
     */
    public void insertAlarmAsync(final Alarm alarm) {
        new InsertAlarmTask(alarm, mStatusHandler, this).execute();
    }

    /**
     * Updates the data of a single alarm.
     * @param alarm The alarm to update.
     */
    public void updateAlarmAsync(final Alarm alarm) {
        new UpdateAlarmTask(alarm, mStatusHandler, this).execute();
    }

    /**
     * Retrieves a single alarm from the database.
     * @param id The id of the alarm.
     * @return The alarm that matches with the id.
     */
    public Alarm getAlarmsByIdAsync (final long id) {
        try {
            return new GetAlarmTask(id).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all the alarms in the database.
     * @return All the alarms in the database.
     */
    public ArrayList<Alarm> getAllAlarmsAsync () {
        try {
            return new GetAllAlarmsTask().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates an async task to remove one alarm.
     * @param alarmCopy The alarm to delete.
     */
    public void removeAlarmAsync(final Alarm alarmCopy) {
        new RemoveAlarmTask (alarmCopy, mStatusHandler, this).execute();
    }

    /**
     * Creates an async task to clear all the alarms.
     */
    public void clearAlarmsAsync () {
        new ClearAlarmsTask(mStatusHandler, this).execute();
    }


    /**
     * Interface that allow an external class to listen to database events.
     */
    public interface IStatusHandler {
        void onDataInserted (Alarm alarm);
        void onDataUpdated  (Alarm alarm);
        void onDataRemoved  (Alarm alarmCopy);
        void onDatabaseUpdate (ArrayList<Alarm> alarms);
    }



    /*******  ASYNC TASKS  ******/

    /**
     * Custom task that calls the DAO to insert one alarm.
     * Also notifies the event listener that the database elements has been modified.
     */
    private static class InsertAlarmTask extends AsyncTask<Void, Void, Void> {

        private long            mReturnedId;
        private DBManager       mDBManager;
        private IStatusHandler  mEventListener;
        private Alarm           mAlarm;

        InsertAlarmTask (Alarm alarm, IStatusHandler eventListener, DBManager dbManager) {
            mAlarm          = alarm;
            mEventListener  = eventListener;
            mDBManager      = dbManager;
            mReturnedId     = -1;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mReturnedId = mDAOAlarms.insert (mAlarm);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mReturnedId == -1)
                return;

            mAlarm.setId (mReturnedId);
            mEventListener.onDataInserted   (mAlarm);
            mEventListener.onDatabaseUpdate (mDBManager.getAllAlarmsAsync());
        }
    }

    /**
     * Custom task that calls the DAO to update one alarm.
     * Also notifies the event listener that the database elements has been modified.
     */
    private static class UpdateAlarmTask extends AsyncTask<Void, Void, Void> {

        private int             mReturnedId;
        private DBManager       mDBManager;
        private IStatusHandler  mEventListener;
        private Alarm           mAlarm;

        UpdateAlarmTask (Alarm alarm, IStatusHandler eventListener, DBManager dbManager) {
            mAlarm          = alarm;
            mEventListener  = eventListener;
            mDBManager      = dbManager;
            mReturnedId     = 0;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mReturnedId = mDAOAlarms.update(mAlarm);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mReturnedId <= 0)
                return;

            mEventListener.onDataUpdated    (mAlarm);
            mEventListener.onDatabaseUpdate (mDBManager.getAllAlarmsAsync());
        }
    }

    /**
     * Custom task that calls the DAO to retrieve one alarm.
     */
    private static class GetAlarmTask extends AsyncTask<Void, Void, Alarm> {

        private long mId;
        GetAlarmTask(long id) { mId = id; }

        @Override
        protected Alarm doInBackground(Void... voids) {
            return mDAOAlarms.get(mId);
        }
    }

    /**
     * Custom task that calls the DAO to retrieve all the alarms.
     */
    private static class GetAllAlarmsTask extends AsyncTask<Void, Void, ArrayList<Alarm>> {
        @Override
        protected ArrayList<Alarm> doInBackground(Void... voids) {
            return (ArrayList<Alarm>) mDAOAlarms.getAll();
        }
    }

    /**
     * Custom task that calls the DAO to remove one alarm.
     * Also notifies the event listener that the database elements has been modified.
     */
    private static class RemoveAlarmTask extends AsyncTask<Void, Void, Void> {

        private int             mReturnedId;
        private DBManager       mDBManager;
        private IStatusHandler  mEventListener;
        private long            mId;
        private Alarm           mAlarmCopy;

        RemoveAlarmTask(Alarm alarmCopy, IStatusHandler eventListener, DBManager dbManager) {
            mAlarmCopy      = alarmCopy;
            mId             = alarmCopy.getId();
            mEventListener  = eventListener;
            mDBManager      = dbManager;
            mReturnedId     = 0;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mReturnedId = mDAOAlarms.remove(mId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mReturnedId <= 0)
                return;

            mEventListener.onDataRemoved    (mAlarmCopy);
            mEventListener.onDatabaseUpdate (mDBManager.getAllAlarmsAsync());
        }
    }

    /**
     * Custom task that calls the DAO to clear all the alarms.
     * Also notifies the event listener that the database elements has been modified.
     */
    private static class ClearAlarmsTask extends AsyncTask<Void, Void, Void> {

        private DBManager       mDBManager;
        private IStatusHandler  mEventListener;

        public ClearAlarmsTask (IStatusHandler eventListener, DBManager dbManager) {
            mEventListener  = eventListener;
            mDBManager      = dbManager;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDAOAlarms.clear();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mEventListener.onDatabaseUpdate (mDBManager.getAllAlarmsAsync());
        }
    }

}

