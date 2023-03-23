package com.example.freeplayandroidclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.example.freeplayandroidclient.dataClasses.Notification;
import com.example.freeplayandroidclient.dataClasses.User;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DataStorage {
    public User currentUser;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    public DataStorage(Context context) {
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        editor = settings.edit(); //editor.putString("ID", "0");
        currentUser = getUser(settings.getString("ID", ""));
        //editor.putStringSet("user_id", new HashSet<>());
        //editor.putStringSet("notification_id", new HashSet<>());
        editor.apply();
    }
    public void saveCurrentUser(User user) {
        editor.putString("ID", user.getId());
        editor.putString("user_name_" + user.getId(), user.getName());
        editor.putString("user_email_" + user.getId(), user.getEmail());
        editor.putString("user_status_" + user.getId(), user.getStatus());
        editor.putString("user_password_" + user.getId(), user.getPassword());
        editor.putString("user_telephone_" + user.getId(), user.getTelephone());
        editor.apply();
    }
    public String getCurrentUserId() {
        return settings.getString("ID", "");
    }
    public User getCurrentUser() {
        String id = getCurrentUserId();
        return new User(id,
                settings.getString("user_name_" + id, ""),
                settings.getString("user_email_" + id, ""),
                settings.getString("user_status_" + id, ""),
                settings.getString("user_password_" + id, ""),
                settings.getString("user_telephone_" + id, ""));
    }
    public void saveUser(User user) {
        Set<String> set = settings.getStringSet("user_id", new HashSet<>());
        set.add(user.getId()); editor.putStringSet("user_id", set);
        editor.putString("user_name_" + user.getId(), user.getName());
        editor.putString("user_email_" + user.getId(), user.getEmail());
        editor.putString("user_status_" + user.getId(), user.getStatus());
        editor.putString("user_password_" + user.getId(), user.getPassword());
        editor.putString("user_telephone_" + user.getId(), user.getTelephone());
        editor.apply();
    }

    public void saveProtector(String p, String d) {
        editor.putString("user_" + d + "_protector_id", d);
        editor.apply();
    }

    public String getProtector(String d) {
        return settings.getString("user_" + d + "_protector_id", "");
    }

    public void saveNotification(Notification notification) {
        Set<String> set = settings.getStringSet("notification_id", new HashSet<>());
        set.add(notification.getId()); editor.putStringSet("notification_id", set);
        editor.putString("notification_message_" + notification.getId(), notification.getMessage());

    }

    public Notification getNotification(String id) {
        return new Notification(id, getNotificationMessage(id));
    }

    public User getUser(String id) {
        return new User(id, getUserName(id),
                getUserEmail(id), getUserPassword(id),
                getUserTelephone(id), getUserStatus(id));
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        Set<String> set = settings.getStringSet("user_id", new HashSet<>());
        System.out.println(getCurrentUserId());
        for (String id : set) {
            if (Objects.equals(id, getCurrentUserId())) continue;
            users.add(getUser(id));
        }
        return users;
    }

    public ArrayList<Notification> getNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();
        Set<String> set = settings.getStringSet("notification_id", new HashSet<>());
        for (String id : set) {
            notifications.add(getNotification(id));
        }
        return notifications;
    }

    public ArrayList<User> getDependants(String ID) {
        ArrayList<User> users = new ArrayList<>();
        Set<String> set = settings.getStringSet("user_id", new HashSet<>());
        for (String id : set) {
            if (ID.equals(getProtector(id))) users.add(getUser(id));
        }
        return users;
    }

    public String getUserName(String id) {
        return settings.getString("user_name_" + id, "");
    }

    public String getUserEmail(String id) {
        return settings.getString("user_email_" + id, "");
    }

    public String getUserStatus(String id) {
        return settings.getString("user_status_" + id, "");
    }

    public String getUserPassword(String id) {
        return settings.getString("user_password_" + id, "");
    }

    public String getUserTelephone(String id) {
        return settings.getString("user_telephone_" + id, "");
    }

    public String getNotificationMessage(String id) {
        return settings.getString("notification_message_" + id, "");
    }

    public String getUserProtectorId() {
        return "";
    }

    public Bitmap getUserImage(String id) {
        File dest = new File(Environment.getExternalStorageDirectory(), id + ".png");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        if (dest.exists()) return BitmapFactory.decodeFile(dest.getAbsolutePath(), options);
        else return null;
    }

    public void saveUserImage(User user, Bitmap bitmap) {
        String filename = user.getId() + ".png";
        File dest = new File(Environment.getExternalStorageDirectory(), filename);
        try {
            boolean fileCreated = dest.createNewFile();
            FileOutputStream fos = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush(); fos.close();
        } catch (Exception exception) { exception.printStackTrace(); }
    }

}
