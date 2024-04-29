package com.example.voteeverything.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.voteeverything.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // SharedPreferences'ten oturum durumunu kontrol et
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // Oturum açıldıysa MainActivty'ye yönlendir
        // Oturum açılmadıysa LoginActivity'e yönlendir
        Class<?> destinationActivity = isLoggedIn ? MainActivity.class : LoginActivity.class;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, destinationActivity));
                finish();
            }
        }, 2000); // 2 saniye bekleyip sonra yönlendir
    }
}
