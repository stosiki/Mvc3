package com.example.mike.mvc3;

import java.util.ArrayList;

/**
 * Created by User on 06/07/2015.
 */
public class EventLine {
    private EventLineDescriptor descriptor;
    private ArrayList<SimpleEvent> events;

    public EventLine(int type, String title) {
        descriptor = new EventLineDescriptor(type, title);
        events = new ArrayList<>();
    }

    public int getType() {
        return descriptor.getType();
    }

    public String getTitle() {
        return descriptor.getTitle();
    }

    public void addEvent(SimpleEvent event) {
        events.add(event);
    }

    public ArrayList<SimpleEvent> getEvents() {
        return events;
    }
}
