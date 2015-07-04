package com.example.mike.mvc3;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mike on 6/28/2015.
 * 04/07 moved to real model
 */
public class ModelData {

    private HashMap<EventDescriptor, ArrayList<SimpleEvent>> data;

    public ModelData() {
        data = new HashMap<>();
    }

    public HashMap<EventDescriptor, ArrayList<SimpleEvent>> getData() {
        return data;
    }

    public void addEventLine(EventDescriptor descriptor) throws Exception {
        // make sure the title is unique
        for(EventDescriptor ed : data.keySet()) {
            if(ed.getTitle().equals(descriptor.getTitle())) {
                throw new Exception("duplicate key");
            }
        }
        data.put(descriptor, new ArrayList<SimpleEvent>());
    }

    public ArrayList<SimpleEvent> getEventLine(String eventTitle) {
        EventDescriptor descriptor = findDescriptorByTitle(eventTitle);
        return (descriptor == null) ? null : data.get(descriptor);
    }

    public void addEvent(String eventTitle, SimpleEvent event) {
        EventDescriptor descriptor = findDescriptorByTitle(eventTitle);
        data.get(descriptor).add(event);
    }

    private EventDescriptor findDescriptorByTitle(String title) {
        for(EventDescriptor descriptor : data.keySet()) {
            if(title.equals(descriptor.getTitle())) {
                return descriptor;
            }
        }
        return null;
    }
}
