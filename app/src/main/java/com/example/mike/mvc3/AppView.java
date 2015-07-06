package com.example.mike.mvc3;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.mike.mvc3.ControllerProtocol.*;

/**
 * Created by mike on 6/28/2015.
 *
 * View component is responsible for laying out controls, and their visual appearance
 * It notifies the controller of user input in async manner to avoid the possibility of
 * blocking and maintains a handler on UI thread to receive messages from the controller
 */

public class AppView extends LinearLayout {
    private static final String TAG = AppView.class.getSimpleName();

    // outbox handler to make sure view is never stuck due to controller malfunction
    private Handler controllerHandler;

    // inbox handler to make sure all inbound calls are processed on UI thread
    private Handler inboxHandler;

    // the model
    private AppModel model;

    // UI controls
    private ListView eventLineList;
    private ArrayAdapter eventLineListAdapter;
    private Button addEventLine;

    // We need this form of constructor to be able to describe the layout in xml file
    public AppView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inboxHandler = new InboxHandler(context.getMainLooper());

    }


    private class InboxHandler extends Handler {
        InboxHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            switch(message.what) {
                case UPDATE_STARTED:
                    AppView.this.showUpdating();
                    break;
                case UPDATE_ENDED:
//                        setData(valueOne);
                    eventLineListAdapter.notifyDataSetChanged();
                    showUpdateReady();
                    break;
                case DATA_UPDATE_ERROR:
                    Log.e("AppView", "Data Update Error");
                default:
                    Log.e(TAG, "Hadler: unkonwn command");
            }
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        MainActivity mainActivity = (MainActivity)getContext();
        model = mainActivity.getModel();

        List<EventLine> eventLineItems = model.getData();
        eventLineListAdapter = new EventLineArrayAdapter(getContext(),
//                android.R.layout.simple_list_item_1,
                R.layout.line_list_item,
                eventLineItems);
        ((ListActivity)getContext()).setListAdapter(eventLineListAdapter);

        eventLineList = (ListView)findViewById(R.id.list);

        /*
        eventLineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showUpdating();
                Log.d("", "showUpdating");
                Message.obtain(controllerHandler, VALUE_ONE_UPDATE_REQUESTED).sendToTarget();
            }
        });
        */
        addEventLine = (Button)findViewById(R.id.control1);
        addEventLine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                eventLineListAdapter.add(new EventLine(1, "something elase"));
                Message.obtain(controllerHandler, ADD_EVENT_LINE).sendToTarget();
            }
        });
    }

    public void setControllerHandler(Handler controllerHandler) {
        this.controllerHandler = controllerHandler;
    }

    public Handler getInboxHandler() {
        return inboxHandler;
    }



    private void showUpdateReady() {
        addEventLine.setText("Update Me");
        addEventLine.setEnabled(true);
        addEventLine.setBackgroundColor(Color.parseColor("#aa0000"));
    }


    private void showUpdating() {
        addEventLine.setBackgroundColor(Color.parseColor("#222222"));
        addEventLine.setTextColor(Color.parseColor("#cccccc"));
        addEventLine.setText("Updating...");
        addEventLine.setEnabled(false);
    }

}
