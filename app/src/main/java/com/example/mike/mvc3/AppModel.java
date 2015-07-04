package com.example.mike.mvc3;

import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

/**
 * Created by mike on 6/28/2015.
 * 04/07 moved to real model
 */
public class AppModel {
    public interface AppModelListener {
        void onModelUpdated(AppModel model);
    }

    private ModelData modelData;

    private final List<AppModelListener> listeners;

    public AppModel() {
        modelData = new ModelData();
        listeners = new ArrayList<>();
    }

    public final ModelData getData() {
        synchronized (this) {
            return modelData;
        }
    }

    public void addEventLine(EventDescriptor descriptor) throws Exception {
        synchronized (this) {
            modelData.addEventLine(descriptor);
        }
        notifyListeners();
    }

    public ArrayList<SimpleEvent> getEventLine(String eventTitle) {
        return modelData.getEventLine(eventTitle);
    }

    public void addEvent(String eventTitle, SimpleEvent event) {
        synchronized (this) {
            modelData.addEvent(eventTitle, event);
        }
    }

    public void addListener(AppModelListener listener) {
        synchronized(listeners) {
            listeners.add(listener);
        }
    }

    public void removeListener(AppModelListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    private void notifyListeners() {
        for(AppModelListener listener : listeners) {
            listener.onModelUpdated(this);
        }
    }
}
