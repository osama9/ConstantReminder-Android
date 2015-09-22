package com.grangewood.constantreminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class CreateReminder extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_reminder, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {
            CreateReminder(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void CreateReminder(View view) {
        Reminder reminder = new Reminder();

        EditText title = (EditText)findViewById(R.id.title);
        EditText description = (EditText)findViewById(R.id.description);
        Spinner rate = (Spinner)findViewById(R.id.reminderRate);

        reminder.setTitle(title.getText().toString());
        reminder.setDescription(description.getText().toString());
        reminder.setRate(rate.getSelectedItem().toString());

        if (reminder.getTitle().equals("")) {
           showError("Please enter a title.", this);
        }
        else {
            reminder.Enable(this);
            finish();
        }
    }

    public void showError(String message, Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Alert")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
