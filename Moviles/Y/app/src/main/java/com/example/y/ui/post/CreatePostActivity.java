package com.example.y.ui.post;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.y.R;
import com.example.y.databinding.ActivityCreatePostBinding;

public class CreatePostActivity extends AppCompatActivity {

    ActivityCreatePostBinding binding;

    private EditText etPostContent;
    private TextView tvCounter;
    private Button btnPost;
    String username;

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
        tvCounter = binding.tvCounter;
        etPostContent = binding.etPostContent;
    }

    private void setUpListeners() {
        btnPost.setOnClickListener(v -> {
            if (validation()) {

                finish();
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
        if (etPostContent.getText() != null && etPostContent.getText().toString().trim().length() == 0) {
            etPostContent.setError("Text cannot be empty");
            etPostContent.requestFocus();
            return false;
        }

        return true;
    }


}