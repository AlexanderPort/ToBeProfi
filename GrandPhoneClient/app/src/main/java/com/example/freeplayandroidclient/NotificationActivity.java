package com.example.freeplayandroidclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.freeplayandroidclient.dataClasses.Notification;

import java.util.UUID;

public class NotificationActivity extends Base implements NotificationDialog.NotificationDialogListener {
    private Button addButton;
    private Button backButton;
    private NotificationRecyclerView notificationRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        addButton = findViewById(R.id.add_button);
        backButton = findViewById(R.id.back_button);
        notificationRecyclerView = findViewById(R.id.notificationRecyclerView);
        notificationRecyclerView.addNotifications(dataStorage.getNotifications());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactDialog();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showContactDialog() {
        NotificationDialog dialog = new NotificationDialog();
        dialog.show(getSupportFragmentManager(), "NotificationDialog");
    }

    @Override
    public void onDialogPositiveClick(NotificationDialog dialog) {
        Notification notification = dialog.getNotification();
        final String[] id = {UUID.randomUUID().toString()};
        id[0] = id[0].replace('x', '-');
        notification.setId(id[0]);
        dataStorage.saveNotification(notification);
        notificationRecyclerView.addNotification(notification);
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(NotificationDialog dialog) {
        dialog.dismiss();
    }
}