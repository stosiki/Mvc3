package com.example.mike.mvc3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

/**
 * Created by mike on 7/2/2015.
 */
public class TextEntryDialogFragment extends DialogFragment {
    public interface TextEntryDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    private TextEntryDialogListener listener;
    private EditText description;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (TextEntryDialogListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement SomeTextDialogListener");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage("Enter Description");
        description = new EditText(this.getActivity());
        dialogBuilder.setView(description);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               listener.onDialogPositiveClick(TextEntryDialogFragment.this);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return dialogBuilder.create();
    }

    public String getDescriptionText() {
        return description.getText().toString();
    }
}
