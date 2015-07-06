package com.example.mike.mvc3;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 28/06/2015.
 * 04/07 moved to real model
 * 06/07 introduced EventLine, moved from Hash to Array
 */
public class ModelData {
    private static final String TAG = ModelData.class.getSimpleName();

    private ArrayList<EventLine> data;

    public ModelData() {
        data = new ArrayList<>();

        createFakeData();
    }

    private void createFakeData() {
        try {
            addEventLine(1, "something_one");
            addEventLine(1, "something_two");
            addEventLine(1, "something_tree");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public List<EventLine> getData() {
        return data;
    }

    public void addEventLine(int type, String title) throws Exception {
        if(findTitle(title)) {
            throw new Exception("Title must be unique");
        }
        EventLine eventLine = new EventLine(type, title);
        data.add(eventLine);
    }

    public boolean removeEventLine(String eventLineTitle) {
        for(EventLine eventLine : data) {
            if(eventLine.getTitle().equals(eventLineTitle)) {
                data.remove(eventLine);
                return true;
            }
        }
        return false;
    }

    public EventLine getEventLine(String eventLineTitle) {
        for(EventLine eventLine : data) {
            if(eventLine.getTitle().equals(eventLineTitle)) {
                return eventLine;
            }
        }
        return null;
    }

    public void addEvent(String eventLineTitle, SimpleEvent event) {
        EventLine eventLine = getEventLine(eventLineTitle);
        eventLine.addEvent(event);
    }

    private boolean findTitle(String title) {
        for(EventLine eventLine : data) {
            if(eventLine.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
}
