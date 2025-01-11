package com.example.voteeverything;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.voteeverything.ViewModels.SignInViewModel;
import com.example.voteeverything.models.User;
import com.google.android.material.snackbar.Snackbar;

public class SignInActivity extends AppCompatActivity {

    private SignInViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        // Bind Views
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button signInButton = findViewById(R.id.signInButton);
        Button registerButton = findViewById(R.id.registerButton);

        // Observe ViewModel for sign-in status
        viewModel.getSignInStatus().observe(this, status -> {
            if ("Sign-in successful".equals(status)) {
                viewModel.getUserLiveData().observe(this, user -> {
                    if (user != null) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                Snackbar.make(findViewById(android.R.id.content), status, Snackbar.LENGTH_LONG).show();
            }
        });

        // Sign-in logic
        signInButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "Email and password cannot be empty", Snackbar.LENGTH_LONG).show();
                return;
            }

            if (!email.contains("@") || (!email.endsWith("gmail.com") && !email.endsWith("hotmail.com"))) {
                Snackbar.make(findViewById(android.R.id.content), "Invalid email format", Snackbar.LENGTH_LONG).show();
                return;
            }

            viewModel.signInWithEmail(email, password);
        });

        // Register button logic
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
