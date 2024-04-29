package com.example.voteeverything.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.voteeverything.R;
import com.google.android.material.card.MaterialCardView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // SharedPreferences nesnesini oluştur
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        // CardView'i tanımla
        CardView cardView = findViewById(R.id.cardViewLoggin);

        // CardView'e tıklama dinleyicisi ekle
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SharedPreferences'teki isLoggedIn değerini true yap
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                // Kullanıcıya geri bildirim ver
                Toast.makeText(LoginActivity.this, "Oturum açıldı!", Toast.LENGTH_SHORT).show();

                // Sonraki işlemi yap (örneğin ana ekrana git)
                goToMainActivity();
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // LoginActivity'yi kapat
    }

}
