package com.grangewood.constantreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ReminderListAdaptor extends ArrayAdapter<Reminder> {

    private LayoutInflater mInflater;

    public ReminderListAdaptor(Context context, ReminderList objects) {
        super(context, R.layout.list_item_reminder, objects);

        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Reminder rowData = getItem(position);

        final View holder = GetHolder(convertView);

        TextView itemTitle = (TextView)holder.findViewById(R.id.title);
        TextView itemDescription = (TextView)holder.findViewById(R.id.description);
        TextView itemRate = (TextView)holder.findViewById(R.id.rate);

        itemTitle.setText(rowData.getTitle());
        itemDescription.setText(rowData.getDescription());
        itemRate.setText(rowData.getRate());

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)holder.findViewById(R.id.cancel);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                rowData.Disable(holder.getContext()); //or some other task
                ((ShowActivities) holder.getContext()).CreateDataView();
            }
        });
        return holder;
    }

    private View GetHolder(View convertView) {
        View holder = convertView;

        if(null == holder){
            holder = (View)mInflater.inflate(R.layout.list_item_reminder, null);
        }

        return holder;
    }
}
