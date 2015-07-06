package com.example.mike.mvc3;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 06/07/2015.
 */
public class EventLineArrayAdapter extends ArrayAdapter<EventLine> {
    private static final String TAG = EventLineArrayAdapter.class.getSimpleName();
    private Context context;
    private int resource;
    private List<EventLine> objects;

    EventLineArrayAdapter(Context context, int resource, List<EventLine> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView");
        View rowView = convertView;
        EventLineHolder holder = null;

        if(rowView == null) {
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            rowView = inflater.inflate(resource, parent, false);

            holder = new EventLineHolder();
            holder.title = (TextView)rowView.findViewById(R.id.event_line_title);
            holder.eventCount = (TextView)rowView.findViewById(R.id.event_line_event_count);

            rowView.setTag(holder);
        } else {
            holder = (EventLineHolder)rowView.getTag();
        }

        EventLine eventLine = objects.get(position);
        holder.title.setText(eventLine.getTitle());
        holder.eventCount.setText(String.valueOf(eventLine.getEvents().size()));

        return rowView;
    }

    static class EventLineHolder {
        TextView title;
        TextView eventCount;
    }
}
