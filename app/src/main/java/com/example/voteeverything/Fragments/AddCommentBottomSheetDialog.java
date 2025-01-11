package com.example.voteeverything.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.voteeverything.R;
import com.example.voteeverything.models.Comment;
import com.example.voteeverything.models.Post;
import com.example.voteeverything.models.User;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddCommentBottomSheetDialog extends BottomSheetDialogFragment {

    private static final String POST_ID_KEY = "POST_ID_KEY"; // Default

    public static AddCommentBottomSheetDialog newInstance(String postId) {
        AddCommentBottomSheetDialog fragment = new AddCommentBottomSheetDialog();
        Bundle args = new Bundle();
        args.putString(POST_ID_KEY, postId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_comment, container, false);

        String postId = getArguments().getString(POST_ID_KEY);

        EditText commentEditText = view.findViewById(R.id.commentEditText);
        RatingBar ratingBar = view.findViewById(R.id.commentRatingBar);
        Button submitButton = view.findViewById(R.id.submitCommentButton);

        submitButton.setOnClickListener(v -> {
            String commentText = commentEditText.getText().toString().trim();
            float rating = ratingBar.getRating();
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            if (commentText.isEmpty() || rating == 0) {
                Toast.makeText(getContext(), "Please provide a comment and rating", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            String commentId = String.valueOf(System.currentTimeMillis());

            // Comment create
            Comment newComment = new Comment(
                    commentId,
                    postId,
                    userId,
                    Math.round(rating * 2),
                    commentText,
                    String.valueOf(System.currentTimeMillis()),
                    null
            );

            // Post update
            firestore.collection("posts").document(postId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Post post = documentSnapshot.toObject(Post.class);
                            if (post != null) {
                                if (post.getComments() == null) {
                                    post.setComments(new ArrayList<>());
                                }
                                post.getComments().add(newComment);

                                firestore.collection("posts").document(postId).set(post)
                                        .addOnSuccessListener(aVoid -> updateUserWithComment(userId, newComment))
                                        .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to add comment to post", Toast.LENGTH_SHORT).show());
                            }
                        }
                    });
        });

        return view;
    }

    private void updateUserWithComment(String userId, Comment newComment) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            if (user.getCommentIdList() == null) {
                                user.setCommentIdList(new ArrayList<>());
                            }
                            user.getCommentIdList().add(newComment.getCommentId());

                            firestore.collection("users").document(userId).set(user)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getContext(), "Comment added successfully", Toast.LENGTH_SHORT).show();
                                        dismiss();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to update user comments", Toast.LENGTH_SHORT).show());
                        }
                    }
                });
    }
}
