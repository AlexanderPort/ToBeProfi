package com.example.freeplayandroidclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.example.freeplayandroidclient.dataClasses.User;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DataStorage {
    public User currentUser;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    public DataStorage(Context context) {
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        editor = settings.edit(); currentUser = getUser("0");
        //editor.putStringSet("id", new HashSet<>());
    }
    public void saveUser(User user) {
        Set<String> set = settings.getStringSet("id", new HashSet<>());
        set.add(user.getId()); editor.putStringSet("id", set);
        editor.putString("user_name_" + user.getId(), user.getName());
        editor.putString("user_email_" + user.getId(), user.getEmail());
        editor.putString("user_status_" + user.getId(), user.getStatus());
        editor.putString("user_password_" + user.getId(), user.getPassword());
        editor.putString("user_telephone_" + user.getId(), user.getTelephone());
    }

    public User getUser(String id) {
        return new User(getUserName(id),
                getUserEmail(id), getUserPassword(id),
                getUserTelephone(id), getUserStatus(id));
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        Set<String> set = settings.getStringSet("id", new HashSet<>());
        for (String id : set) {
            if (id == "0") continue;
            users.add(getUser(id));
        }
        return users;
    }

    public ArrayList<User> getDependants() {
        ArrayList<User> users = new ArrayList<>();
        Set<String> set = settings.getStringSet("id", new HashSet<>());
        for (String id : set) {
            if (id == "0") continue;
            if (getUserStatus(id).equals("P")) continue;
            users.add(getUser(id));
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

    public Bitmap getUserImage(String id) {
        File dest = new File(Environment.getExternalStorageDirectory(), id + ".png");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(dest.getAbsolutePath(), options);
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
