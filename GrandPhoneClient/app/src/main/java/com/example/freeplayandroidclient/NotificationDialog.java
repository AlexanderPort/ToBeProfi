package com.example.freeplayandroidclient;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.freeplayandroidclient.dataClasses.Notification;

public class NotificationDialog extends DialogFragment implements View.OnClickListener {
    private Button cancelButton, createButton;
    private EditText message;

    @Override
    public void onClick(View view) {
        if (view.getId() == cancelButton.getId()) {
            listener.onDialogNegativeClick(this);
        } else if (view.getId() == createButton.getId()) {
            listener.onDialogPositiveClick(this);
        }
    }

    public interface NotificationDialogListener {
        public void onDialogPositiveClick(NotificationDialog dialog);
        public void onDialogNegativeClick(NotificationDialog dialog);
    }
    NotificationDialogListener listener;


    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NotificationDialogListener) context;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.notification_dialog, null);
        cancelButton = view.findViewById(R.id.cancel_id);
        createButton = view.findViewById(R.id.create_id);
        message = view.findViewById(R.id.message_id);
        cancelButton.setOnClickListener(this);
        createButton.setOnClickListener(this);
        builder.setView(view);
        return builder.create();
    }
    public String getNotificationMessage() {
        return message.getText().toString();
    }

    public Notification getNotification() {
        return new Notification(getNotificationMessage());
    }

}