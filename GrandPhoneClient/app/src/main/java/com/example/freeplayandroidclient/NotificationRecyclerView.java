package com.example.freeplayandroidclient;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freeplayandroidclient.api.API;
import com.example.freeplayandroidclient.dataClasses.Notification;
import com.example.freeplayandroidclient.dataClasses.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class NotificationRecyclerView extends RecyclerView {
    private API api;
    private NotificationAdapter notificationAdapter;
    private DataStorage dataStorage;
    private List<NotificationAdapter.Notification> notifications;
    static private HashSet<String> ids = new HashSet<>();
    private void init() {
        api = new API(getContext());
        notifications = new ArrayList<>();
        setAdapter(new NotificationAdapter());
        dataStorage = new DataStorage(getContext());
        setLayoutManager(new LinearLayoutManager(getContext()));
    }
    public NotificationRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }
    public NotificationRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public NotificationRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void setAdapter(NotificationAdapter notificationAdapter) {
        super.setAdapter(notificationAdapter);
        this.notificationAdapter = notificationAdapter;
    }
    public void addNotification(Notification notification) {
        NotificationAdapter.Notification notificationAdapter =
                new NotificationAdapter.Notification(
                        notification.getId(), notification.getMessage());
        this.notifications.add(notificationAdapter);
        this.notificationAdapter.setItems(this.notifications);
        this.notificationAdapter.notifyDataSetChanged();
    }
    public void addNotifications(List<Notification> notifications) {
        this.notifications = new ArrayList<>();
        for (Notification notification : notifications) {

            NotificationAdapter.Notification notificationAdapter =
                    new NotificationAdapter.Notification(
                            notification.getId(), notification.getMessage());
            this.notifications.add(notificationAdapter);
        }
        this.notificationAdapter.setItems(this.notifications);
        this.notificationAdapter.notifyDataSetChanged();
    }
}
