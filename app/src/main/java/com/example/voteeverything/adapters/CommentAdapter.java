package com.example.voteeverything.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteeverything.R;
import com.example.voteeverything.models.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Comment comment);
    }

    private List<Comment> comments = new ArrayList<>();
    private OnItemClickListener listener;

    // Default constructor
    public CommentAdapter() {
    }

    public CommentAdapter(List<Comment> comments, OnItemClickListener listener) {
        this.comments = comments;
        this.listener = listener;
    }


    public void setComments(List<Comment> comments) {
        // Boş ya da null açıklamalı yorumları filtrele // todo ingilizce yap
        List<Comment> filteredComments = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getDescription() != null && !comment.getDescription().trim().isEmpty()) {
                filteredComments.add(comment);
            }
        }
        this.comments = filteredComments;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);

        // Format creation time
        String formattedDate = formatDate(comment.getCreationTime());

        holder.commentOwnerTextView.setText(comment.getUserId() != null ? "By: " + formattedDate : "By: Anonymous");
        holder.commentRatingTextView.setText("Rating: " + comment.getRating());
        holder.commentDescriptionTextView.setText(comment.getDescription());

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onItemClick(comment));
        }
    }

    private String formatDate(String creationTime) {
        try {
            long timestamp = Long.parseLong(creationTime);
            Date date = new Date(timestamp);
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
            return formatter.format(date);
        } catch (NumberFormatException e) {
            return "Unknown date";
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentOwnerTextView;
        TextView commentRatingTextView;
        TextView commentDescriptionTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentOwnerTextView = itemView.findViewById(R.id.commentOwnerTextView);
            commentRatingTextView = itemView.findViewById(R.id.commentRatingTextView);
            commentDescriptionTextView = itemView.findViewById(R.id.commentDescriptionTextView);
        }
    }
}
