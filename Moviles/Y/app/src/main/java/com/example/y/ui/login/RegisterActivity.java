package com.example.y.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.y.ui.MainActivity;
import com.example.y.R;

public class RegisterActivity extends AppCompatActivity {

    EditText etUser, etEmail, etPassword,etPassword2;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initUI() {
        btnRegister = findViewById(R.id.btnRegister);
        etUser = findViewById(R.id.etUser);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
    }

    private void setUpListeners() {
        btnRegister.setOnClickListener(v -> {
            loginManagement();
        });

    }

    private void loginManagement() {
        Boolean valid = false;
        if (validation()) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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
            etPassword.requestFocus();
            return false;
        }
        if (etPassword2.getText() != null && etPassword2.getText().toString().trim().length() == 0) {
            etPassword2.setError("Password is required");
            etPassword2.requestFocus();
            return false;
        }
        if (etPassword.getText().toString().trim().length()<6){
            etPassword.setError("Password must have at least 6 characters");
            etPassword.requestFocus();
            return false;
        }
        if (etPassword.getText()!=etPassword2.getText()){
            etPassword2.setError("The passwords are different");
            etPassword2.requestFocus();
            return false;
        }

        if (etEmail.getText() != null && !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {
            return false;
        }

        return true;
    }

}