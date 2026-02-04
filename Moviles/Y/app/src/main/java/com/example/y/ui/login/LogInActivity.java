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

import com.example.y.ApiRest;
import com.example.y.ui.MainActivity;
import com.example.y.R;

public class LogInActivity extends AppCompatActivity {
    EditText etUser, etPassword;
    Button btnLogin;
    TextView tvRegister, tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            initUI();
            setUpListeners();
            return insets;

        });
    }

    private void initUI() {
        btnLogin = findViewById(R.id.btnLogin);
        etUser=findViewById(R.id.etUser);
        etPassword=findViewById(R.id.etPassword);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);
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
        Boolean valid = false;
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