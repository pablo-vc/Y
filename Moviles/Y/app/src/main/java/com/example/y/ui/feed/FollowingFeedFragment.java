package com.example.y.ui.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.y.Post;
import com.example.y.R;
import com.example.y.adapters.FeedPagerAdapter;
import com.example.y.adapters.PostAdapter;
import com.example.y.databinding.FragmentFollowingFeedBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class FollowingFeedFragment extends Fragment {

    FragmentFollowingFeedBinding binding;
    private RecyclerView rv;
    private PostAdapter adapter;
    private List<Post> postList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFollowingFeedBinding.inflate(getLayoutInflater());
        initUI();
        loadPosts();
        return binding.getRoot();
    }

    private void loadPosts() {
        postList.clear();

        postList.add(new Post(1, 1, "juan", "Hola mundo", "2 min"));

        postList.add(new Post(2, 2, "ana", "Primera publicación", "10 min"));
        postList.add(new Post(1, 1, "juan", "Hola mundo", "2 min"));

        postList.add(new Post(2, 2, "ana", "Primera publicación", "10 min"));
        postList.add(new Post(1, 1, "juan", "Hola mundo", "2 min"));

        postList.add(new Post(2, 2, "ana", "Primera publicación", "10 min"));
        postList.add(new Post(1, 1, "juan", "Hola mundo", "2 min"));

        postList.add(new Post(2, 2, "ana", "Primera publicación", "10 min"));

        adapter.notifyDataSetChanged();
    }

    private void initUI() {
        rv = binding.rvFollowing;
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PostAdapter(getContext(), postList, false);
        rv.setAdapter(adapter);
    }
}