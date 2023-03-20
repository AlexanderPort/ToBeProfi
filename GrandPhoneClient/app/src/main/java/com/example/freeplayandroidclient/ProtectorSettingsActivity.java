package com.example.freeplayandroidclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.example.freeplayandroidclient.api.API;
import com.example.freeplayandroidclient.dataClasses.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class ProtectorSettingsActivity extends Base implements ContactDialog2.ContactDialogListener {
    private Button addButton;
    private Button backButton;
    private UserRecyclerView userRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_contacts);
        addButton = findViewById(R.id.add_button);
        backButton = findViewById(R.id.back_button);
        userRecyclerView = findViewById(R.id.userRecyclerView);
        userRecyclerView.addUsers(dataStorage.getDependants(
                dataStorage.getCurrentUserId()));
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
        ContactDialog2 dialog = new ContactDialog2();
        dialog.show(getSupportFragmentManager(), "ContactDialog2");
    }

    @Override
    public void onDialogPositiveClick(ContactDialog2 dialog) {
        String userName = dialog.getUserName();
        String userEmail = dialog.getUserEmail();
        String userPassword = dialog.getUserPassword();
        final String[] id = {UUID.randomUUID().toString()};
        id[0] = id[0].replace('x', '-');
        if (true) {
            api.postPD(id[0], dataStorage.currentUser.getId(),
                    userName, userEmail, userPassword,
                    new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("status").equals("created")) {
                            String id = response.getString("id");
                            String telephone = response.getString("telephone");
                            User user = new User(id, userName, userEmail,
                                    userPassword, telephone, "D");
                            dataStorage.saveUser(user);
                            userRecyclerView.addUser(user);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    } catch (JSONException exception) { exception.printStackTrace(); }
                }
            });
        }
    }

    @Override
    public void onDialogNegativeClick(ContactDialog2 dialog) {
        dialog.dismiss();
    }


    private boolean emailValidator(String email) {
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            Toast.makeText(this, "Email address is invalid!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean telephoneValidator(String telephone) {
        if (!telephone.isEmpty() && Patterns.PHONE.matcher(telephone).matches()) {
            return true;
        } else {
            Toast.makeText(this, "Telephone number is invalid!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}