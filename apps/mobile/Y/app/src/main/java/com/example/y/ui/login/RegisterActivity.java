package com.example.y.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.y.R;
import com.example.y.data.models.Session;
import com.example.y.data.models.User;
import com.example.y.data.Api;
import com.example.y.databinding.ActivityRegisterBinding;
import com.example.y.ui.MainActivity;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    String username;
    String email;
    String password;
    String password2;
    EditText etUsername, etEmail, etPassword, etPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
        setUpListeners();
    }

    private void initUI() {
        etUsername = binding.etUsername;
        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
        etPassword2 = binding.etPassword2;
    }

    private void setUpListeners() {
        binding.btnRegister.setOnClickListener(v -> {
            handleRegister();
        });
        binding.tvLogin.setOnClickListener(v -> {
            finish();
        });
    }

    private void handleRegister() {
        username = etUsername.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        password2 = etPassword2.getText().toString().trim();

        if (username.isEmpty()) {
            etUsername.setError(getString(R.string.cannot_be_empty));
            etUsername.requestFocus();
            return;
        }
        if (!User.validateUsername(username)) {
            etUsername.setError(getString(R.string.invalid_username));
            etUsername.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.cannot_be_empty));
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.cannot_be_empty));
            etPassword.requestFocus();
            return;
        }
        if (!password.equals(password2)) {
            etPassword2.setError(getString(R.string.passwords_doesnt_match));
            etPassword2.requestFocus();
            return;
        }

        new Thread(() -> {
            String result = Api.register(username, email, password);

            if (result.equals("success")) {
                User user = Api.login(email, password);
                runOnUiThread(() -> {
                    Toast.makeText(this, getString(R.string.registered_succesfully), Toast.LENGTH_SHORT).show();
                    if (user != null) {
                        Session.getInstance().setUser(user);
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Unexpected error", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }
}