package com.example.mike.mvc3;

import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

/**
 * Created by mike on 6/28/2015.
 */
public class AppModel {
    public interface AppModelListener {
        public void onModelUpdated(AppModel model);
    }

    private ModelData modelData;

    private List<AppModelListener> listeners;

    public AppModel() {
        modelData = new ModelData();
        listeners = new ArrayList<AppModelListener>();
    }

    public final ModelData getData() {
        synchronized (this) {
            return modelData;
        }
    }

    public final void updateData(String s) {
        SystemClock.sleep(5000);
        synchronized (this) {
            modelData.setData(s);
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
