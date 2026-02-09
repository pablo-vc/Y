package com.example.y.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.y.Notification;
import com.example.y.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;
    private Context context;

    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        Notification notification = notificationList.get(position);

        if (notification.getType().equals("POST")) {
            holder.textMessage.setText(notification.getFromUsername() + " ha compartido una publicación: \"" + notification.getContent() + "\"");
        } else {
            holder.textMessage.setText(notification.getFromUsername() + " ha empezado a seguirte");
        }

        holder.textDate.setText(notification.getCreatedAt());

        if (!notification.isRead()) {
            holder.textMessage.setTypeface(null, Typeface.BOLD);
            notification.setRead(true);
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView textMessage, textDate;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            textMessage = itemView.findViewById(R.id.tvMessage);
            textDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
