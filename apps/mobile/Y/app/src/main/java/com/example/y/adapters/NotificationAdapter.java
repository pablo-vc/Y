package com.example.y.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.y.data.models.Notification;
import com.example.y.R;
import com.example.y.data.models.Session;
import com.example.y.ui.otherprofile.OtherProfileActivity;

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
        holder.setNotification(notification);

        holder.textMessage.setText(String.format("%s%s", notification.getFromUsername(), R.string.started_following));

        holder.textDate.setText(notification.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView textMessage, textDate;

        Notification currentNotification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            textMessage = itemView.findViewById(R.id.tvMessage);
            textDate = itemView.findViewById(R.id.tvDate);

            itemView.setOnClickListener(v -> {
                if (currentNotification.getId_follower() != Session.getInstance().getUserId()) {
                    Intent intent = new Intent(itemView.getContext(), OtherProfileActivity.class);
                    intent.putExtra("id_user", currentNotification.getId_follower());
                    itemView.getContext().startActivity(intent);
                }
            });

        }

        public void setNotification(Notification notification) {
            this.currentNotification = notification;
        }

    }
}
