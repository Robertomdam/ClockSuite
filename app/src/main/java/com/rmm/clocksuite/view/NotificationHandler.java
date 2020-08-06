package com.rmm.clocksuite.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.rmm.clocksuite.R;

/**
 * Helper class to abstract the android notification management in order to get a quicker access
 * to notification sending. Singleton class.
 */
public class NotificationHandler {

    private final String CHANNEL_ID = "0";

    private Context mAppContext;
    private NotificationManagerCompat mNotificationManager;

    static {
        instance = new NotificationHandler ();
    }

    private NotificationHandler () {}
    private static NotificationHandler instance;

    /**
     * Retrieves the singleton instance of the class.
     * @return The instance of the class.
     */
    public static NotificationHandler getInstance() {
        return instance;
    }

    /**
     * Initializes the basic members of the class and creates the channel for the notifications.
     * @param appContext The application context.
     */
    public void init (Context appContext) {

        mAppContext = appContext;
        mNotificationManager = NotificationManagerCompat.from (mAppContext);

        createNotificationChannel ();
    }

    /**
     * Sends a notification to the user. This kind of notification wont allow to be dismissed.
     * @param id The id for the notification.
     * @param title The title for the notification.
     * @param text The content text for the notification.
     */
    public void sendNotification (int id, String title, String text) {

        // create notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder (mAppContext, CHANNEL_ID);
        notificationBuilder.setContentTitle (title);
        notificationBuilder.setContentText (text);
        notificationBuilder.setSmallIcon (R.drawable.ic_alarm_small);
        notificationBuilder.setOngoing (true);

        // send notification
        Notification notification = notificationBuilder.build();
        mNotificationManager.notify (id, notification);
    }

    /**
     * Cancels a current notification.
     * @param id The id of the notification to cancel.
     */
    public void cancelNotification (int id) {
        NotificationManagerCompat.from (mAppContext).cancel (id);
    }

    /**
     * Creates the channel for the notifications.
     */
    private void createNotificationChannel () {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Default channel";
            String description = "Default channel for notifications";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            mNotificationManager.createNotificationChannel(channel);
        }
    }
}
