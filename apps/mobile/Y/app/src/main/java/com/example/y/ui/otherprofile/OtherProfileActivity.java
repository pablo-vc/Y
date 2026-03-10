package com.example.y.ui.otherprofile;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.y.data.models.Post;
import com.example.y.R;
import com.example.y.data.models.Session;
import com.example.y.data.models.User;
import com.example.y.adapters.PostAdapter;
import com.example.y.data.Api;
import com.example.y.databinding.ActivityOtherProfileBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OtherProfileActivity extends AppCompatActivity {

    ActivityOtherProfileBinding binding;

    private RecyclerView rv;
    private PostAdapter adapter;
    private List<Post> postList = new ArrayList<>();
    private TabLayout tabLayout;
    private Button btnFollow;
    TextView tvUsername, tvBio, tvFollowers, tvFollowing;
    private int userId;
    private boolean isFollowing = false;
    int currentUserId = Session.getInstance().getUserId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityOtherProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userId = getIntent().getIntExtra("id_user", -1);

        if (userId != -1) {
            initUI();
            loadUserProfile();
            loadPosts();
            setUpListeners();
        } else {
            Toast.makeText(this, "ID de usuario no encontrado", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadUserProfile() {
        new Thread(() -> {
            User user = Api.getUserById(userId);
            runOnUiThread(() -> {
                if (user != null) {
                    tvUsername.setText(user.getUsername());
                    tvBio.setText(user.getBio());
                    checkFollowStatus(userId);
                    loadNumbers();
                }
            });
        }).start();
    }

    private void loadNumbers() {
        new Thread(() -> {
            int Following = Api.getFollowingCount(userId);
            int Followers = Api.getFollowerCount(userId);
            runOnUiThread(() -> {
                tvFollowing.setText(Html.fromHtml("<b>" + getString(R.string.following) + ": </b>" + Following));
                tvFollowers.setText(Html.fromHtml("<b>" + getString(R.string.followers) + ": </b>" + Followers));
            });
        }).start();
    }

    private void loadPosts() {
        new Thread(() -> {
            List<Post> posts = Api.getUserPosts(userId);
            runOnUiThread(() -> {
                postList.clear();
                postList.addAll(posts);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void initUI() {
        tabLayout = binding.tabLayout;
        tabLayout.addTab(tabLayout.newTab().setText("Posts"));

        rv = binding.rvOwnPosts;
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PostAdapter(this, postList, false);
        rv.setAdapter(adapter);
        btnFollow = binding.btnFollow;
        tvUsername = binding.tvUsername;
        tvBio = binding.tvBio;
        tvFollowers = binding.tvFollowers;
        tvFollowing = binding.tvFollowing;
    }

    private void setUpListeners() {
        btnFollow.setOnClickListener(v -> {
            Log.i("PRUEBA", "hola");
            if (isFollowing) {
                Log.i("PRUEBA", "siiiiiiii");
                unfollowUser(currentUserId, userId);
            } else {
                Log.i("PRUEBA", "noooo");
                followUser(currentUserId, userId);
            }
        });
    }

    private void checkFollowStatus(int userId) {
        new Thread(() -> {
            isFollowing = Api.isFollowing(currentUserId, userId);
            runOnUiThread(() -> {
                if (isFollowing) {
                    Log.i("SIGUIENDO", "si");
                    btnFollow.setText("Dejar de seguir");
                } else {
                    Log.i("SIGUIENDO", "no");
                    btnFollow.setText("Seguir");
                }
            });
        }).start();
    }

    private void followUser(int currentUserId, int userId) {
        new Thread(() -> {
            String result = Api.followUser(currentUserId, userId);
            runOnUiThread(() -> {
                Toast.makeText(this, "Result: " + result, Toast.LENGTH_SHORT).show();
                if ("success".equals(result)) {
                    Toast.makeText(this, "Siguiendo", Toast.LENGTH_SHORT).show();
                    checkFollowStatus(userId);
                }
            });
        }).start();
    }

    private void unfollowUser(int currentUserId, int userId) {
        new Thread(() -> {
            String result = Api.unfollowUser(currentUserId, userId);
            runOnUiThread(() -> {
                Toast.makeText(this, "Result: " + result, Toast.LENGTH_SHORT).show();
                if ("success".equals(result)) {
                    Toast.makeText(this, "Dejado de seguir", Toast.LENGTH_SHORT).show();
                    checkFollowStatus(userId);
                }
            });
        }).start();
    }
}