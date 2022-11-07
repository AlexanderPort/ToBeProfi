package com.example.freeplayandroidclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.example.freeplayandroidclient.api.API;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends Base {
    private static enum PD {
        Dependant, Protector
    }
    private PD pd;
    private Chip protectorChip;
    private Chip dependantChip;
    private EditText searchView;
    private Button searchButton;
    private UserRecyclerView userRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.search);
        searchButton = findViewById(R.id.search_button);
        protectorChip = findViewById(R.id.protectorChip);
        dependantChip = findViewById(R.id.dependantChip);
        userRecyclerView = findViewById(R.id.userRecyclerView);

        protectorChip.setAlpha(0.9f); pd = PD.Protector;

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = searchView.getText().toString();
                if (!name.isEmpty()) {
                    api.searchUsersName(name, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            /*
                            try {
                                JSONArray data = response.getJSONArray("users");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            */

                        }
                    });
                }

            }
        });

        protectorChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                protectorChip.setAlpha(0.9f);
                dependantChip.setAlpha(0.3f);
                pd = PD.Protector;
            }
        });

        dependantChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                protectorChip.setAlpha(0.3f);
                dependantChip.setAlpha(0.9f);
                pd = PD.Dependant;
            }
        });


    }

}