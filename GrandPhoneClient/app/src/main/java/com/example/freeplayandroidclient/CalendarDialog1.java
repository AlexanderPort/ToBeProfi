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
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.freeplayandroidclient.dataClasses.User;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

public class CalendarDialog1 extends DialogFragment implements View.OnClickListener {
    private DatePicker datePicker;
    private Button cancelButton, createButton;
    public static int year, month, day;
    @Override
    public void onClick(View view) {
        if (view.getId() == cancelButton.getId()) {
            listener.onDialogNegativeClick(this);
        } else if (view.getId() == createButton.getId()) {
            listener.onDialogPositiveClick(this);
        }
    }

    public interface CalendarDialog1Listener {
        public void onDialogPositiveClick(CalendarDialog1 dialog);
        public void onDialogNegativeClick(CalendarDialog1 dialog);
    }

    CalendarDialog1Listener listener;


    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CalendarDialog1Listener) context;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.calendar_dialog1, null);

        datePicker = view.findViewById(R.id.date_picker_id);
        cancelButton = view.findViewById(R.id.cancel_id);
        createButton = view.findViewById(R.id.create_id);

        cancelButton.setOnClickListener(this);
        createButton.setOnClickListener(this);

        Calendar today = Calendar.getInstance();

        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int month, int day) {
                        CalendarDialog1.day = day;
                        CalendarDialog1.year = year;
                        CalendarDialog1.month = month;
                    }
                });

        builder.setView(view);

        return builder.create();
    }

    public String getDate() {
        return new StringBuilder()
                .append(datePicker.getDayOfMonth()).append(".")
                .append(datePicker.getMonth() + 1).append(".")
                .append(datePicker.getYear()).toString();
    }

}
