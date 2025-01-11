package com.example.voteeverything;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.voteeverything.ViewModels.CreatePostViewModel;
import com.example.voteeverything.models.Comment;
import com.example.voteeverything.models.Post;
import com.example.voteeverything.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CreatePostActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;

    private CreatePostViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // ViewModel Bağlama
        viewModel = new ViewModelProvider(this).get(CreatePostViewModel.class);

        // Firestore başlat
        //firestore = FirebaseFirestore.getInstance();
        // Rastgele veri oluştur ve kaydet todo sil beni! bu test için
        // generateAndSaveSampleData();


        EditText postTitleEditText = findViewById(R.id.postTitleEditText);
        EditText postDescriptionEditText = findViewById(R.id.postDescriptionEditText);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button savePostButton = findViewById(R.id.savePostButton);

        savePostButton.setOnClickListener(v -> {
            String title = postTitleEditText.getText().toString().trim();
            String description = postDescriptionEditText.getText().toString().trim();
            float rating = ratingBar.getRating() * 2;

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Title and Description cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = FirebaseAuth.getInstance().getCurrentUser() != null
                    ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                    : null;

            if (userId == null) {
                Toast.makeText(this, "No user is logged in. Please log in to create a post.", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.createPost(userId, title, description, rating, new CreatePostViewModel.OnPostCreatedListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(CreatePostActivity.this, "Post Created Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Exception e) {
                    if (e.getMessage().contains("A post with this title already exists")) {
                        Snackbar.make(savePostButton, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(savePostButton, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        });

    }
    private void generateAndSaveSampleData() { // todo sil beni!
        // Kullanıcıları oluştur
        User user1 = new User("user1", "Kullanıcı 1", "password1", null, null);
        User user2 = new User("user2", "Kullanıcı 2", "password2", null, null);

        // Kullanıcıları Firestore'a kaydet
        firestore.collection("users").document(user1.getUserId()).set(user1)
                .addOnSuccessListener(aVoid -> Log.d("CreatePostActivity", "User1 kaydedildi"))
                .addOnFailureListener(e -> Log.e("CreatePostActivity", "User1 kaydedilemedi", e));

        firestore.collection("users").document(user2.getUserId()).set(user2)
                .addOnSuccessListener(aVoid -> Log.d("CreatePostActivity", "User2 kaydedildi"))
                .addOnFailureListener(e -> Log.e("CreatePostActivity", "User2 kaydedilemedi", e));

        // Rastgele postlar ve yorumlar oluştur
        List<Post> samplePosts = com.example.voteeverything.PostGenerator.generateSamplePostsAndComments();

        // Post ve yorumları Firestore'a kaydet
        for (Post post : samplePosts) {
            firestore.collection("posts").document(post.getPostId()).set(post)
                    .addOnSuccessListener(aVoid -> Log.d("CreatePostActivity", "Post kaydedildi: " + post.getPostId()))
                    .addOnFailureListener(e -> Log.e("CreatePostActivity", "Post kaydedilemedi: " + post.getPostId(), e));

            for (Comment comment : post.getComments()) {
                firestore.collection("comments").document(comment.getCommentId()).set(comment)
                        .addOnSuccessListener(aVoid -> Log.d("CreatePostActivity", "Comment kaydedildi: " + comment.getCommentId()))
                        .addOnFailureListener(e -> Log.e("CreatePostActivity", "Comment kaydedilemedi: " + comment.getCommentId(), e));
            }
        }
    }
}


