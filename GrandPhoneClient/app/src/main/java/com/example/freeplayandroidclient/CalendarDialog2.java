package com.example.freeplayandroidclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.freeplayandroidclient.dataClasses.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;
import java.util.PrimitiveIterator;

public class CalendarDialog2 extends DialogFragment implements View.OnClickListener {
    private Spinner spinner;
    private ArrayList<User> users;
    private DataStorage dataStorage;
    private Button cancelButton, createButton;
    private EditText dateText, timeText, periodText, durationText;
    @Override
    public void onClick(View view) {
        if (view.getId() == cancelButton.getId()) {
            listener.onDialogNegativeClick(this);
        } else if (view.getId() == createButton.getId()) {
            listener.onDialogPositiveClick(this);
        }
    }

    public interface CalendarDialog2Listener {
        public void onDialogPositiveClick(CalendarDialog2 dialog);
        public void onDialogNegativeClick(CalendarDialog2 dialog);
    }

    CalendarDialog2Listener listener;


    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CalendarDialog2Listener) context;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.calendar_dialog2, null);

        dateText = view.findViewById(R.id.d_date_id);
        timeText = view.findViewById(R.id.d_time_id);
        periodText = view.findViewById(R.id.d_period_id);
        durationText = view.findViewById(R.id.d_duration_id);

        cancelButton = view.findViewById(R.id.cancel_id);
        createButton = view.findViewById(R.id.create_id);

        spinner = view.findViewById(R.id.spinner_id);

        dataStorage = new DataStorage(getContext());
        users = dataStorage.getDependants(dataStorage.getCurrentUserId());
        User[] dependants = new User[users.size()];
        SpinnerAdapter adapter = new SpinnerAdapter(getContext(),
                R.layout.spinner_item, users.toArray(dependants));

        spinner.setAdapter(adapter);
        spinner.setSelection(1, true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        cancelButton.setOnClickListener(this);
        createButton.setOnClickListener(this);

        builder.setView(view);

        return builder.create();
    }

    public class SpinnerAdapter extends ArrayAdapter<User> {

        public SpinnerAdapter(Context context, int textViewResourceId, User[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.spinner_item, parent, false);
            TextView textView = view.findViewById(R.id.username);
            ImageView imageView = view.findViewById(R.id.thumbnail);
            textView.setText(users.get(position).getName());
            imageView.setImageBitmap(dataStorage.getUserImage(users.get(position).getId()));
            return view;
        }
    }

}
