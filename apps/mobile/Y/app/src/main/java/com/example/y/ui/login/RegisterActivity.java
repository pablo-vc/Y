package com.example.y.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.y.R;
import com.example.y.Session;
import com.example.y.User;
import com.example.y.data.Api;
import com.example.y.databinding.ActivityRegisterBinding;
import com.example.y.ui.MainActivity;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    String username;
    String email;
    String password;
    String password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpListeners();
    }

    private void setUpListeners() {
        binding.btnRegister.setOnClickListener(v -> {
            handleRegister();
        });
    }

    private void handleRegister() {
        username = binding.etUsername.getText().toString().trim();
        email = binding.etEmail.getText().toString().trim();
        password = binding.etPassword.getText().toString().trim();
        password2 = binding.etPassword2.getText().toString().trim();

        // Validaciones simples
        if (username.isEmpty()) {
            binding.etUsername.setError("Username requerido");
            binding.etUsername.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            binding.etEmail.setError("Email requerido");
            binding.etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            binding.etPassword.setError("Contraseña requerida");
            binding.etPassword.requestFocus();
            return;
        }
        if (!password.equals(password2)) {
            binding.etPassword2.setError("Las contraseñas no coinciden");
            binding.etPassword2.requestFocus();
            return;
        }

        // Ejecutar registro en hilo aparte
        new Thread(() -> {
            String result = Api.register(username, email, password);

            if (result.equals("success")) {
                User user = Api.login(email, password);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    if (user != null) {
                        Session.getInstance().setUser(user);
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Email o contraseña incorrecta", Toast.LENGTH_SHORT).show();
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