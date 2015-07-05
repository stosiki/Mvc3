package com.example.mike.mvc3;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mike on 6/28/2015.
 * 04/07 moved to real model
 */
public class ModelData {
    private static final String TAG = ModelData.class.getSimpleName();

    private HashMap<EventLineDescriptor, ArrayList<SimpleEvent>> data;

    public ModelData() {
        data = new HashMap<>();

        createFakeData();
    }

    private void createFakeData() {
        EventLineDescriptor eld1 = new EventLineDescriptor(1, "something_one");
        EventLineDescriptor eld2 = new EventLineDescriptor(1, "something_two");
        EventLineDescriptor eld3 = new EventLineDescriptor(1, "something_three");
        try {
            addEventLine(eld1);
            addEventLine(eld2);
            addEventLine(eld3);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public HashMap<EventLineDescriptor, ArrayList<SimpleEvent>> getData() {
        return data;
    }

    public void addEventLine(EventLineDescriptor descriptor) throws Exception {
        // make sure the title is unique
        for(EventLineDescriptor ed : data.keySet()) {
            if(ed.getTitle().equals(descriptor.getTitle())) {
                throw new Exception("duplicate key");
            }
        }
        data.put(descriptor, new ArrayList<SimpleEvent>());
    }

    public ArrayList<SimpleEvent> getEventLine(String eventTitle) {
        EventLineDescriptor descriptor = findDescriptorByTitle(eventTitle);
        return (descriptor == null) ? null : data.get(descriptor);
    }

    public void addEvent(String eventTitle, SimpleEvent event) {
        EventLineDescriptor descriptor = findDescriptorByTitle(eventTitle);
        data.get(descriptor).add(event);
    }

    private EventLineDescriptor findDescriptorByTitle(String title) {
        for(EventLineDescriptor descriptor : data.keySet()) {
            if(title.equals(descriptor.getTitle())) {
                return descriptor;
            }
        }
        return null;
    }

    public List<EventLineDescriptor> getEventLineDescriptors() {
        ArrayList<EventLineDescriptor> list = new ArrayList<>();
        for(EventLineDescriptor descriptor : data.keySet()) {
            list.add(descriptor);
        }
        return list;
    }

    public void removeEventLine(String eventLineTitle) {
        EventLineDescriptor descriptor = findDescriptorByTitle(eventLineTitle);
        data.remove(descriptor);
    }
}
