package com.example.voteeverything.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.voteeverything.R;
import com.example.voteeverything.StaticShorCut;
import com.example.voteeverything.dao.DummyDao;
import com.example.voteeverything.model.Post;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private Button buttonSave;

    private DummyDao dummyDao;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        buttonSave = findViewById(R.id.buttonSave);

        dummyDao = new DummyDao();
        db = FirebaseFirestore.getInstance();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewPost();
            }
        });
    }

    private void saveNewPost() {
        String title = editTextTitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(PostActivity.this, "Başlık ve içerik boş olamaz.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Yeni post nesnesini oluştur
        Post newPost = new Post(StaticShorCut.generateRandomString(8),
                StaticShorCut.generateRandomString(8),
                title, content, 0.0, null, 0);

        // DummyDao üzerinden Firestore'a kaydet
        dummyDao.createPost(newPost)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(PostActivity.this, "Yeni post başarıyla kaydedildi.", Toast.LENGTH_SHORT).show();
                    finish(); // Aktiviteyi kapat
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PostActivity.this, "Post kaydedilirken bir hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
