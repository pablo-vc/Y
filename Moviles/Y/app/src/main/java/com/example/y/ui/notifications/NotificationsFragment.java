package com.example.y.ui.notifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.y.Notification;
import com.example.y.R;
import com.example.y.adapters.NotificationAdapter;
import com.example.y.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    RecyclerView rv;
    NotificationAdapter adapter;
    FragmentNotificationsBinding binding;
    List<Notification> notificationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(getLayoutInflater());
        rv = binding.rvNotifications;
        initUI();
        return binding.getRoot();
    }

    private void initUI() {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationList = new ArrayList<>();

        // Datos de ejemplo (simulan la API)
        notificationList.add(new Notification(
                1, "POST", "juan",
                "Hoy es un gran día", "Hace 5 min"));

        notificationList.add(new Notification(
                2, "FOLLOW", "ana",
                null, "Hace 1 h"));

        adapter = new NotificationAdapter(getContext(), notificationList);
        rv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}