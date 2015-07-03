package com.example.mike.mvc3;

import android.app.DialogFragment;
import android.os.*;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.example.mike.mvc3.ControllerProtocol.*;


public class MainActivity extends ActionBarActivity
        implements TextEntryDialogFragment.TextEntryDialogListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private AppModel model;
    private AppView appView;

    private Handler inboxHandler;
    private List<Handler> outboxHandlers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        model = new AppModel();
        HandlerThread handlerThread = new HandlerThread("controller_inbox",
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
        inboxHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "handleMessage");
                onUpdateRequested();
            }
        };

        outboxHandlers = new ArrayList<Handler>();

        appView = (AppView)View.inflate(this, R.layout.activity_main, null);
        setContentView(appView);
        appView.setControllerHandler(inboxHandler);
        addOutboxHandler(appView.getInboxHandler());
    }

    public void onUpdateRequested() {
        // notify observers of update start
        Log.d(TAG, "onUpdateReqested");
        notifyOutboxHandlers(UPDATE_STARTED, 0, 0, null, null);
        // show dialog
        TextEntryDialogFragment dialog = new TextEntryDialogFragment();
        dialog.show(getFragmentManager(), "dialog");
    }

    private final void notifyOutboxHandlers(int what, int arg1, int arg2, Object obj, Bundle bundle) {
        if (outboxHandlers.isEmpty()) {
            Log.w(TAG, String.format("No outbox handler to handle outgoing message (%d)", what));
        } else {
            for (Handler handler : outboxHandlers) {
                Message msg = Message.obtain(handler, what, arg1, arg2, obj);
                if(bundle != null) {
                    msg.setData(bundle);
                }
                msg.sendToTarget();
            }
        }
    }

    private void addOutboxHandler(Handler inboxHandler) {
        synchronized (outboxHandlers) {
            outboxHandlers.add(inboxHandler);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
//        if(dialog instanceof TextEntryDialogFragment) {
            // gather data from the dialog
            String description = ((TextEntryDialogFragment) dialog).getDescriptionText();
            Log.d(TAG, description);
            // launch model update
            updateModel(description);
//        }
    }

    private void updateModel(final String s) {
        Thread updateThread = new Thread("Model Update") {
            @Override
            public void run() {
                try {
                    model.updateData(s);
                } catch (Throwable t) {
                    Log.e(TAG, "Error in the update thread", t);
                    notifyOutboxHandlers(DATA_UPDATE_ERROR, 0, 0, null, null);
                } finally {
                    ModelData data = model.getData();
                    Bundle bundle = new Bundle();
                    bundle.putString("value_one", data.getData());
                    notifyOutboxHandlers(UPDATE_ENDED, 0, 0, null, bundle);
                    Log.d(TAG, "Model udpate finished");
                }
            }
        };
        updateThread.start();
        Log.d(TAG, "Starting model update");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}