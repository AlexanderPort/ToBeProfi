package com.example.freeplayandroidclient;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.example.freeplayandroidclient.api.API;
import com.example.freeplayandroidclient.dataClasses.User;

import org.json.JSONException;
import org.json.JSONObject;

public class CalendarActivity extends Base implements
        CalendarDialog1.CalendarDialog1Listener,
        CalendarDialog2.CalendarDialog2Listener {
    private EditText dateText;
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        dateText = findViewById(R.id.date_id);
        addButton = findViewById(R.id.add_button);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showCalendarDialog1(); }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog2();
            }
        });
    }

    public void showCalendarDialog1() {
        CalendarDialog1 dialog = new CalendarDialog1();
        dialog.show(getSupportFragmentManager(), "CalendarDialog1");
    }

    public void showCalendarDialog2() {
        CalendarDialog2 dialog = new CalendarDialog2();
        dialog.show(getSupportFragmentManager(), "CalendarDialog2");
    }

    @Override
    public void onDialogPositiveClick(CalendarDialog1 dialog) {
        dateText.setText(dialog.getDate());
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(CalendarDialog1 dialog) {
        dialog.dismiss();
    }

    @Override
    public void onDialogPositiveClick(CalendarDialog2 dialog) {
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(CalendarDialog2 dialog) {
        dialog.dismiss();
    }
}