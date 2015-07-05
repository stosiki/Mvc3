package com.example.mike.mvc3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by User on 04/07/2015.
 * ok
 */
public class EventLineCreateDialog  extends DialogFragment {
    public interface EventLineCreateDialogListener {
        public void onEventLineCreatePositive(DialogFragment dialog);
        public void onEventLineCreateNegative(DialogFragment dialog);
    }

    private EventLineCreateDialogListener listener;
    private EditText description;

    @Override
     public void onAttach(Activity activity) {
    super.onAttach(activity);

    try {
        listener = (EventLineCreateDialogListener)activity;
    } catch (ClassCastException e) {
        throw new ClassCastException("must implement SomeTextDialogListener");
    }

}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage("Enter Event Title");
        description = new EditText(this.getActivity());
        dialogBuilder.setView(description);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onEventLineCreatePositive(EventLineCreateDialog.this);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return dialogBuilder.create();
    }

    public String getTitle() {
        return description.getText().toString();
    }
}

