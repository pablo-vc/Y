package com.example.y.ui.notifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.y.Notification;
import com.example.y.Post;
import com.example.y.R;
import com.example.y.Session;
import com.example.y.adapters.NotificationAdapter;
import com.example.y.data.Api;
import com.example.y.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    RecyclerView rv;
    NotificationAdapter adapter;
    FragmentNotificationsBinding binding;
    List<Notification> notificationList;

    int currentUserId = Session.getInstance().getUser().getId();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(getLayoutInflater());
        rv = binding.rvNotifications;
        initUI();
        loadNotifications();
        return binding.getRoot();
    }

    private void loadNotifications() {
            new Thread(() -> {
                List<Notification> notifications = Api.getNotifications(currentUserId);
                getActivity().runOnUiThread(() -> {
                    notificationList.clear();
                    notificationList.addAll(notifications);
                    adapter.notifyDataSetChanged();
                });
            }).start();

    }

    private void initUI() {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationList = new ArrayList<>();


        adapter = new NotificationAdapter(getContext(), notificationList);
        rv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}