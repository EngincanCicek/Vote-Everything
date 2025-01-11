package com.example.voteeverything;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.voteeverything.ViewModels.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Bind Views
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        Button registerButton = findViewById(R.id.registerButton);
        TextView loginTextView = findViewById(R.id.loginTextView);

        // Observe ViewModel for registration status
        viewModel.getRegistrationStatus().observe(this, status -> {
            if (status.equals("Registration successful")) {
                Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Snackbar.make(findViewById(R.id.main), status, Snackbar.LENGTH_LONG).show();
            }
        });

        // Register Button Logic
        registerButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            // Check for empty fields
            if (email.isEmpty()) {
                emailEditText.setError("Email is required");
                emailEditText.requestFocus();
                return;
            }

            // Check email format
            if (!isValidEmail(email)) {
                emailEditText.setError("Invalid email format. Use a valid Gmail or Hotmail address.");
                emailEditText.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                passwordEditText.setError("Password is required");
                passwordEditText.requestFocus();
                return;
            }

            // Check password length
            if (password.length() < 8) {
                passwordEditText.setError("Password must be at least 8 characters");
                passwordEditText.requestFocus();
                return;
            }

            // Check for password mismatch
            if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Passwords do not match");
                confirmPasswordEditText.requestFocus();
                return;
            }

            // Proceed with registration
            viewModel.registerUser(email, password);
        });

        // Navigate to Sign In Activity
        loginTextView.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
            finish();
        });
    }

    private boolean isValidEmail(String email) {
        // Check if the email contains @ and ends with gmail.com or hotmail.com
        return email.contains("@") && (email.endsWith("gmail.com") || email.endsWith("hotmail.com"));
    }
}
