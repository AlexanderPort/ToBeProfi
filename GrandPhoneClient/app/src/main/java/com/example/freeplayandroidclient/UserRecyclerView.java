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
import com.example.freeplayandroidclient.dataClasses.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class UserRecyclerView extends RecyclerView {
    private API api;
    private UserAdapter userAdapter;
    private DataStorage dataStorage;
    private List<UserAdapter.User> users;
    static private HashSet<String> ids = new HashSet<>();
    private void init() {
        api = new API(getContext());
        users = new ArrayList<>();
        setAdapter(new UserAdapter());
        dataStorage = new DataStorage(getContext());
        setLayoutManager(new LinearLayoutManager(getContext()));
    }
    public UserRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }
    public UserRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public UserRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void setAdapter(UserAdapter userAdapter) {
        super.setAdapter(userAdapter);
        this.userAdapter = userAdapter;
    }
    public void addUser(User user) {
        System.out.println(user.getId());
        if (ids.contains(user.getId())) return;
        else ids.add(user.getId());
        UserAdapter.User.OnClickListener onClickListener =
                new UserAdapter.User.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = getContext();

                    }
                };
        UserAdapter.User userAdapter = new UserAdapter.User(user.getName());
        userAdapter.setThumbnail(dataStorage.getUserImage(user.getId()));
        userAdapter.setOnClickListener(onClickListener);
        users.add(userAdapter);
        this.userAdapter.setItems(this.users);
        this.userAdapter.notifyDataSetChanged();
    }
    public void addUsers(List<User> users) {
        for (User user : users) this.addUser(user);
    }
}
