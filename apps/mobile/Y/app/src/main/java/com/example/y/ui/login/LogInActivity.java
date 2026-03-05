package com.example.y.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.y.Session;
import com.example.y.User;
import com.example.y.data.Api;
import com.example.y.databinding.ActivityLogInBinding;
import com.example.y.ui.MainActivity;

public class LogInActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister, tvForgotPassword;
    String email, password;
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
        etEmail = binding.etEmailLogin;
        etPassword = binding.etPassword;
        tvRegister = binding.tvRegister;
    }

    private void setUpListeners() {
        btnLogin.setOnClickListener(v -> {
            loginManagement();
        });
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
        });
    }

    private void loginManagement() {
        if (validation()) {
            new Thread(() -> {
                User user = Api.login(email, password);
                runOnUiThread(() -> {
                    if (user != null) {
                        // Guardamos usuario en Session singleton
                        Session.getInstance().setUser(user);
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LogInActivity.this, "Email o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        }
    }

    private Boolean validation() {
        email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        if (email.isEmpty()) {
            etEmail.setError("Email requerido");
            etEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Contraseña requerida");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }
}