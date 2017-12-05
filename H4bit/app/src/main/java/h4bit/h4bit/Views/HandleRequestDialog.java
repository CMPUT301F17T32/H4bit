package h4bit.h4bit.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import h4bit.h4bit.R;

/**
 * HabitHistoryActivity
 * Version 1.0
 * December 12, 2017
 * Copyright 2017 Team 32, CMPUT 301, University of Alberta - All Rights Reserved.
 */

public class HandleRequestDialog extends DialogFragment {
    private String username;

    public static HandleRequestDialog newInstance(String username){
        HandleRequestDialog hrd = new HandleRequestDialog();
        Bundle args = new Bundle();
        args.putString("username", username);
        hrd.setArguments(args);
        return hrd;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final String username = getArguments().getString("username");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_handle_request, null); // different
        TextView message = (TextView) view.findViewById(R.id.requestMessage);
        message.setText("Would you like to allow " + username + " to follow you?");
        builder.setView(view)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDismiss(true, username);
                    }
                })
                .setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDismiss(false, username);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static interface onDismissListener {
        void onDismiss(boolean accepted, String username);
    }

    private onDismissListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (HandleRequestDialog.onDismissListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onDismissListener");
        }
    }
}
