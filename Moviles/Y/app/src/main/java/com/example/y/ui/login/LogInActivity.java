package com.example.y.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.y.R;
import com.example.y.databinding.ActivityLogInBinding;
import com.example.y.ui.MainActivity;

public class LogInActivity extends AppCompatActivity {

    EditText etUser, etPassword;
    Button btnLogin;
    TextView tvRegister, tvForgotPassword;
    ActivityLogInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
        setUpListeners();
    }

    private void initUI() {
        btnLogin = binding.btnLogin;
        etUser=binding.etUser;
        etPassword=binding.etPassword;
        tvForgotPassword = binding.tvForgotPassword;
        tvRegister = binding.tvRegister;
    }
    private void setUpListeners() {
        btnLogin.setOnClickListener(v -> {
            loginManagement();
        });
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
        });
        tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(LogInActivity.this, ForgotPasswordActivity.class));
        });
    }

    private void loginManagement() {
        if (validation()) {
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private Boolean validation() {
        if (etUser.getText() != null && etUser.getText().toString().trim().length() == 0) {
            etUser.setError("Username is required");
            etUser.requestFocus();
            return false;
        }
        if (etPassword.getText() != null && etPassword.getText().toString().trim().length() == 0) {
            etPassword.setError("Password is required");
            etUser.requestFocus();
            return false;
        }
        return true;
    }
}