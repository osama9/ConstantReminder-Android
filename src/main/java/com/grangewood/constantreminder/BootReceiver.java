package com.grangewood.constantreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vquig on 21/09/2015.
 */
public class BootReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            ConstantRemindersDB db = new ConstantRemindersDB(context);
            ReminderList list = db.selectRecords();

            RemindUserReceiver rc = new RemindUserReceiver();

            for (Reminder reminder : list) {
                rc.SetAlarm(context, reminder);
            }
        }
    }
}
