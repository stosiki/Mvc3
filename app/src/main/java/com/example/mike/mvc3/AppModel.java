package com.example.mike.mvc3;

import java.util.ArrayList;
import java.util.List;

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

    public List<EventLine> getData() {
        return modelData.getData();
    }

    public void addEventLine(int type, String title) throws Exception {
        synchronized (this) {
            modelData.addEventLine(type, title);
        }
        notifyListeners();
    }

    public void removeEventLine(String eventLineTitle) {
        synchronized (this) {
            modelData.removeEventLine(eventLineTitle);
        }
        notifyListeners();
    }

    public EventLine getEventLine(String eventTitle) {
        return modelData.getEventLine(eventTitle);
    }

    public void addEvent(String eventTitle, SimpleEvent event) {
        synchronized (this) {
            modelData.addEvent(eventTitle, event);
        }
        notifyListeners();
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
