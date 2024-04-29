package com.example.voteeverything.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteeverything.R;
import com.example.voteeverything.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> implements Filterable {

    private List<Post> postList;
    private List<Post> postListFull; // Filtreleme için orijinal veri listesi

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
        this.postListFull = new ArrayList<>(postList); // Klon oluştur
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView ratingTextView;
        private TextView commentCountTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            commentCountTextView = itemView.findViewById(R.id.commentCountTextView);
        }

        public void bind(Post post) {
            titleTextView.setText(post.getTitle());
            ratingTextView.setText("Rating: " + post.getRating());
            commentCountTextView.setText("Comments: " + post.getComments().size());
        }
    }

    @Override
    public Filter getFilter() {
        return postFilter;
    }

    private Filter postFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Post> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(postListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Post post : postListFull) {
                    if (post.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(post);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            postList.clear();
            postList.addAll((List<Post>) results.values);
            notifyDataSetChanged();
        }
    };
}
