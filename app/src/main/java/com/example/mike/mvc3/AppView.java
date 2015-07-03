package com.example.mike.mvc3;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    // UI controls
    private TextView dataView1;
    private Button control1;

    // We need this form of constructor to be able to describe the layout in xml file
    public AppView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inboxHandler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch(message.what) {
                    case UPDATE_STARTED:
                        showUpdating();
                        break;
                    case UPDATE_ENDED:
                        Bundle data = message.getData();
                        String valueOne = data.getString("value_one");
                        setData(valueOne);
                        showUpdateReady();
                        break;
                    case DATA_UPDATE_ERROR:
                        Log.e("AppView", "Data Update Error");
                    default:
                        Log.e(TAG, "Hadler: unkonwn command");
                }
            }
        };
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

        dataView1 = (TextView)findViewById(R.id.data_view1);
        control1 = (Button)findViewById(R.id.control1);
        control1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdating();
                Log.d("", "showUpdating");
                Message msg = Message.obtain(controllerHandler, VALUE_ONE_UPDATE_REQUESTED);
                if(msg != null && msg.getTarget() != null) {
                    msg.sendToTarget();
                } else {
                    Log.d("MainActivity", "Controller handler message is null");
                }
            }
        });

    }

    public void setControllerHandler(Handler controllerHandler) {
        this.controllerHandler = controllerHandler;
    }

    public Handler getInboxHandler() {
        return inboxHandler;
    }

    private void setData(String data) {
        // update view
        dataView1.setText(dataView1.getText() + data);
    }

    private void showUpdateReady() {
        control1.setText("Update Me");
        control1.setEnabled(true);
        control1.setBackgroundColor(Color.parseColor("#aa0000"));
    }


    private void showUpdating() {
        control1.setBackgroundColor(Color.parseColor("#222222"));
        control1.setTextColor(Color.parseColor("#cccccc"));
        control1.setText("Updating...");
        control1.setEnabled(false);
    }
}
