package com.example.voteeverything;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteeverything.Fragments.AddCommentBottomSheetDialog;
import com.example.voteeverything.adapters.CommentAdapter;
import com.example.voteeverything.ViewModels.ShowPostViewModel;
import com.example.voteeverything.models.Comment;
import com.example.voteeverything.models.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowPostActivity extends AppCompatActivity {

    private ShowPostViewModel viewModel;
    private RatingBar postRatingBar;
    private TextView postRatingTextView;
    private TextView postTitleTextView;
    private TextView postDescriptionTextView;
    private TextView postDateTextView;
    private TextView postOwnerTextView;
    private RecyclerView commentsRecyclerView;
    private CommentAdapter commentAdapter;
    private FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);


        viewModel = new ViewModelProvider(this).get(ShowPostViewModel.class);


        postTitleTextView = findViewById(R.id.postTitleTextView);
        postDescriptionTextView = findViewById(R.id.postDescriptionTextView);
        postDateTextView = findViewById(R.id.postDateTextView);
        postOwnerTextView = findViewById(R.id.postOwnerTextView);
        postRatingBar = findViewById(R.id.postRatingBar);
        postRatingTextView = findViewById(R.id.postRatingTextView);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        actionButton = findViewById(R.id.editPostButton);


        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter();
        commentsRecyclerView.setAdapter(commentAdapter);


        Post post = getIntent().getParcelableExtra("post");
        if (post != null) {
            displayPostDetails(post);
            displayComments(post.getComments());
            configureActionButton(post);
        }
    }

    private void displayPostDetails(Post post) {

        String formattedCreationTime = formatDate(post.getCreationTime());
        String formattedUpdateTime = formatDate(post.getUpdateTime()); // todo sil beni

        postTitleTextView.setText(post.getPostId());
        postDescriptionTextView.setText(post.getDescription());
        postDateTextView.setText("Created: " + formattedCreationTime );


        viewModel.getPostOwner(post.getUserId()).observe(this, username -> {
            if (username != null) {
                postOwnerTextView.setText("Posted by: " + username);
            }
        });


        float averageRating = calculateAverageRating(post.getComments());
        postRatingBar.setRating(averageRating / 2); // 10 üzerinden gelen puanı 5 yıldıza ölçekle
        postRatingTextView.setText(String.format("%.1f/10", averageRating));
    }

    private void displayComments(List<Comment> comments) {
        if (comments != null && !comments.isEmpty()) {
            commentAdapter.setComments(comments);
        }
    }

    private float calculateAverageRating(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return 0f;
        }
        int totalRating = 0;
        for (Comment comment : comments) {
            totalRating += comment.getRating();
        }
        return totalRating / (float) comments.size();
    }

    private void configureActionButton(Post post) {
        String currentUserId = viewModel.getCurrentUserId();
        if (post.getUserId().equals(currentUserId)) {

            actionButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, CreatePostActivity.class);
                intent.putExtra("post", post);
                startActivity(intent);
            });
        } else {

            actionButton.setOnClickListener(v -> {
                AddCommentBottomSheetDialog dialog = AddCommentBottomSheetDialog.newInstance(post.getPostId());
                dialog.show(getSupportFragmentManager(), "AddCommentBottomSheet");
            });
        }
    }
    private String formatDate(String timestamp) {
        try {
            long time = Long.parseLong(timestamp);
            Date date = new Date(time);
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
            return formatter.format(date);
        } catch (NumberFormatException e) {
            return "Unknown date";
        }
    }
}
