package com.example.freeplayandroidclient;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freeplayandroidclient.dataClasses.User;

import java.util.ArrayList;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> users;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView thumbnail;
        private final TextView username;
        private final Button button;
        public ViewHolder(View view) {
            super(view);
            button = view.findViewById(R.id.button);
            username = view.findViewById(R.id.username);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
        public ImageView getThumbnail() {
            return thumbnail;
        }
        public TextView getUserName() {
            return username;
        }
        public Button getButton() {
            return button;
        }
        public void setThumbnail(Bitmap bitmap) {
            thumbnail.setImageBitmap(bitmap);
        }
    }
    public static class User {
        private String id;
        private String name;
        private Bitmap thumbnail;
        private ViewHolder viewHolder;
        private OnClickListener onClickListener;
        public User(String id, String name) {
            this.name = name;
        }
        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public Bitmap getThumbnail() {
            return thumbnail;
        }
        public ViewHolder getViewHolder() {
            return viewHolder;
        }
        public OnClickListener getOnClickListener() {
            return onClickListener;
        }
        public void setViewHolder(ViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }
        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }
        public void setThumbnail(Bitmap thumbnail) {
            this.thumbnail = thumbnail;
            if (viewHolder != null) {
                viewHolder.setThumbnail(thumbnail);
            }
        }
        public interface OnClickListener {
            void onClick(View view);
        }
    }
    public UserAdapter() {
        this.users = new ArrayList<>();
    }
    public UserAdapter(List<User> users) {
        this.users = users;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_item, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        User user = users.get(position);
        user.setViewHolder(viewHolder);
        String userName = user.getName();
        viewHolder.getUserName().setText(userName);
        if (user.getThumbnail() != null) {
            viewHolder.setThumbnail(user.getThumbnail());
        }
        viewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.OnClickListener onClickListener = user.getOnClickListener();
                if (onClickListener != null) { onClickListener.onClick(view); }
            }
        });
    }
    @Override
    public int getItemCount() {
        return users.size();
    }
    public void setItems(List<User> users) {
        this.users.addAll(users);
        notifyDataSetChanged();
    }
    public void clearItems() {
        users.clear();
        notifyDataSetChanged();
    }
}