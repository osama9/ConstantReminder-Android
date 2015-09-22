package com.grangewood.constantreminder;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;


public class ShowActivities extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_activities);
    }

    public void CreateDataView() {
        ConstantRemindersDB db = new ConstantRemindersDB(this);
        ReminderList list = db.selectRecords();
        ReminderListAdaptor adapter = new ReminderListAdaptor(this, list);
        setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        CreateDataView();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_show_activities, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            CreateReminder(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void CreateReminder(View view) {
        Intent intent = new Intent(this, CreateReminder.class);
        startActivity(intent);
    }
}
