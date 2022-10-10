package com.example.freeplayandroidclient;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.CursorLoader;

import com.example.freeplayandroidclient.api.API;
import com.example.freeplayandroidclient.dataClasses.User;

import java.io.IOException;
import java.util.Objects;

public class RegistrationDialog extends DialogFragment implements View.OnClickListener {
    private Button cancelButton, signupButton;
    private EditText userName, userEmail,
            userPassword, userTelephone;
    private CheckBox userStatus;

    private Bitmap bitmap;
    private Button imageButton;
    private ImageView imageView;

    @Override
    public void onClick(View view) {
        if (view.getId() == cancelButton.getId()) {
            listener.onDialogNegativeClick(this);
        } else if (view.getId() == signupButton.getId()) {
            listener.onDialogPositiveClick(this);
        }
    }

    public interface RegistrationDialogListener {
        public void onDialogPositiveClick(RegistrationDialog dialog);
        public void onDialogNegativeClick(RegistrationDialog dialog);
    }
    RegistrationDialogListener listener;


    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (RegistrationDialogListener) context;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.registration, null);

        cancelButton = view.findViewById(R.id.d_cancel);
        signupButton = view.findViewById(R.id.d_signup);
        userName = view.findViewById(R.id.d_user_name);
        userEmail = view.findViewById(R.id.d_user_email);
        imageView = view.findViewById(R.id.d_image_data);
        userStatus = view.findViewById(R.id.d_user_status);
        imageButton = view.findViewById(R.id.d_image_button);
        userPassword = view.findViewById(R.id.d_user_password);
        userTelephone = view.findViewById(R.id.d_user_telephone);

        cancelButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        userName.setOnClickListener(this);
        userEmail.setOnClickListener(this);
        userPassword.setOnClickListener(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showFileChooser(); }});

        builder.setView(view);

        return builder.create();
    }
    public Bitmap getUserImage() {
        return bitmap;
    }
    public Boolean getUserStatus() {
        return userStatus.isChecked();
    }
    public String getUserName() {
        return userName.getText().toString();
    }
    public String getUserEmail() {
        return userEmail.getText().toString();
    }
    public String getUserPassword() {
        return userPassword.getText().toString();
    }
    public String getUserTelephone() {
        return userTelephone.getText().toString();
    }
    public User getUser() {
        return new User(
                getUserName(),
                getUserEmail(),
                getUserPassword(),
                getUserTelephone(),
                getUserStatus());
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*"); intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver contentResolver = Objects.requireNonNull(getContext()).getContentResolver();
        if (requestCode == 1 && resultCode == Activity.RESULT_OK &&
                data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException exception) { exception.printStackTrace(); }
        }
    }
}