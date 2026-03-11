package com.example.y.ui.post;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.y.R;
import com.example.y.data.models.Session;
import com.example.y.data.Api;
import com.example.y.databinding.ActivityCreatePostBinding;
import com.example.y.ui.feed.FeedFragment;

public class CreatePostActivity extends AppCompatActivity {

    ActivityCreatePostBinding binding;
    private EditText etPostContent;
    private TextView tvCounter;
    private Button btnPost;
    private ImageButton btnBack;
    String username;
    String content;
    int currentUserId = Session.getInstance().getUserId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getData();
        initUI();
        setUpListeners();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("username");
        }

    }

    private void initUI() {
        btnPost = binding.btnPost;
        btnBack = binding.btnBack;
        tvCounter = binding.tvCounter;
        etPostContent = binding.etPostContent;
    }

    private void setUpListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
        });
        btnPost.setOnClickListener(v -> {
            if (validation()) {
                new Thread(() -> {
                    boolean success = Api.createPost(currentUserId, content);

                    runOnUiThread(() -> {
                        if (success) {
                            Toast.makeText(this, getString(R.string.posted_correctly), Toast.LENGTH_SHORT).show();

                            finish();
                        } else {
                            Toast.makeText(this, getString(R.string.error_posting), Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }).start();
            }
        });
        etPostContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCounter.setText(s.length() + " / 280");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private Boolean validation() {
        content = etPostContent.getText() != null ? etPostContent.getText().toString().trim() : "";
        if (content.isEmpty()) {
            etPostContent.setError(getString(R.string.cannot_be_empty));
            etPostContent.requestFocus();
            return false;
        }

        return true;
    }


}