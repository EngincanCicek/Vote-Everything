package com.example.voteeverything;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DURATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Splash ekranını göstermek için zamanlayıcı
        new Handler().postDelayed(() -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            if (auth.getCurrentUser() != null) {
                // Kullanıcı oturum açmışsa MainActivity'ye yönlendir
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
            } else {
                // Oturum yoksa SignInActivity'ye yönlendir
                Intent signInIntent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(signInIntent);
            }

            // SplashActivity'yi kapat
            finish();
        }, SPLASH_DURATION);
    }
}
