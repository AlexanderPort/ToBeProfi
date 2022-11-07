package com.example.freeplayandroidclient;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.freeplayandroidclient.dataClasses.User;

import java.io.IOException;
import java.util.Objects;

public class ContactDialog extends DialogFragment implements View.OnClickListener {
    private EditText userName, userEmail, userPassword,
            userPasswordConfirm, userTelephone;
    private Button cancelButton, createButton;
    private Spinner userStatus;
    private Boolean choose;
    private Bitmap bitmap;
    private Button imageButton;
    private ImageView imageView;

    @Override
    public void onClick(View view) {
        if (view.getId() == cancelButton.getId()) {
            listener.onDialogNegativeClick(this);
        } else if (view.getId() == createButton.getId()) {
            listener.onDialogPositiveClick(this);
        }
    }

    public interface ContactDialogListener {
        public void onDialogPositiveClick(ContactDialog dialog);
        public void onDialogNegativeClick(ContactDialog dialog);
    }
    ContactDialogListener listener;


    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ContactDialogListener) context;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.contact_dialog, null);

        cancelButton = view.findViewById(R.id.cancel_id);
        createButton = view.findViewById(R.id.create_id);
        userName = view.findViewById(R.id.username_id);
        userEmail = view.findViewById(R.id.email_id);
        imageView = view.findViewById(R.id.image_id);
        userStatus = view.findViewById(R.id.status_id);
        imageButton = view.findViewById(R.id.image_button_id);
        userPassword = view.findViewById(R.id.password_id);
        userTelephone = view.findViewById(R.id.telephone_id);
        userPasswordConfirm = view.findViewById(R.id.password_confirm_id);

        userStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                       int selectedItemPosition, long selectedId) {
                choose = Objects.equals(getResources().getStringArray(R.array.spinner1)[selectedItemPosition], "Protector");
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showFileChooser(); }});

        cancelButton.setOnClickListener(this);
        createButton.setOnClickListener(this);

        builder.setView(view);

        return builder.create();
    }
    public Bitmap getUserImage() {
        return bitmap;
    }
    public String getUserName() {
        return userName.getText().toString();
    }
    public String getUserEmail() {
        return userEmail.getText().toString();
    }
    public String getUserStatus() {
        return choose ? "P" : "D";
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