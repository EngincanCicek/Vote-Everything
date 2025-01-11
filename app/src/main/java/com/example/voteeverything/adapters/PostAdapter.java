package com.example.voteeverything.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteeverything.R;
import com.example.voteeverything.models.Comment;
import com.example.voteeverything.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Post post);
    }

    private final List<Post> posts;
    private final OnItemClickListener listener;

    public PostAdapter(List<Post> posts, OnItemClickListener listener) {
        this.posts = posts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.titleTextView.setText(post.getPostId());

        float averageRating = calculatePostRating(post);
        holder.ratingBar.setRating(averageRating / 2); // 10 to 5 star
        holder.ratingTextView.setText(String.format("%.1f/10", averageRating)); // "x/10" formatted

        holder.itemView.setOnClickListener(v -> listener.onItemClick(post));
    }

    private float calculatePostRating(Post post) {
        if (post.getComments() == null || post.getComments().isEmpty()) {
            return 0f;
        }
        int totalRating = 0;
        for (Comment comment : post.getComments()) {
            totalRating += comment.getRating();
        }
        return totalRating / (float) post.getComments().size();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        RatingBar ratingBar;
        TextView ratingTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.postTitleTextView);
            ratingBar = itemView.findViewById(R.id.postRatingBar);
            ratingTextView = itemView.findViewById(R.id.postRatingTextView);
        }
    }
}
