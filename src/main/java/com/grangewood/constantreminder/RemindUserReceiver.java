package com.grangewood.constantreminder;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.util.Calendar;

public class RemindUserReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        SendNotification(context, (Reminder)intent.getSerializableExtra("reminder"));

        wl.release();
    }

    public void SendNotification(Context context, Reminder reminder){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.reminder)
                        .setContentTitle(reminder.getTitle())
                        .setContentText(reminder.getDescription())
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, ShowActivities.class);
        resultIntent.putExtra("reminder", reminder);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, (int)reminder.getId(), resultIntent, 0);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManagerCompat mNotificationManager =
                NotificationManagerCompat.from(context);
        // mId allows you to update the notification later on.
        mNotificationManager.notify((int)reminder.getId(), mBuilder.build());
    }

    public void SetAlarm(Context context, Reminder reminder)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, RemindUserReceiver.class);
        intent.putExtra("reminder", reminder);
        PendingIntent pi = PendingIntent.getBroadcast(context, (int)reminder.getId(), intent, 0);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                Calendar.getInstance().getTimeInMillis() + reminder.getRateMilliseconds(),
                reminder.getRateMilliseconds(),
                pi);
    }

    public void CancelAlarm(Context context, Reminder reminder)
    {
        Intent intent = new Intent(context, RemindUserReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, (int)reminder.getId(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}