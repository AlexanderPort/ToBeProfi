package com.example.freeplayandroidclient;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.example.freeplayandroidclient.api.API;
import com.example.freeplayandroidclient.dataClasses.User;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneContactsActivity extends Base implements ContactDialog.ContactDialogListener {
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
        userRecyclerView.addUsers(dataStorage.getUsers());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactDialog();
            }
        });
    }

    public void showContactDialog() {
        ContactDialog dialog = new ContactDialog();
        dialog.show(getSupportFragmentManager(), "ContactDialog");
    }

    @Override
    public void onDialogPositiveClick(ContactDialog dialog) {
        User user = dialog.getUser();
        Bitmap bitmap = dialog.getUserImage();
        if (telephoneValidator(user.getTelephone()) && false) {
            api.postUserWithImage(user, bitmap, new API.OnResponseListener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    try {
                        JSONObject object = new JSONObject(new String(response.data));
                        dialog.dismiss();
                    } catch (JSONException exception) { exception.printStackTrace(); }
                }
                @Override
                public void onNetworkResponse(NetworkResponse response) {
                    if (response.statusCode == 200) {

                    }
                }
            });

        }
        dataStorage.saveUserImage(user, bitmap);
        userRecyclerView.addUser(user);
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(ContactDialog dialog) {
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