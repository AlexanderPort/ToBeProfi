package com.example.freeplayandroidclient;


import android.graphics.Bitmap;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.example.freeplayandroidclient.api.API;
import com.example.freeplayandroidclient.dataClasses.User;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthActivity extends Base implements RegistrationDialog.RegistrationDialogListener {
    EditText email, password;
    Button login, signup;
    CheckBox remember_me;
    TextView forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        password = findViewById(R.id.password_login);
        email = findViewById(R.id.email_login);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        remember_me = findViewById(R.id.remember_me);
        forget_password = findViewById(R.id.forget_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean remember = remember_me.isChecked();
                String email_text = email.getText().toString();
                String password_text = password.getText().toString();
                if (emailValidator(email_text)) {
                    api.searchUserByEmailAndPassword(
                            email_text, password_text,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getBoolean("status")) {
                                            if (remember) {
                                                User user = new User(
                                                        response.getString("userName"),
                                                        response.getString("userEmail"),
                                                        response.getString("userPassword"),
                                                        response.getString("userTelephone"),
                                                        response.getString("userStatus"));
                                                user.setId("0"); dataStorage.saveUser(user);
                                            }
                                        } else {
                                            Toast toast = Toast.makeText(getBaseContext(),
                                                    response.getString("message"),
                                                    Toast.LENGTH_LONG); toast.show();
                                        }
                                    } catch (JSONException exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            });
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegistrationDialog();
            }
        });
    }

    public void showRegistrationDialog() {
        RegistrationDialog dialog = new RegistrationDialog();
        dialog.show(getSupportFragmentManager(), "RegistrationDialog");
    }

    @Override
    public void onDialogPositiveClick(RegistrationDialog dialog) {
        User user = dialog.getUser();
        Bitmap bitmap = dialog.getUserImage();
        if (emailValidator(user.getEmail()) && telephoneValidator(user.getTelephone())) {
            api.postUserWithImage(user, bitmap, new API.OnResponseListener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    try {
                        JSONObject object = new JSONObject(new String(response.data));
                        if (object.getString("status").equals("exist")) {
                            Toast toast = Toast.makeText(getBaseContext(),
                                    "User with such email already exists!", Toast.LENGTH_LONG);
                            toast.show();
                        } else { dialog.dismiss(); }
                    } catch (JSONException exception) { exception.printStackTrace(); }
                }
                @Override
                public void onNetworkResponse(NetworkResponse response) {
                    if (response.statusCode == 200) {

                    }
                }
            });
        }
    }

    @Override
    public void onDialogNegativeClick(RegistrationDialog dialog) {
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