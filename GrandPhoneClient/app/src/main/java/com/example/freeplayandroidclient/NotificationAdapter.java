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


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification> notifications;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView message;
        private final ImageButton button;
        public ViewHolder(View view) {
            super(view);
            message = view.findViewById(R.id.message);
            button = view.findViewById(R.id.button);
        }
        public TextView getMessage() {
            return message;
        }
        public ImageButton getButton() {
            return button;
        }
    }
    public static class Notification {
        private String id;
        private String message;
        private ViewHolder viewHolder;
        private OnClickListener onClickListener;
        public Notification(String id, String message) {
            this.message = message;
        }
        public String getId() {
            return id;
        }
        public String getMessage() {
            return message;
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

        public interface OnClickListener {
            void onClick(View view);
        }
    }
    public NotificationAdapter() {
        this.notifications = new ArrayList<>();
    }
    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_item, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Notification notification = notifications.get(position);
        notification.setViewHolder(viewHolder);
        String message = notification.getMessage();
        viewHolder.getMessage().setText(message);
        viewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification.OnClickListener onClickListener = notification.getOnClickListener();
                if (onClickListener != null) { onClickListener.onClick(view); }
            }
        });
    }
    @Override
    public int getItemCount() {
        return notifications.size();
    }
    public void setItems(List<Notification> notifications) {
        this.notifications.addAll(notifications);
        notifyDataSetChanged();
    }
    public void clearItems() {
        notifications.clear();
        notifyDataSetChanged();
    }
}
