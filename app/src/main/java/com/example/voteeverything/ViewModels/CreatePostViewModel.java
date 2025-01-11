package com.example.voteeverything.ViewModels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.voteeverything.models.Comment;
import com.example.voteeverything.models.Post;
import com.example.voteeverything.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CreatePostViewModel extends ViewModel {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public void createPost(String userId, String title, String description, float rating, OnPostCreatedListener listener) {
        Log.d("CreatePostViewModel", "Creating post for userId: " + userId);

        // UNIQ?
        firestore.collection("posts")
                .whereEqualTo("postId", title) // Başlık postId olarak kullanılıyor
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        // is it uniq
                        Log.e("CreatePostViewModel", "Post with the same title already exists");
                        listener.onFailure(new Exception("A post with this title already exists. Please choose a different title."));
                    } else {
                        // uniq Post ve Comment ID
                        String postId = title;
                        String commentId = String.valueOf(System.currentTimeMillis());

                        Log.d("CreatePostViewModel", "Generated Post ID: " + postId + ", Comment ID: " + commentId);

                        // comment create
                        Comment comment = new Comment(
                                commentId,
                                postId,
                                userId,
                                Math.round(rating),
                                null,
                                String.valueOf(System.currentTimeMillis()),
                                null
                        );

                        // Post create
                        Post post = new Post(
                                postId,
                                description,
                                userId,
                                new ArrayList<>(),
                                String.valueOf(System.currentTimeMillis()),
                                null
                        );
                        post.getComments().add(comment);


                        firestore.collection("users").document(userId)
                                .get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        User user = documentSnapshot.toObject(User.class);
                                        if (user != null) {
                                            Log.d("CreatePostViewModel", "User found: " + user.getUsername());

                                            if (user.getPostIdList() == null) user.setPostIdList(new ArrayList<>());
                                            if (user.getCommentIdList() == null) user.setCommentIdList(new ArrayList<>());

                                            user.getPostIdList().add(postId);
                                            user.getCommentIdList().add(commentId);

                                            // update user
                                            firestore.collection("users").document(userId).set(user)
                                                    .addOnSuccessListener(aVoid -> {
                                                        Log.d("CreatePostViewModel", "User updated successfully");

                                                        // Save post
                                                        firestore.collection("posts").document(postId).set(post)
                                                                .addOnSuccessListener(unused -> {
                                                                    Log.d("CreatePostViewModel", "Post saved successfully");
                                                                    listener.onSuccess();
                                                                })
                                                                .addOnFailureListener(e -> {
                                                                    Log.e("CreatePostViewModel", "Failed to save post", e);
                                                                    listener.onFailure(e);
                                                                });
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e("CreatePostViewModel", "Failed to update user", e);
                                                        listener.onFailure(e);
                                                    });
                                        } else {
                                            Log.e("CreatePostViewModel", "User is null");
                                            listener.onFailure(new Exception("User data is null"));
                                        }
                                    } else {
                                        Log.e("CreatePostViewModel", "User not found");
                                        listener.onFailure(new Exception("User not found"));
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("CreatePostViewModel", "Failed to retrieve user data", e);
                                    listener.onFailure(e);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CreatePostViewModel", "Failed to check existing post titles", e);
                    listener.onFailure(e);
                });
    }

    public interface OnPostCreatedListener {
        void onSuccess();

        void onFailure(Exception e);
    }
}
