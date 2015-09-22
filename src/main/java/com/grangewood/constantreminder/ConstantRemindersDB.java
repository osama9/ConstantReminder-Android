package com.grangewood.constantreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ConstantRemindersDB{

    private MyDatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public final static String REMINDER_TABLE="Reminder";

    public final static String REMINDER_ID="_id";
    public final static String REMINDER_NAME="title";
    public final static String REMINDER_DES="description";
    public final static String REMINDER_RATE="rate";
    /**
     *
     * @param context
     */
    public ConstantRemindersDB(Context context){
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecords(String title, String description, String rate){
        ContentValues values = new ContentValues();
        values.put(REMINDER_NAME, title);
        values.put(REMINDER_DES, description);
        values.put(REMINDER_RATE, rate);
        return database.insert(REMINDER_TABLE, null, values);
    }

    public ReminderList selectRecords() {

        ReminderList list = new ReminderList();

        String[] cols = new String[] {REMINDER_ID, REMINDER_NAME, REMINDER_DES, REMINDER_RATE};
        Cursor mCursor = database.query(true, REMINDER_TABLE,cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();

            for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
                // The Cursor is now set to the right position
                Reminder reminder = new Reminder();
                reminder.setId(mCursor.getInt(mCursor.getColumnIndex(REMINDER_ID)));
                reminder.setTitle(mCursor.getString(mCursor.getColumnIndex(REMINDER_NAME)));
                reminder.setDescription(mCursor.getString(mCursor.getColumnIndex(REMINDER_DES)));
                reminder.setRate(mCursor.getString(mCursor.getColumnIndex(REMINDER_RATE)));

                list.add(reminder);
            }
        }

        return list; // iterate to get each value.
    }

    public Reminder selectRecord(long id) {

        Reminder reminder = new Reminder();

        String[] cols = new String[] {REMINDER_ID, REMINDER_NAME, REMINDER_DES, REMINDER_RATE};
        Cursor mCursor = database.query(true, REMINDER_TABLE,cols, REMINDER_ID + "=" + id , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();

            for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
                // The Cursor is now set to the right position
                reminder.setId(mCursor.getInt(mCursor.getColumnIndex(REMINDER_ID)));
                reminder.setTitle(mCursor.getString(mCursor.getColumnIndex(REMINDER_NAME)));
                reminder.setDescription(mCursor.getString(mCursor.getColumnIndex(REMINDER_DES)));
                reminder.setRate(mCursor.getString(mCursor.getColumnIndex(REMINDER_RATE)));

                return reminder;
            }
        }

        return reminder; // iterate to get each value.
    }

    public void deleteRecord(long id) {
        int rowcount = database.delete(REMINDER_TABLE,
                REMINDER_ID + "=" + id,
                null);
        Log.d("No of record deleted", String.valueOf(rowcount));
    }
}