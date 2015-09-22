package com.grangewood.constantreminder;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by vquig on 18/09/2015.
 */
public class Reminder implements Serializable {
    private long id;
    private String title;
    private String description;
    private String rate;

    public long getRateMilliseconds() {
        long result;

        switch (rate) {
            case "15 minutes":
                result = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                break;
            case "1/2 hour":
                result =  AlarmManager.INTERVAL_HALF_HOUR;
                break;
            case "1 hour":
                result =  AlarmManager.INTERVAL_HOUR;
                break;
            case "1/2 day":
                result =  AlarmManager.INTERVAL_HALF_DAY;
                break;
            case "1 day":
                result =  AlarmManager.INTERVAL_DAY;
                break;
            default:
                result =  AlarmManager.INTERVAL_DAY;
                break;
        }

        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void Enable(Context context) {
        ConstantRemindersDB db = new ConstantRemindersDB(context);

        setId(db.createRecords(getTitle(),
                getDescription(),
                getRate()));

        RemindUserReceiver receiver = new RemindUserReceiver();
        receiver.SetAlarm(context, this);

        Toast.makeText(context, "Reminder set", Toast.LENGTH_SHORT);
    }

    public void Disable(Context context) {
        ConstantRemindersDB db = new ConstantRemindersDB(context);

        RemindUserReceiver receiver = new RemindUserReceiver();
        receiver.CancelAlarm(context, this);

        db.deleteRecord(getId());

        Toast.makeText(context, "Reminder removed", Toast.LENGTH_SHORT);
    }
}
