package com.example.freeplayandroidclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAuth();
    }

    private void checkAuth() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (!settings.getBoolean("auth", false)) {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }
    }
}
