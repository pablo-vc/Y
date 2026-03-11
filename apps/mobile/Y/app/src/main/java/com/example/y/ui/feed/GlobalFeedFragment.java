package com.example.y.ui.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.y.data.models.Post;
import com.example.y.adapters.PostAdapter;
import com.example.y.data.Api;
import com.example.y.databinding.FragmentGlobalFeedBinding;

import java.util.ArrayList;
import java.util.List;


public class GlobalFeedFragment extends Fragment {

    FragmentGlobalFeedBinding binding;
    private RecyclerView rv;
    private PostAdapter adapter;
    private List<Post> postList = new ArrayList<>();

    public GlobalFeedFragment() {
        loadPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGlobalFeedBinding.inflate(getLayoutInflater());
        initUI();
        loadPosts();
        return binding.getRoot();
    }

    private void loadPosts() {
        new Thread(() -> {
            try {
                List<Post> posts = Api.getPosts();
                postList.clear();
                postList.addAll(posts);
                getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void initUI() {
        rv = binding.rvGlobal;
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PostAdapter(getContext(), postList, false);
        rv.setAdapter(adapter);
    }
}